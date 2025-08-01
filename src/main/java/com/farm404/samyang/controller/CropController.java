package com.farm404.samyang.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.CropService;
import com.farm404.samyang.service.UserService;

/**
 * 작물 컨트롤러
 * 작물 관련 웹 요청 처리
 */
@Controller
@RequestMapping("/crop")
public class CropController {
    
    @Autowired
    private CropService cropService;
    
    @Autowired
    private UserService userService;
    
    /**
     * 작물 목록 페이지
     */
    @GetMapping("/list")
    public String cropList(CropDTO searchCondition, Model model) {
        try {
            List<CropDTO> cropList = cropService.getCropList(searchCondition);
            int totalCount = cropService.getCropCount(searchCondition);
            
            model.addAttribute("cropList", cropList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("searchCondition", searchCondition);
            
            return "crop/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "작물 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 사용자별 작물 목록 페이지
     */
    @GetMapping("/user/{userId}")
    public String cropListByUser(@PathVariable Integer userId, Model model) {
        try {
            List<CropDTO> cropList = cropService.getCropListByUserId(userId);
            UserDTO user = userService.getUserById(userId);
            int totalCount = cropService.getCropCountByUserId(userId);
            
            model.addAttribute("cropList", cropList);
            model.addAttribute("user", user);
            model.addAttribute("totalCount", totalCount);
            
            return "crop/user-list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 작물 상세 페이지
     */
    @GetMapping("/detail/{cropId}")
    public String cropDetail(@PathVariable Integer cropId, Model model) {
        try {
            CropDTO crop = cropService.getCropById(cropId);
            model.addAttribute("crop", crop);
            return "crop/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 작물 등록 폼 페이지
     */
    @GetMapping("/register")
    public String cropRegisterForm(Model model) {
        try {
            // 사용자 목록 조회 (select box용)
            List<UserDTO> userList = userService.getUserList(new UserDTO());
            
            model.addAttribute("crop", new CropDTO());
            model.addAttribute("userList", userList);
            
            return "crop/register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "작물 등록 페이지를 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 작물 등록 처리
     */
    @PostMapping("/register")
    public String cropRegister(CropDTO crop, RedirectAttributes redirectAttributes) {
        try {
            cropService.registerCrop(crop);
            redirectAttributes.addFlashAttribute("successMessage", "작물이 등록되었습니다.");
            return "redirect:/crop/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("crop", crop);
            return "redirect:/crop/register";
        }
    }
    
    /**
     * 작물 수정 폼 페이지
     */
    @GetMapping("/edit/{cropId}")
    public String cropEditForm(@PathVariable Integer cropId, Model model) {
        try {
            CropDTO crop = cropService.getCropById(cropId);
            List<UserDTO> userList = userService.getUserList(new UserDTO());
            
            model.addAttribute("crop", crop);
            model.addAttribute("userList", userList);
            
            return "crop/edit";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 작물 수정 처리
     */
    @PostMapping("/edit")
    public String cropEdit(CropDTO crop, RedirectAttributes redirectAttributes) {
        try {
            cropService.updateCrop(crop);
            redirectAttributes.addFlashAttribute("successMessage", "작물 정보가 수정되었습니다.");
            return "redirect:/crop/detail/" + crop.getCropId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/crop/edit/" + crop.getCropId();
        }
    }
    
    /**
     * 작물 삭제 처리
     */
    @PostMapping("/delete/{cropId}")
    public String cropDelete(@PathVariable Integer cropId, RedirectAttributes redirectAttributes) {
        try {
            cropService.deleteCrop(cropId);
            redirectAttributes.addFlashAttribute("successMessage", "작물이 삭제되었습니다.");
            return "redirect:/crop/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/crop/list";
        }
    }
    
    /**
     * 작물 상태 변경 처리
     */
    @PostMapping("/update-status")
    @ResponseBody
    public String updateCropStatus(@RequestParam Integer cropId, @RequestParam String status) {
        try {
            cropService.updateCropStatus(cropId, status);
            return "success";
        } catch (Exception e) {
            return "error: " + e.getMessage();
        }
    }
    
    /**
     * 수확 가능한 작물 목록 API
     */
    @GetMapping("/harvestable")
    public String harvestableCrops(Model model) {
        try {
            List<CropDTO> harvestableCrops = cropService.getHarvestableCrops();
            model.addAttribute("cropList", harvestableCrops);
            model.addAttribute("totalCount", harvestableCrops.size());
            model.addAttribute("pageTitle", "수확 가능한 작물");
            
            return "crop/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수확 가능한 작물 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
}