package com.farm404.samyang.dto;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 결제수단 DTO 클래스
 * 데이터베이스 PaymentMethod 테이블과 매핑
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentMethodDTO {
    
    private Integer paymentMethodId;
    private Integer userId;
    private String methodType;  // CARD, BANK_TRANSFER, MOBILE_PAY 등
    private String provider;    // 카드사명, 은행명 등
    private String accountInfo; // 계좌번호, 카드번호 등 (암호화된 형태)
    private Boolean isDefault;  // 기본 결제수단 여부
    private LocalDateTime createdAt;
    
    // 조인 관련 필드
    private String userName; // 사용자명 표시용
}