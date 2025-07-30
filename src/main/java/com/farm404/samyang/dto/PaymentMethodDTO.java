package com.farm404.samyang.dto;

import java.time.LocalDateTime;

public class PaymentMethodDTO {
    private Integer paymentMethodId;
    private Integer userId;
    // TODO: [DB매핑오류] paymentType 컬럼이 DB에 존재하지 않음
    // 실제 DB에는 CardNumber, ExpiryDate, CVC만 존재
    private String paymentType;
    private String cardNumber;
    // TODO: [DB매핑오류] cardHolderName 컬럼이 DB에 존재하지 않음
    private String cardHolderName;
    private String expiryDate;
    // TODO: [최소수정] cvv -> cvc로 변경 필요 (DB 컬럼명은 CVC)
    private String cvv;
    // TODO: [DB매핑오류] billingAddress 컬럼이 DB에 존재하지 않음
    private String billingAddress;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
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
    
    public String getCvv() {
        return cvv;
    }
    
    public void setCvv(String cvv) {
        this.cvv = cvv;
    }
    
    // TODO: [임시해결] DB의 CVC 컬럼과 매핑을 위한 getter/setter 추가
    // 이상적으로는 cvv 대신 cvc로 통일
    public String getCvc() {
        return cvv;
    }
    
    public void setCvc(String cvc) {
        this.cvv = cvc;
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