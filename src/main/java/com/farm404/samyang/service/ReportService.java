package com.farm404.samyang.service;

import com.farm404.samyang.dto.ReportDTO;
import com.farm404.samyang.mapper.ReportMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class ReportService {
    
    @Autowired
    private ReportMapper reportMapper;
    
    // 신고 등록
    public boolean registerReport(ReportDTO report) {
        try {
            // 유효성 검사
            if (report.getReporterId() == null || report.getReportedId() == null || 
                report.getReportedType() == null || report.getCategory() == null || 
                report.getReason() == null) {
                return false;
            }
            
            // 신고 유형 검증
            if (!isValidReportedType(report.getReportedType())) {
                return false;
            }
            
            // 카테고리 검증
            if (!isValidCategory(report.getCategory())) {
                return false;
            }
            
            // 신고 사유 검증
            if (report.getReason().trim().isEmpty() || report.getReason().length() > 1000) {
                return false;
            }
            
            // 중복 신고 확인
            int duplicateCount = reportMapper.checkDuplicateReport(
                report.getReporterId(), 
                report.getReportedId(), 
                report.getReportedType()
            );
            
            if (duplicateCount > 0) {
                return false; // 이미 처리 중인 신고가 있음
            }
            
            return reportMapper.insertReport(report) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 신고 조회
    public ReportDTO getReportById(Integer reportId) {
        if (reportId == null) {
            return null;
        }
        return reportMapper.selectReportById(reportId);
    }
    
    // 전체 신고 목록 조회 (관리자용)
    public List<ReportDTO> getAllReports() {
        return reportMapper.selectAllReports();
    }
    
    // 상태별 신고 목록 조회
    public List<ReportDTO> getReportsByStatus(String status) {
        if (status == null || !isValidStatus(status)) {
            return null;
        }
        return reportMapper.selectReportsByStatus(status);
    }
    
    // 카테고리별 신고 목록 조회
    public List<ReportDTO> getReportsByCategory(String category) {
        if (category == null || !isValidCategory(category)) {
            return null;
        }
        return reportMapper.selectReportsByCategory(category);
    }
    
    // 신고 유형별 목록 조회
    public List<ReportDTO> getReportsByType(String reportedType) {
        if (reportedType == null || !isValidReportedType(reportedType)) {
            return null;
        }
        return reportMapper.selectReportsByType(reportedType);
    }
    
    // 사용자의 신고 목록 조회
    public List<ReportDTO> getReportsByReporter(Integer reporterId) {
        if (reporterId == null) {
            return null;
        }
        return reportMapper.selectReportsByReporter(reporterId);
    }
    
    // 특정 대상에 대한 신고 목록 조회
    public List<ReportDTO> getReportsByTarget(Integer reportedId, String reportedType) {
        if (reportedId == null || reportedType == null) {
            return null;
        }
        return reportMapper.selectReportsByTarget(reportedId, reportedType);
    }
    
    // 신고 상태 업데이트
    public boolean updateReportStatus(Integer reportId, String status) {
        try {
            if (reportId == null || status == null) {
                return false;
            }
            
            // 상태 유효성 검사
            if (!isValidStatus(status)) {
                return false;
            }
            
            // 신고 존재 여부 확인
            ReportDTO existingReport = reportMapper.selectReportById(reportId);
            if (existingReport == null) {
                return false;
            }
            
            return reportMapper.updateReportStatus(reportId, status) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 관리자 노트 업데이트 및 신고 처리
    public boolean processReport(Integer reportId, String status, String adminNote) {
        try {
            if (reportId == null || status == null) {
                return false;
            }
            
            // 상태 유효성 검사
            if (!isValidStatus(status)) {
                return false;
            }
            
            // 신고 존재 여부 확인
            ReportDTO existingReport = reportMapper.selectReportById(reportId);
            if (existingReport == null) {
                return false;
            }
            
            // 상태 업데이트
            boolean statusUpdated = reportMapper.updateReportStatus(reportId, status) > 0;
            
            // 관리자 노트 업데이트 (처리 완료 상태인 경우)
            if (statusUpdated && ("RESOLVED".equals(status) || "REJECTED".equals(status))) {
                Date resolvedAt = new Date();
                reportMapper.updateAdminNote(reportId, adminNote, resolvedAt);
            }
            
            return statusUpdated;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 신고 삭제
    public boolean deleteReport(Integer reportId) {
        try {
            if (reportId == null) {
                return false;
            }
            
            // 신고 존재 여부 확인
            ReportDTO existingReport = reportMapper.selectReportById(reportId);
            if (existingReport == null) {
                return false;
            }
            
            return reportMapper.deleteReport(reportId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 신고 통계 조회
    public Map<String, Object> getReportStatistics() {
        Map<String, Object> statistics = new HashMap<>();
        
        // 상태별 통계
        Map<String, Integer> statusStats = new HashMap<>();
        statusStats.put("pending", reportMapper.countReportsByStatus("PENDING"));
        statusStats.put("processing", reportMapper.countReportsByStatus("PROCESSING"));
        statusStats.put("resolved", reportMapper.countReportsByStatus("RESOLVED"));
        statusStats.put("rejected", reportMapper.countReportsByStatus("REJECTED"));
        statistics.put("statusStats", statusStats);
        
        // 카테고리별 통계
        Map<String, Integer> categoryStats = new HashMap<>();
        categoryStats.put("spam", reportMapper.countReportsByCategory("SPAM"));
        categoryStats.put("abuse", reportMapper.countReportsByCategory("ABUSE"));
        categoryStats.put("fraud", reportMapper.countReportsByCategory("FRAUD"));
        categoryStats.put("inappropriate", reportMapper.countReportsByCategory("INAPPROPRIATE"));
        categoryStats.put("other", reportMapper.countReportsByCategory("OTHER"));
        statistics.put("categoryStats", categoryStats);
        
        // 유형별 통계
        Map<String, Integer> typeStats = new HashMap<>();
        typeStats.put("user", reportMapper.countReportsByType("USER"));
        typeStats.put("crop", reportMapper.countReportsByType("CROP"));
        typeStats.put("diary", reportMapper.countReportsByType("DIARY"));
        typeStats.put("review", reportMapper.countReportsByType("REVIEW"));
        typeStats.put("comment", reportMapper.countReportsByType("COMMENT"));
        statistics.put("typeStats", typeStats);
        
        // 전체 개수
        int totalCount = statusStats.values().stream().mapToInt(Integer::intValue).sum();
        statistics.put("totalCount", totalCount);
        
        return statistics;
    }
    
    // 신고 유형 유효성 검사
    private boolean isValidReportedType(String reportedType) {
        return "USER".equals(reportedType) || 
               "CROP".equals(reportedType) || 
               "DIARY".equals(reportedType) || 
               "REVIEW".equals(reportedType) || 
               "COMMENT".equals(reportedType);
    }
    
    // 카테고리 유효성 검사
    private boolean isValidCategory(String category) {
        return "SPAM".equals(category) || 
               "ABUSE".equals(category) || 
               "FRAUD".equals(category) || 
               "INAPPROPRIATE".equals(category) || 
               "OTHER".equals(category);
    }
    
    // 상태 유효성 검사
    private boolean isValidStatus(String status) {
        return "PENDING".equals(status) || 
               "PROCESSING".equals(status) || 
               "RESOLVED".equals(status) || 
               "REJECTED".equals(status);
    }
}