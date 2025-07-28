package com.farm404.samyang.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.service.FarmDiaryService;
import com.farm404.samyang.service.UserService;
import com.farm404.samyang.service.CropService;

/**
 * 농사일지 컨트롤러
 * 농사일지 관련 웹 요청 처리
 */
@Controller
@RequestMapping("/diary")
public class FarmDiaryController {
    
    @Autowired
    private FarmDiaryService farmDiaryService;
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CropService cropService;
    
    /**
     * 농사일지 목록 페이지
     */
    @GetMapping("/list")
    public String diaryList(FarmDiaryDTO searchCondition, Model model) {
        try {
            List<FarmDiaryDTO> diaryList = farmDiaryService.getFarmDiaryList(searchCondition);
            int totalCount = farmDiaryService.getFarmDiaryCount(searchCondition);
            
            model.addAttribute("diaryList", diaryList);
            model.addAttribute("totalCount", totalCount);
            model.addAttribute("searchCondition", searchCondition);
            
            return "diary/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "농사일지 목록을 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 사용자별 농사일지 목록 페이지
     */
    @GetMapping("/user/{사용자ID}")
    public String diaryListByUser(@PathVariable Integer 사용자ID, Model model) {
        try {
            List<FarmDiaryDTO> diaryList = farmDiaryService.getFarmDiaryListByUserId(사용자ID);
            UserDTO user = userService.getUserById(사용자ID);
            int totalCount = farmDiaryService.getFarmDiaryCountByUserId(사용자ID);
            
            model.addAttribute("diaryList", diaryList);
            model.addAttribute("user", user);
            model.addAttribute("totalCount", totalCount);
            
            return "diary/user-list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 작물별 농사일지 목록 페이지
     */
    @GetMapping("/crop/{작물ID}")
    public String diaryListByCrop(@PathVariable Integer 작물ID, Model model) {
        try {
            List<FarmDiaryDTO> diaryList = farmDiaryService.getFarmDiaryListByCropId(작물ID);
            CropDTO crop = cropService.getCropById(작물ID);
            int totalCount = farmDiaryService.getFarmDiaryCountByCropId(작물ID);
            
            model.addAttribute("diaryList", diaryList);
            model.addAttribute("crop", crop);
            model.addAttribute("totalCount", totalCount);
            
            return "diary/crop-list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 농사일지 상세 페이지
     */
    @GetMapping("/detail/{농사일지ID}")
    public String diaryDetail(@PathVariable Integer 농사일지ID, Model model) {
        try {
            FarmDiaryDTO diary = farmDiaryService.getFarmDiaryById(농사일지ID);
            model.addAttribute("diary", diary);
            return "diary/detail";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 농사일지 등록 폼 페이지
     */
    @GetMapping("/register")
    public String diaryRegisterForm(@RequestParam(required = false) Integer 사용자ID,
                                   @RequestParam(required = false) Integer 작물ID,
                                   Model model) {
        try {
            // 사용자 목록 조회
            List<UserDTO> userList = userService.getUserList(new UserDTO());
            
            // 작물 목록 조회 (사용자ID가 있으면 해당 사용자의 작물만)
            List<CropDTO> cropList;
            if (사용자ID != null) {
                cropList = cropService.getCropListByUserId(사용자ID);
            } else {
                cropList = cropService.getCropList(new CropDTO());
            }
            
            FarmDiaryDTO diary = new FarmDiaryDTO();
            if (사용자ID != null) diary.set사용자ID(사용자ID);
            if (작물ID != null) diary.set작물ID(작물ID);
            
            model.addAttribute("diary", diary);
            model.addAttribute("userList", userList);
            model.addAttribute("cropList", cropList);
            
            return "diary/register";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "농사일지 등록 페이지를 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
    
    /**
     * 농사일지 등록 처리
     */
    @PostMapping("/register")
    public String diaryRegister(FarmDiaryDTO diary, RedirectAttributes redirectAttributes) {
        try {
            farmDiaryService.registerFarmDiary(diary);
            redirectAttributes.addFlashAttribute("successMessage", "농사일지가 등록되었습니다.");
            return "redirect:/diary/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            redirectAttributes.addFlashAttribute("diary", diary);
            return "redirect:/diary/register";
        }
    }
    
    /**
     * 농사일지 수정 폼 페이지
     */
    @GetMapping("/edit/{농사일지ID}")
    public String diaryEditForm(@PathVariable Integer 농사일지ID, Model model) {
        try {
            FarmDiaryDTO diary = farmDiaryService.getFarmDiaryById(농사일지ID);
            List<UserDTO> userList = userService.getUserList(new UserDTO());
            List<CropDTO> cropList = cropService.getCropListByUserId(diary.get사용자ID());
            
            model.addAttribute("diary", diary);
            model.addAttribute("userList", userList);
            model.addAttribute("cropList", cropList);
            
            return "diary/edit";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "error";
        }
    }
    
    /**
     * 농사일지 수정 처리
     */
    @PostMapping("/edit")
    public String diaryEdit(FarmDiaryDTO diary, RedirectAttributes redirectAttributes) {
        try {
            farmDiaryService.updateFarmDiary(diary);
            redirectAttributes.addFlashAttribute("successMessage", "농사일지가 수정되었습니다.");
            return "redirect:/diary/detail/" + diary.get농사일지ID();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/diary/edit/" + diary.get농사일지ID();
        }
    }
    
    /**
     * 농사일지 삭제 처리
     */
    @PostMapping("/delete/{농사일지ID}")
    public String diaryDelete(@PathVariable Integer 농사일지ID, RedirectAttributes redirectAttributes) {
        try {
            farmDiaryService.deleteFarmDiary(농사일지ID);
            redirectAttributes.addFlashAttribute("successMessage", "농사일지가 삭제되었습니다.");
            return "redirect:/diary/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
            return "redirect:/diary/list";
        }
    }
    
    /**
     * 사용자별 작물 목록 API (AJAX용)
     */
    @GetMapping("/api/crops/{사용자ID}")
    @ResponseBody
    public List<CropDTO> getCropsByUserId(@PathVariable Integer 사용자ID) {
        try {
            return cropService.getCropListByUserId(사용자ID);
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * 최근 농사일지 목록 페이지
     */
    @GetMapping("/recent")
    public String recentDiaries(Model model) {
        try {
            List<FarmDiaryDTO> recentDiaries = farmDiaryService.getRecentFarmDiaries(10);
            model.addAttribute("diaryList", recentDiaries);
            model.addAttribute("totalCount", recentDiaries.size());
            model.addAttribute("pageTitle", "최근 농사일지");
            
            return "diary/list";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "최근 농사일지를 불러오는 중 오류가 발생했습니다.");
            return "error";
        }
    }
}