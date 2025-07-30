package com.farm404.samyang.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class FarmingDiaryDTO {
    private Integer farmingDiaryId;
    private Integer userId;
    // TODO: [날짜타입혼용] LocalDate와 LocalDateTime 혼용 중
    // 이상적으로는 모든 날짜 필드를 LocalDateTime으로 통일
    private LocalDate date;
    private String activityType;
    private String content;
    private String photoUrl;
    private LocalDateTime createdAt;
    
    // 조인 시 사용될 추가 필드
    private String userName;

    // Getters and Setters
    public Integer getFarmingDiaryId() {
        return farmingDiaryId;
    }

    public void setFarmingDiaryId(Integer farmingDiaryId) {
        this.farmingDiaryId = farmingDiaryId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}