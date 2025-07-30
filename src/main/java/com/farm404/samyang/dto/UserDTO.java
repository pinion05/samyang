package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class UserDTO {
    // 통합된 사용자 ID
    private Integer userId;
    private String name;
    private String email;
    private String password;
    private String phoneNumber; // DB 필드명과 일치
    private String address;
    private Boolean isAdmin; // DB의 tinyint(1)과 매핑
    private LocalDateTime createdAt;
    
    // 추가 필드 (화면 표시용)
    private String passwordConfirm; // 비밀번호 확인용
    
    
    // 호환성을 위한 deprecated 필드
    @Deprecated
    private String id; // userId로 통합 예정
    @Deprecated  
    private String username; // name으로 통합 예정
    @Deprecated
    private String phone; // phoneNumber로 통합 예정
    @Deprecated
    private String role; // isAdmin으로 통합 예정
    @Deprecated
    private LocalDateTime updatedAt; // 현재 사용 안함

    // 기본 Getters and Setters
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    
    // 호환성 메소드 - deprecated 필드 지원
    @Deprecated
    public String getId() {
        return userId != null ? userId.toString() : null;
    }

    @Deprecated
    public void setId(String id) {
        try {
            this.userId = id != null ? Integer.parseInt(id) : null;
        } catch (NumberFormatException e) {
            this.userId = null;
        }
    }

    @Deprecated
    public String getUsername() {
        return name; // username을 name으로 매핑
    }

    @Deprecated
    public void setUsername(String username) {
        this.name = username;
    }

    @Deprecated
    public String getPhone() {
        return phoneNumber;
    }

    @Deprecated
    public void setPhone(String phone) {
        this.phoneNumber = phone;
    }

    @Deprecated
    public String getRole() {
        return isAdmin != null && isAdmin ? "ADMIN" : "USER";
    }

    @Deprecated
    public void setRole(String role) {
        this.isAdmin = "ADMIN".equalsIgnoreCase(role);
    }

    @Deprecated
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Deprecated
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    // loginId는 email로 매핑
    public String getLoginId() {
        return email;
    }
    
    public void setLoginId(String loginId) {
        this.email = loginId;
    }
}