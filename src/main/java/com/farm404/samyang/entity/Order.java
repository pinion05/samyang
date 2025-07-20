package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 주문 엔티티 클래스
 * MySQL 데이터베이스의 orders 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Order {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Long orderId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @CreationTimestamp
    @Column(name = "order_datetime", nullable = false, updatable = false)
    private LocalDateTime orderDatetime;
    
    @Column(name = "total_payment_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalPaymentAmount;
    
    @Column(name = "status", length = 50)
    private String status; // 예: '주문 완료', '배송 중', '배송 완료', '취소'
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<OrderProduct> orderProducts;
    
    /**
     * 엔티티가 저장되기 전에 실행되는 메서드
     */
    @PrePersist
    protected void onCreate() {
        if (orderDatetime == null) {
            orderDatetime = LocalDateTime.now();
        }
    }
}