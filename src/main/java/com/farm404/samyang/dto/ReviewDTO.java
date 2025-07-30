package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Integer reviewId;
    private Integer userId;
    // TODO: [DB매핑오류] DB에는 OrderItemID가 있지만 OrderItem 테이블이 없음
    // productId로 사용 중이지만 Product 테이블도 없음
    // 이상적으로는 Product, Order, OrderItem 테이블 생성 후 사용
    private Integer productId;
    private Integer rating;
    private String comment;
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
    
    public Integer getProductId() {
        return productId;
    }
    
    public void setProductId(Integer productId) {
        this.productId = productId;
    }
    
    public Integer getRating() {
        return rating;
    }
    
    public void setRating(Integer rating) {
        this.rating = rating;
    }
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
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