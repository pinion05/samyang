package com.farm404.samyang.controller;

import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/crop")
public class CropController {
    
    @Autowired
    private CropService cropService;
    
    @GetMapping("/list")
    public String cropList(@RequestParam(required = false) String keyword,
                          @RequestParam(required = false) String status,
                          HttpSession session,
                          Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        List<CropDTO> cropList;
        
        // 관리자는 모든 작물 조회, 일반 사용자는 자신의 작물만 조회
        if ("ADMIN".equals(currentUser.getRole())) {
            if (keyword != null && !keyword.isEmpty()) {
                cropList = cropService.searchCrops(keyword);
            } else if (status != null && !status.isEmpty()) {
                cropList = cropService.getCropsByStatus(status);
            } else {
                cropList = cropService.getAllCrops();
            }
        } else {
            // UserDTO의 id가 String이므로 Integer로 변환
            Integer userId = Integer.parseInt(currentUser.getId());
            cropList = cropService.getCropsByUserId(userId);
            
            // 일반 사용자도 자신의 작물 중에서 검색
            if (keyword != null && !keyword.isEmpty()) {
                cropList = cropList.stream()
                    .filter(crop -> crop.getCropName().contains(keyword) || 
                                  (crop.getVariety() != null && crop.getVariety().contains(keyword)))
                    .toList();
            }
            if (status != null && !status.isEmpty()) {
                cropList = cropList.stream()
                    .filter(crop -> status.equals(crop.getStatus()))
                    .toList();
            }
        }
        
        model.addAttribute("cropList", cropList);
        return "crop/list";
    }
    
    @GetMapping("/detail/{cropId}")
    public String cropDetail(@PathVariable Integer cropId, 
                           HttpSession session, 
                           Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        CropDTO crop = cropService.getCropById(cropId);
        if (crop == null) {
            return "redirect:/crop/list";
        }
        
        // 권한 체크: 자신의 작물이거나 관리자만 조회 가능
        Integer userId = Integer.parseInt(currentUser.getId());
        if (!crop.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/crop/list";
        }
        
        model.addAttribute("crop", crop);
        return "crop/detail";
    }
    
    @GetMapping("/form")
    public String cropForm(@RequestParam(required = false) Integer cropId,
                         HttpSession session,
                         Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        CropDTO crop;
        if (cropId != null) {
            crop = cropService.getCropById(cropId);
            if (crop == null) {
                return "redirect:/crop/list";
            }
            
            // 권한 체크: 자신의 작물이거나 관리자만 수정 가능
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!crop.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                return "redirect:/crop/list";
            }
        } else {
            crop = new CropDTO();
            crop.setUserId(Integer.parseInt(currentUser.getId()));
        }
        
        model.addAttribute("crop", crop);
        model.addAttribute("isEdit", cropId != null);
        return "crop/form";
    }
    
    @PostMapping("/save")
    public String saveCrop(@ModelAttribute CropDTO crop,
                         HttpSession session,
                         RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            boolean success;
            if (crop.getCropId() != null) {
                // 수정
                CropDTO existingCrop = cropService.getCropById(crop.getCropId());
                if (existingCrop == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "작물을 찾을 수 없습니다.");
                    return "redirect:/crop/list";
                }
                
                // 권한 체크
                Integer userId = Integer.parseInt(currentUser.getId());
                if (!existingCrop.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
                    return "redirect:/crop/list";
                }
                
                success = cropService.updateCrop(crop);
            } else {
                // 등록
                crop.setUserId(Integer.parseInt(currentUser.getId()));
                success = cropService.registerCrop(crop);
            }
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    crop.getCropId() != null ? "작물 정보가 수정되었습니다." : "작물이 등록되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "작업 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/crop/list";
    }
    
    @PostMapping("/delete/{cropId}")
    public String deleteCrop(@PathVariable Integer cropId,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            CropDTO crop = cropService.getCropById(cropId);
            if (crop == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "작물을 찾을 수 없습니다.");
                return "redirect:/crop/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!crop.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "redirect:/crop/list";
            }
            
            boolean success = cropService.deleteCrop(cropId);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "작물이 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/crop/list";
    }
}