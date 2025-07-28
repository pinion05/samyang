package com.farm404.samyang.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 댓글 DTO 클래스
 * 데이터베이스 Comment 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentDTO {
    
    private Integer commentId;
    private Integer userId;
    private Integer cropId;
    private String content;
    private LocalDateTime createdAt;
    
    // 조인 관련 필드
    private String userName; // 댓글 작성자명
    private String cropName; // 작물명
    
    // 화면 전용 필드
    private String searchKeyword; // 검색용
}