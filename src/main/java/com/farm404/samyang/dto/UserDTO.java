package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class UserDTO {
    // TODO: [최소수정] id 필드는 제거하고 userId로 통일 필요
    // 이상적으로는 userId 하나만 사용하여 혼란 방지
    private String id;
    private String username;
    private String password;
    private String email;
    private String name;
    // TODO: [DB매핑] phone 필드는 DB의 PhoneNumber와 매핑되어야 함
    private String phone;
    private String address;
    // TODO: [DB매핑] role 필드는 DB의 IsAdmin(tinyint)과 매핑 필요
    // 이상적으로는 Boolean isAdmin으로 변경
    private String role; // 'USER' or 'ADMIN'
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String passwordConfirm; // 비밀번호 확인용
    private Integer userId; // 호환성을 위해 추가
    
    // TODO: [필수추가] loginId 필드 추가 필요 (현재 UserService에서 참조중)
    // 또는 email을 로그인 ID로 사용하도록 전체 로직 변경

    // Getters and Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    // TODO: [임시해결] loginId getter 추가 - email을 loginId로 사용
    public String getLoginId() {
        return email;
    }
    
    // TODO: [임시해결] loginId setter 추가
    public void setLoginId(String loginId) {
        // loginId 대신 email 사용
        this.email = loginId;
    }
}