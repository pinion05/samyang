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
    
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String phone;
    private String address;
    private Boolean isAdmin;
    private LocalDateTime createdAt;
    
    // 화면 전용 필드 (DB 매핑 제외)
    private String passwordConfirm; // 회원가입시 비밀번호 확인용
    private String searchKeyword;   // 검색용
}