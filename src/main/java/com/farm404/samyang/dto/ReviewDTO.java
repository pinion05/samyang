package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    // DB 스키마와 일치하도록 필드명 변경
    private Integer orderItemId;
    private Integer rating;
    private String content;  // DB의 Content 필드와 일치
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // Join을 위한 추가 필드
    private String userName;
    private String productName;
    
    // Getters and Setters
    public Integer getReviewId() {
        return reviewId;
    }
    
    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getOrderItemId() {
        return orderItemId;
    }
    
    public void setOrderItemId(Integer orderItemId) {
        this.orderItemId = orderItemId;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public Integer getProductId() {
        return orderItemId;
    }
    
    @Deprecated
    public void setProductId(Integer productId) {
        this.orderItemId = productId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public String getComment() {
        return content;
    }
    
    @Deprecated
    public void setComment(String comment) {
        this.content = comment;
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
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getProductName() {
        return productName;
    }
    
    public void setProductName(String productName) {
        this.productName = productName;
    }
    
    // TODO: [이상적개선] 평점 변환 로직은 Service 레이어로 이동
    // DTO는 데이터 전달용으로만 사용
    // 평점을 별표로 변환하는 메서드
    public String getStarRating() {
        if (rating == null || rating < 1) return "";
        
        StringBuilder stars = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            if (i < rating) {
                stars.append("★");
            } else {
                stars.append("☆");
            }
        }
        return stars.toString();
    }
    
    // 평점 텍스트 변환
    public String getRatingText() {
        if (rating == null) return "평가 없음";
        
        switch (rating) {
            case 1: return "매우 나쁨";
            case 2: return "나쁨";
            case 3: return "보통";
            case 4: return "좋음";
            case 5: return "매우 좋음";
            default: return "평가 없음";
        }
    }
}