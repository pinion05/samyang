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
    
    private Integer 작물ID;
    private Integer 사용자ID;
    private String 작물명;
    private String 품종;
    private LocalDate 심은날짜;
    private LocalDate 예상수확일;
    private String 상태;
    
    // 조인 관련 필드
    private String 사용자이름; // 작물 목록에서 사용자 이름 표시용
    
    // 화면 전용 필드
    private String 검색키워드;
    private String 상태필터; // 상태별 필터링용
}