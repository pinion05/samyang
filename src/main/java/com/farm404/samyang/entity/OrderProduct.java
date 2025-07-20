package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 주문상품 엔티티 클래스
 * MySQL 데이터베이스의 order_product 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "order_product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderProduct {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_product_id")
    private Long orderProductId;
    
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "quantity", nullable = false)
    private Integer quantity;
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", insertable = false, updatable = false)
    private Order order;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    
    @OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
}