package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 신고 엔티티 클래스
 * MySQL 데이터베이스의 report 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "report")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Report {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "report_id")
    private Long reportId;
    
    @Column(name = "reporter_user_id", nullable = false)
    private Long reporterUserId;
    
    @Column(name = "report_target_type", nullable = false, length = 50)
    private String reportTargetType; // '게시물', '댓글', '리뷰'
    
    @Column(name = "report_target_id", nullable = false)
    private Long reportTargetId;
    
    @Column(name = "report_reason", nullable = false, columnDefinition = "TEXT")
    private String reportReason;
    
    @CreationTimestamp
    @Column(name = "report_datetime", nullable = false, updatable = false)
    private LocalDateTime reportDatetime;
    
    @Column(name = "processing_status", length = 50)
    @Builder.Default
    private String processingStatus = "접수"; // '접수', '처리 중', '처리 완료', '반려'
    
    @Column(name = "sanction_content", columnDefinition = "TEXT")
    private String sanctionContent; // 예: '7일 계정 정지'
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reporter_user_id", insertable = false, updatable = false)
    private User reporter;
    
    /**
     * 엔티티가 저장되기 전에 실행되는 메서드
     */
    @PrePersist
    protected void onCreate() {
        if (reportDatetime == null) {
            reportDatetime = LocalDateTime.now();
        }
        if (processingStatus == null) {
            processingStatus = "접수";
        }
    }
}