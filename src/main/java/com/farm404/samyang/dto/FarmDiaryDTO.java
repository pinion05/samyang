package com.farm404.samyang.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 농사일지 DTO 클래스
 * 데이터베이스 농사일지 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FarmDiaryDTO {
    
    private Integer diaryId;
    private Integer userId;
    private Integer cropId; // ERD에 있는 관계 (README 232라인 참조)
    private LocalDate date;
    private String activityType;
    private String content;
    private String photoUrl;
    private LocalDateTime createdAt;
    
    // 조인 관련 필드
    private String userName;
    private String cropName;
    
    // 화면 전용 필드
    private String searchKeyword;
    private String activityFilter; // 활동 유형별 필터링용
    private LocalDate startDate; // 기간 검색용
    private LocalDate endDate; // 기간 검색용
}