package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class PaymentMethodDTO {
    // 기본 필드 (DB 스키마와 일치)
    private Integer paymentMethodId;
    private Integer userId;
    private String cardNumber;
    private String expiryDate;
    private String cvc; // DB 컬럼명과 일치
    
    // 화면 표시용 추가 필드 (실제 DB에는 없음)
    @Deprecated
    private String paymentType; // UI에서만 사용
    @Deprecated
    private String cardHolderName; // UI에서만 사용
    @Deprecated
    private String billingAddress; // UI에서만 사용
    @Deprecated
    private String cvv; // cvc로 통합
    @Deprecated
    private LocalDateTime createdAt; // 현재 사용 안함
    @Deprecated
    private LocalDateTime updatedAt; // 현재 사용 안함
    
    // Join을 위한 추가 필드
    private String userName;
    
    // Getters and Setters
    public Integer getPaymentMethodId() {
        return paymentMethodId;
    }
    
    public void setPaymentMethodId(Integer paymentMethodId) {
        this.paymentMethodId = paymentMethodId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getPaymentType() {
        return paymentType;
    }
    
    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }
    
    public String getCardNumber() {
        return cardNumber;
    }
    
    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }
    
    public String getCardHolderName() {
        return cardHolderName;
    }
    
    public void setCardHolderName(String cardHolderName) {
        this.cardHolderName = cardHolderName;
    }
    
    public String getExpiryDate() {
        return expiryDate;
    }
    
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    
    public String getCvc() {
        return cvc;
    }
    
    public void setCvc(String cvc) {
        this.cvc = cvc;
    }
    
    // 호환성을 위한 deprecated 메소드
    @Deprecated
    public String getCvv() {
        return cvc; // cvv를 cvc로 매핑
    }
    
    @Deprecated
    public void setCvv(String cvv) {
        this.cvc = cvv;
    }
    
    public String getBillingAddress() {
        return billingAddress;
    }
    
    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
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
    
    // 카드번호 마스킹 메서드
    public String getMaskedCardNumber() {
        if (cardNumber != null && cardNumber.length() >= 4) {
            return "**** **** **** " + cardNumber.substring(cardNumber.length() - 4);
        }
        return "****";
    }
}