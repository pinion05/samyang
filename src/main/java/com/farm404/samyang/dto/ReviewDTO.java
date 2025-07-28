package com.farm404.samyang.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 리뷰 DTO 클래스
 * 데이터베이스 Review 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDTO {
    
    private Integer reviewId;
    private Integer userId;
    private Integer cropId;
    private Integer rating;     // 평점 (1-5)
    private String content;
    private LocalDateTime createdAt;
    
    // 조인 관련 필드
    private String userName; // 리뷰 작성자명
    private String cropName; // 작물명
    
    // 화면 전용 필드
    private String searchKeyword; // 검색용
    private Integer ratingFilter; // 평점별 필터링용
}