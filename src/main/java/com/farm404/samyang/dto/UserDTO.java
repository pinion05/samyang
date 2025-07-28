package com.farm404.samyang.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 사용자 DTO 클래스
 * 데이터베이스 사용자 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    
    private Integer 사용자ID;
    private String 이름;
    private String 이메일;
    private String 비밀번호;
    private String 전화번호;
    private String 주소;
    private Boolean 관리자여부;
    private LocalDateTime 가입일시;
    
    // 화면 전용 필드 (DB 매핑 제외)
    private String 비밀번호확인; // 회원가입시 비밀번호 확인용
    private String 검색키워드;   // 검색용
}