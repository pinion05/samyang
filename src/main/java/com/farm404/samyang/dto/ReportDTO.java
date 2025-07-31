package com.farm404.samyang.dto;

import java.util.Date;

public class ReportDTO {
    private Integer reportId;
    // DB 스키마와 일치하도록 필드명 변경
    private Integer reporterUserId;  // DB: ReporterUserID
    private Integer targetId;        // DB: TargetID
    private String reportType;       // DB: ReportType
    private String reason;
    private String status; // DB에는 기본값 'Received'
    private Date reportedAt;         // DB: ReportedAt
    
    // 호환성을 위한 필드 (JSP와 Mapper에서 사용)
    private String reportedType;     // reportType의 별칭
    
    // DB에 없는 필드들 (화면 표시용)
    private String category; // UI용 추가 분류
    private String adminNote;
    private Date resolvedAt;
    
    // 조인 필드
    private String reporterName;
    private String reportedTitle; // 신고 대상의 제목/이름
    private String reportedUserName; // 신고 대상 사용자 이름
    
    // 생성자
    public ReportDTO() {}
    
    // Getters and Setters
    public Integer getReportId() {
        return reportId;
    }
    
    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }
    
    public Integer getReporterUserId() {
        return reporterUserId;
    }
    
    public void setReporterUserId(Integer reporterUserId) {
        this.reporterUserId = reporterUserId;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public Integer getReporterId() {
        return reporterUserId;
    }
    
    @Deprecated
    public void setReporterId(Integer reporterId) {
        this.reporterUserId = reporterId;
    }
    
    public Integer getTargetId() {
        return targetId;
    }
    
    public void setTargetId(Integer targetId) {
        this.targetId = targetId;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public Integer getReportedId() {
        return targetId;
    }
    
    @Deprecated
    public void setReportedId(Integer reportedId) {
        this.targetId = reportedId;
    }
    
    public String getReportType() {
        return reportType;
    }
    
    public void setReportType(String reportType) {
        this.reportType = reportType;
    }
    
    // reportedType 필드 getter/setter (JSP 호환성)
    public String getReportedType() {
        return reportedType != null ? reportedType : reportType;
    }
    
    public void setReportedType(String reportedType) {
        this.reportedType = reportedType;
        this.reportType = reportedType;  // 두 필드 동기화
    }
    
    public String getCategory() {
        return category;
    }
    
    public void setCategory(String category) {
        this.category = category;
    }
    
    public String getReason() {
        return reason;
    }
    
    public void setReason(String reason) {
        this.reason = reason;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getAdminNote() {
        return adminNote;
    }
    
    public void setAdminNote(String adminNote) {
        this.adminNote = adminNote;
    }
    
    public Date getReportedAt() {
        return reportedAt;
    }
    
    public void setReportedAt(Date reportedAt) {
        this.reportedAt = reportedAt;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public Date getCreatedAt() {
        return reportedAt;
    }
    
    @Deprecated
    public void setCreatedAt(Date createdAt) {
        this.reportedAt = createdAt;
    }
    
    public Date getResolvedAt() {
        return resolvedAt;
    }
    
    public void setResolvedAt(Date resolvedAt) {
        this.resolvedAt = resolvedAt;
    }
    
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
    
    // 유틸리티 메서드
    public String getReportedTypeText() {
        if (reportedType == null) return "";
        switch (reportedType) {
            case "USER":
                return "사용자";
            case "CROP":
                return "작물";
            case "DIARY":
                return "농업일지";
            case "REVIEW":
                return "리뷰";
            case "COMMENT":
                return "댓글";
            default:
                return reportedType;
        }
    }
    
    public String getCategoryText() {
        if (category == null) return "";
        switch (category) {
            case "SPAM":
                return "스팸/광고";
            case "ABUSE":
                return "욕설/비방";
            case "FRAUD":
                return "사기/허위정보";
            case "INAPPROPRIATE":
                return "부적절한 콘텐츠";
            case "OTHER":
                return "기타";
            default:
                return category;
        }
    }
    
    public String getStatusText() {
        if (status == null) return "";
        switch (status) {
            case "PENDING":
                return "대기중";
            case "PROCESSING":
                return "처리중";
            case "RESOLVED":
                return "처리완료";
            case "REJECTED":
                return "반려";
            default:
                return status;
        }
    }
    
    public String getStatusColor() {
        if (status == null) return "secondary";
        switch (status) {
            case "PENDING":
                return "warning";
            case "PROCESSING":
                return "info";
            case "RESOLVED":
                return "success";
            case "REJECTED":
                return "danger";
            default:
                return "secondary";
        }
    }
    
    public boolean isPending() {
        return "PENDING".equals(status);
    }
    
    public boolean isProcessing() {
        return "PROCESSING".equals(status);
    }
    
    public boolean isResolved() {
        return "RESOLVED".equals(status) || "REJECTED".equals(status);
    }
}