package com.farm404.samyang.controller;

import com.farm404.samyang.dto.FarmingDiaryDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.FarmingDiaryService;
import com.farm404.samyang.util.FileUploadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@Controller
@RequestMapping("/diary")
public class FarmingDiaryController {
    
    @Autowired
    private FarmingDiaryService farmingDiaryService;
    
    @Autowired
    private FileUploadUtil fileUploadUtil;
    
    @GetMapping("/list")
    public String diaryList(@RequestParam(required = false) String activityType,
                           HttpSession session,
                           Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        List<FarmingDiaryDTO> diaryList;
        
        // 관리자는 모든 일지 조회, 일반 사용자는 자신의 일지만 조회
        if ("ADMIN".equals(currentUser.getRole())) {
            diaryList = farmingDiaryService.getAllDiaries();
        } else {
            Integer userId = Integer.parseInt(currentUser.getId());
            if (activityType != null && !activityType.isEmpty()) {
                diaryList = farmingDiaryService.getDiariesByActivityType(userId, activityType);
            } else {
                diaryList = farmingDiaryService.getDiariesByUserId(userId);
            }
        }
        
        model.addAttribute("diaryList", diaryList);
        return "diary/list";
    }
    
    @GetMapping("/detail/{diaryId}")
    public String diaryDetail(@PathVariable Integer diaryId,
                             HttpSession session,
                             Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        FarmingDiaryDTO diary = farmingDiaryService.getDiaryById(diaryId);
        if (diary == null) {
            return "redirect:/diary/list";
        }
        
        // 권한 체크
        Integer userId = Integer.parseInt(currentUser.getId());
        if (!diary.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/diary/list";
        }
        
        model.addAttribute("diary", diary);
        return "diary/detail";
    }
    
    @GetMapping("/form")
    public String diaryForm(@RequestParam(required = false) Integer diaryId,
                           HttpSession session,
                           Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        FarmingDiaryDTO diary;
        if (diaryId != null) {
            diary = farmingDiaryService.getDiaryById(diaryId);
            if (diary == null) {
                return "redirect:/diary/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!diary.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                return "redirect:/diary/list";
            }
        } else {
            diary = new FarmingDiaryDTO();
            diary.setUserId(Integer.parseInt(currentUser.getId()));
            diary.setDate(LocalDate.now());
        }
        
        model.addAttribute("diary", diary);
        model.addAttribute("isEdit", diaryId != null);
        return "diary/form";
    }
    
    @PostMapping("/save")
    public String saveDiary(@ModelAttribute FarmingDiaryDTO diary,
                           @RequestParam(required = false) MultipartFile photoFile,
                           HttpSession session,
                           RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            // 사진 업로드 처리
            if (photoFile != null && !photoFile.isEmpty()) {
                if (!fileUploadUtil.isImageFile(photoFile)) {
                    redirectAttributes.addFlashAttribute("errorMessage", "이미지 파일만 업로드 가능합니다.");
                    return "redirect:/diary/form";
                }
                
                if (!fileUploadUtil.isValidFileSize(photoFile, 5)) {
                    redirectAttributes.addFlashAttribute("errorMessage", "파일 크기는 5MB 이하여야 합니다.");
                    return "redirect:/diary/form";
                }
                
                String savedPath = fileUploadUtil.saveFile(photoFile, "diary");
                diary.setPhotoUrl(savedPath);
            }
            
            boolean success;
            if (diary.getFarmingDiaryId() != null) {
                // 수정
                FarmingDiaryDTO existingDiary = farmingDiaryService.getDiaryById(diary.getFarmingDiaryId());
                if (existingDiary == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "일지를 찾을 수 없습니다.");
                    return "redirect:/diary/list";
                }
                
                // 권한 체크
                Integer userId = Integer.parseInt(currentUser.getId());
                if (!existingDiary.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
                    return "redirect:/diary/list";
                }
                
                // 기존 사진이 있고 새 사진이 업로드된 경우 기존 사진 삭제
                if (existingDiary.getPhotoUrl() != null && diary.getPhotoUrl() != null) {
                    fileUploadUtil.deleteFile(existingDiary.getPhotoUrl());
                }
                
                success = farmingDiaryService.updateDiary(diary);
            } else {
                // 등록
                diary.setUserId(Integer.parseInt(currentUser.getId()));
                success = farmingDiaryService.registerDiary(diary);
            }
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    diary.getFarmingDiaryId() != null ? "일지가 수정되었습니다." : "일지가 등록되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "작업 중 오류가 발생했습니다.");
            }
        } catch (IOException e) {
            redirectAttributes.addFlashAttribute("errorMessage", "파일 업로드 중 오류가 발생했습니다.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/diary/list";
    }
    
    @PostMapping("/delete/{diaryId}")
    public String deleteDiary(@PathVariable Integer diaryId,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            FarmingDiaryDTO diary = farmingDiaryService.getDiaryById(diaryId);
            if (diary == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "일지를 찾을 수 없습니다.");
                return "redirect:/diary/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!diary.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "redirect:/diary/list";
            }
            
            // 사진 파일 삭제
            if (diary.getPhotoUrl() != null) {
                fileUploadUtil.deleteFile(diary.getPhotoUrl());
            }
            
            boolean success = farmingDiaryService.deleteDiary(diaryId);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "일지가 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/diary/list";
    }
    
    @GetMapping("/calendar")
    public String diaryCalendar(@RequestParam(required = false) Integer year,
                               @RequestParam(required = false) Integer month,
                               HttpSession session,
                               Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        // 기본값 설정
        LocalDate now = LocalDate.now();
        if (year == null) year = now.getYear();
        if (month == null) month = now.getMonthValue();
        
        Integer userId = Integer.parseInt(currentUser.getId());
        List<FarmingDiaryDTO> monthlyDiaries = farmingDiaryService.getDiariesByMonth(userId, year, month);
        
        model.addAttribute("year", year);
        model.addAttribute("month", month);
        model.addAttribute("diaries", monthlyDiaries);
        
        return "diary/calendar";
    }
}