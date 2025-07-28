package com.farm404.samyang.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 작물 DTO 클래스
 * 데이터베이스 작물 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CropDTO {
    
    private Integer cropId;
    private Integer userId;
    private String cropName;
    private String variety;
    private LocalDate plantingDate;
    private LocalDate expectedHarvestDate;
    private String status;
    
    // 조인 관련 필드
    private String userName; // 작물 목록에서 사용자 이름 표시용
    
    // 화면 전용 필드
    private String searchKeyword;
    private String statusFilter; // 상태별 필터링용
}