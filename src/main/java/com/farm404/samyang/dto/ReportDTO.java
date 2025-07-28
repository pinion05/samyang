package com.farm404.samyang.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 신고 DTO 클래스
 * 데이터베이스 Report 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportDTO {
    
    private Integer reportId;
    private Integer reporterUserId; // 신고자
    private Integer reportedUserId; // 피신고자
    private String reason;          // 신고 사유
    private String content;         // 신고 내용
    private String status;          // PENDING, APPROVED, REJECTED
    private LocalDateTime createdAt;
    
    // 조인 관련 필드
    private String reporterUserName; // 신고자명
    private String reportedUserName; // 피신고자명
    
    // 화면 전용 필드
    private String searchKeyword;  // 검색용
    private String statusFilter;   // 상태별 필터링용
}