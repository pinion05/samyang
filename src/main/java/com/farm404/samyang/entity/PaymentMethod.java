package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 결제수단 엔티티 클래스
 * MySQL 데이터베이스의 payment_method 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "payment_method")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class PaymentMethod {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "payment_method_id")
    private Long paymentMethodId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "card_type", length = 50)
    private String cardType;
    
    @Column(name = "card_last_four_digits", length = 4)
    private String cardLastFourDigits;
    
    @Column(name = "expiry_date")
    private LocalDate expiryDate;
    
    @Column(name = "is_default_payment", nullable = false)
    @Builder.Default
    private Boolean isDefaultPayment = false;
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}