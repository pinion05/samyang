package com.farm404.samyang.dto;

/**
 * 신고 상세 정보 DTO
 * N+1 쿼리 방지를 위한 Join 결과 매핑용 DTO
 */
public class ReportDetailDTO extends ReportDTO {
    
    // Join으로 가져올 추가 정보
    private String reporterName;        // 신고자 이름
    private String reportedTitle;       // 신고 대상 제목
    private String reportedUserName;    // 신고 대상 사용자 이름
    
    // Getters and Setters
    public String getReporterName() {
        return reporterName;
    }
    
    public void setReporterName(String reporterName) {
        this.reporterName = reporterName;
    }
    
    public String getReportedTitle() {
        return reportedTitle;
    }
    
    public void setReportedTitle(String reportedTitle) {
        this.reportedTitle = reportedTitle;
    }
    
    public String getReportedUserName() {
        return reportedUserName;
    }
    
    public void setReportedUserName(String reportedUserName) {
        this.reportedUserName = reportedUserName;
    }
}