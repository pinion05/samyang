package com.farm404.samyang.service;

import com.farm404.samyang.dto.PaymentMethodDTO;
import com.farm404.samyang.mapper.PaymentMethodMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PaymentMethodService {
    
    @Autowired
    private PaymentMethodMapper paymentMethodMapper;
    
    public PaymentMethodDTO getPaymentMethodById(Integer paymentMethodId) {
        return paymentMethodMapper.selectPaymentMethodById(paymentMethodId);
    }
    
    public List<PaymentMethodDTO> getPaymentMethodsByUserId(Integer userId) {
        return paymentMethodMapper.selectPaymentMethodsByUserId(userId);
    }
    
    public List<PaymentMethodDTO> getAllPaymentMethods() {
        return paymentMethodMapper.selectAllPaymentMethods();
    }
    
    public List<PaymentMethodDTO> getPaymentMethodsByType(Integer userId, String paymentType) {
        return paymentMethodMapper.selectPaymentMethodsByType(userId, paymentType);
    }
    
    public boolean registerPaymentMethod(PaymentMethodDTO paymentMethod) {
        // 카드번호 중복 체크
        if (isCardNumberExists(paymentMethod.getCardNumber(), null)) {
            return false;
        }
        return paymentMethodMapper.insertPaymentMethod(paymentMethod) > 0;
    }
    
    public boolean updatePaymentMethod(PaymentMethodDTO paymentMethod) {
        // 카드번호 중복 체크 (자기 자신 제외)
        if (isCardNumberExists(paymentMethod.getCardNumber(), paymentMethod.getPaymentMethodId())) {
            return false;
        }
        return paymentMethodMapper.updatePaymentMethod(paymentMethod) > 0;
    }
    
    public boolean deletePaymentMethod(Integer paymentMethodId) {
        return paymentMethodMapper.deletePaymentMethod(paymentMethodId) > 0;
    }
    
    public PaymentMethodDTO getDefaultPaymentMethod(Integer userId) {
        return paymentMethodMapper.selectDefaultPaymentMethod(userId);
    }
    
    public boolean isCardNumberExists(String cardNumber, Integer excludeId) {
        return paymentMethodMapper.checkCardNumberExists(cardNumber, excludeId) > 0;
    }
    
    // 카드번호 유효성 검사 (Luhn 알고리즘)
    public boolean isValidCardNumber(String cardNumber) {
        if (cardNumber == null || cardNumber.isEmpty()) {
            return false;
        }
        
        // 공백과 대시 제거
        cardNumber = cardNumber.replaceAll("[\\s-]", "");
        
        // 숫자만 포함되어 있는지 확인
        if (!cardNumber.matches("\\d+")) {
            return false;
        }
        
        // 카드번호 길이 확인 (일반적으로 13-19자리)
        if (cardNumber.length() < 13 || cardNumber.length() > 19) {
            return false;
        }
        
        // Luhn 알고리즘 적용
        int sum = 0;
        boolean alternate = false;
        
        for (int i = cardNumber.length() - 1; i >= 0; i--) {
            int digit = Integer.parseInt(cardNumber.substring(i, i + 1));
            
            if (alternate) {
                digit *= 2;
                if (digit > 9) {
                    digit = (digit % 10) + 1;
                }
            }
            
            sum += digit;
            alternate = !alternate;
        }
        
        return (sum % 10 == 0);
    }
    
    // 카드 유효기간 검사
    public boolean isValidExpiryDate(String expiryDate) {
        if (expiryDate == null || expiryDate.isEmpty()) {
            return false;
        }
        
        // MM/YY 형식 확인
        if (!expiryDate.matches("\\d{2}/\\d{2}")) {
            return false;
        }
        
        String[] parts = expiryDate.split("/");
        int month = Integer.parseInt(parts[0]);
        int year = Integer.parseInt("20" + parts[1]);
        
        // 월 유효성 확인
        if (month < 1 || month > 12) {
            return false;
        }
        
        // 현재 날짜와 비교
        java.time.LocalDate now = java.time.LocalDate.now();
        java.time.LocalDate expiry = java.time.LocalDate.of(year, month, 1).plusMonths(1).minusDays(1);
        
        return !expiry.isBefore(now);
    }
    
    // CVV 유효성 검사
    public boolean isValidCvv(String cvv, String cardType) {
        if (cvv == null || cvv.isEmpty()) {
            return false;
        }
        
        // American Express는 4자리, 나머지는 3자리
        if ("AMEX".equalsIgnoreCase(cardType)) {
            return cvv.matches("\\d{4}");
        } else {
            return cvv.matches("\\d{3}");
        }
    }
}