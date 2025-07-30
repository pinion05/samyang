package com.farm404.samyang.controller;

import com.farm404.samyang.dto.ReportDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/report")
public class ReportController {
    
    @Autowired
    private ReportService reportService;
    
    @GetMapping("/list")
    public String reportList(@RequestParam(required = false) String status,
                            @RequestParam(required = false) String category,
                            @RequestParam(required = false) String type,
                            HttpSession session,
                            Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/";
        }
        
        List<ReportDTO> reportList;
        
        // 필터링 조건에 따른 조회
        if (status != null && !status.isEmpty()) {
            reportList = reportService.getReportsByStatus(status);
        } else if (category != null && !category.isEmpty()) {
            reportList = reportService.getReportsByCategory(category);
        } else if (type != null && !type.isEmpty()) {
            reportList = reportService.getReportsByType(type);
        } else {
            reportList = reportService.getAllReports();
        }
        
        // 통계 정보 조회
        Map<String, Object> statistics = reportService.getReportStatistics();
        
        model.addAttribute("reportList", reportList);
        model.addAttribute("statistics", statistics);
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("selectedType", type);
        
        return "report/list";
    }
    
    @GetMapping("/detail/{reportId}")
    public String reportDetail(@PathVariable Integer reportId,
                              HttpSession session,
                              Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        // 관리자만 접근 가능
        if (!"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/";
        }
        
        ReportDTO report = reportService.getReportById(reportId);
        if (report == null) {
            return "redirect:/report/list";
        }
        
        model.addAttribute("report", report);
        return "report/detail";
    }
    
    @GetMapping("/form")
    public String reportForm(@RequestParam(required = false) Integer reportedId,
                            @RequestParam(required = false) String reportedType,
                            HttpSession session,
                            Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        ReportDTO report = new ReportDTO();
        report.setReporterId(Integer.parseInt(currentUser.getId()));
        
        if (reportedId != null && reportedType != null) {
            report.setReportedId(reportedId);
            report.setReportedType(reportedType);
        }
        
        model.addAttribute("report", report);
        return "report/form";
    }
    
    @PostMapping("/save")
    public String saveReport(@ModelAttribute ReportDTO report,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            report.setReporterId(Integer.parseInt(currentUser.getId()));
            boolean success = reportService.registerReport(report);
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "신고가 접수되었습니다.");
                return "redirect:/";
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", 
                    "이미 처리 중인 신고가 있거나 신고 처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/report/form";
    }
    
    @PostMapping("/process/{reportId}")
    public String processReport(@PathVariable Integer reportId,
                               @RequestParam String status,
                               @RequestParam(required = false) String adminNote,
                               HttpSession session,
                               RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        // 관리자만 처리 가능
        if (!"ADMIN".equals(currentUser.getRole())) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/";
        }
        
        try {
            boolean success = reportService.processReport(reportId, status, adminNote);
            
            if (success) {
                String statusText = "";
                switch (status) {
                    case "PROCESSING":
                        statusText = "처리중";
                        break;
                    case "RESOLVED":
                        statusText = "처리완료";
                        break;
                    case "REJECTED":
                        statusText = "반려";
                        break;
                }
                redirectAttributes.addFlashAttribute("successMessage", 
                    "신고가 " + statusText + " 상태로 변경되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "처리 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/report/detail/" + reportId;
    }
    
    @PostMapping("/delete/{reportId}")
    public String deleteReport(@PathVariable Integer reportId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        // 관리자만 삭제 가능
        if (!"ADMIN".equals(currentUser.getRole())) {
            redirectAttributes.addFlashAttribute("errorMessage", "권한이 없습니다.");
            return "redirect:/";
        }
        
        try {
            boolean success = reportService.deleteReport(reportId);
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "신고가 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/report/list";
    }
    
    @GetMapping("/my")
    public String myReports(HttpSession session, Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        Integer userId = Integer.parseInt(currentUser.getId());
        List<ReportDTO> myReports = reportService.getReportsByReporter(userId);
        
        model.addAttribute("reportList", myReports);
        model.addAttribute("isMyReports", true);
        
        return "report/list";
    }
}