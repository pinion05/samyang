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
    
    private Integer 농사일지ID;
    private Integer 사용자ID;
    private Integer 작물ID; // ERD에 있는 관계 (README 232라인 참조)
    private LocalDate 날짜;
    private String 활동_유형;
    private String 내용;
    private String 사진_URL;
    private LocalDateTime 작성일시;
    
    // 조인 관련 필드
    private String 사용자이름;
    private String 작물명;
    
    // 화면 전용 필드
    private String 검색키워드;
    private String 활동필터; // 활동 유형별 필터링용
    private LocalDate 시작날짜; // 기간 검색용
    private LocalDate 종료날짜; // 기간 검색용
}