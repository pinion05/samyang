package com.farm404.samyang.controller;

import java.util.List;
import java.util.ArrayList;
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
            // 데이터베이스 연결 실패 시 임시 더미 데이터 사용
            int totalUsers = 0;
            int totalCrops = 0;
            int totalDiaries = 0;
            List<UserDTO> recentUsers = new ArrayList<>();
            List<CropDTO> recentCrops = new ArrayList<>();
            List<FarmDiaryDTO> recentDiaries = new ArrayList<>();
            List<CropDTO> harvestableCrops = new ArrayList<>();
            
            try {
                // 전체 현황 조회
                totalUsers = userService.getUserCount(new UserDTO());
                totalCrops = cropService.getCropCount(new CropDTO());
                totalDiaries = farmDiaryService.getFarmDiaryCount(new FarmDiaryDTO());
                
                // 최근 등록된 사용자 (최대 5명)
                recentUsers = userService.getUserList(new UserDTO());
                if (recentUsers.size() > 5) {
                    recentUsers = recentUsers.subList(0, 5);
                }
                
                // 최근 등록된 작물 (최대 5개)
                recentCrops = cropService.getCropList(new CropDTO());
                if (recentCrops.size() > 5) {
                    recentCrops = recentCrops.subList(0, 5);
                }
                
                // 최근 농사일지 (최대 5개)
                recentDiaries = farmDiaryService.getRecentFarmDiaries(5);
                
                // 수확 가능한 작물
                harvestableCrops = cropService.getHarvestableCrops();
                
            } catch (Exception dbException) {
                logger.warn("데이터베이스 연결 실패, 임시 데이터로 처리: {}", dbException.getMessage());
                // 임시 더미 데이터는 빈 리스트로 유지
            }
            
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