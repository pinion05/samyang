package com.farm404.samyang.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory; // Added import for LoggerFactory
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.service.UserService;
import com.farm404.samyang.service.CropService;
import com.farm404.samyang.service.FarmDiaryService;

/**
 * 홈 컨트롤러
 * 메인 페이지 및 대시보드 기능
 */
@Controller
public class HomeController {
    
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class); // Added logger instance
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CropService cropService;
    
    @Autowired
    private FarmDiaryService farmDiaryService;
    
    /**
     * 메인 페이지 (대시보드)
     */
    @GetMapping("/")
    public String home(Model model) {
        try {
            // 전체 현황 조회
            int totalUsers = userService.getUserCount(new UserDTO());
            int totalCrops = cropService.getCropCount(new CropDTO());
            int totalDiaries = farmDiaryService.getFarmDiaryCount(new FarmDiaryDTO());
            
            // 최근 등록된 사용자 (최대 5명)
            List<UserDTO> recentUsers = userService.getUserList(new UserDTO());
            if (recentUsers.size() > 5) {
                recentUsers = recentUsers.subList(0, 5);
            }
            
            // 최근 등록된 작물 (최대 5개)
            List<CropDTO> recentCrops = cropService.getCropList(new CropDTO());
            if (recentCrops.size() > 5) {
                recentCrops = recentCrops.subList(0, 5);
            }
            
            // 최근 농사일지 (최대 5개)
            List<FarmDiaryDTO> recentDiaries = farmDiaryService.getRecentFarmDiaries(5);
            
            // 수확 가능한 작물
            List<CropDTO> harvestableCrops = cropService.getHarvestableCrops();
            
            model.addAttribute("totalUsers", totalUsers);
            model.addAttribute("totalCrops", totalCrops);
            model.addAttribute("totalDiaries", totalDiaries);
            model.addAttribute("recentUsers", recentUsers);
            model.addAttribute("recentCrops", recentCrops);
            model.addAttribute("recentDiaries", recentDiaries);
            model.addAttribute("harvestableCrops", harvestableCrops);
            
            return "home";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "메인 페이지를 불러오는 중 오류가 발생했습니다.");
            // Replaced System.out.println with proper logger
            logger.error("Error occurred in HomeController.home method", e);
            return "error";
        }
    }
    
    /**
     * 에러 페이지
     */
    @GetMapping("/error")
    public String error() {
        return "error";
    }
}