package com.farm404.samyang.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 검색 조건 DTO 클래스
 * JSP 페이지에서 검색 조건을 전달할 때 사용
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SearchCondition {
    
    // 공통 검색 조건
    private String keyword; // 검색 키워드
    private String type; // 검색 타입 (제목, 내용, 작성자 등)
    
    // 날짜 관련 검색 조건
    private LocalDate startDate; // 시작 날짜
    private LocalDate endDate; // 종료 날짜
    
    // 카테고리 관련 검색 조건
    private String category; // 카테고리
    private String status; // 상태 (활성, 비활성 등)
    
    // 작물 관련 검색 조건
    private String cropName; // 작물명
    private String variety; // 품종
    
    // 사용자 관련 검색 조건
    private String userName; // 사용자명
    private Integer userId; // 사용자 ID
    
    // 농사일지 관련 검색 조건
    private String activityType; // 활동 유형
    private String weatherCondition; // 날씨 조건
    
    // 페이징 관련
    private Integer page; // 현재 페이지
    private Integer size; // 페이지 크기
    private String sortBy; // 정렬 기준
    private String sortDirection; // 정렬 방향 (ASC, DESC)
}