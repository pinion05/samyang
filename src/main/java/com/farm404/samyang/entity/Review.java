package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

/**
 * 리뷰 엔티티 클래스
 * MySQL 데이터베이스의 review 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "review")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Review {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long reviewId;
    
    @Column(name = "product_id", nullable = false)
    private Long productId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "order_product_id", nullable = false, unique = true)
    private Long orderProductId; // 한 주문상품에 하나의 리뷰만 가능
    
    @Column(name = "rating", nullable = false)
    private Integer rating; // 1~5
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
    
    @CreationTimestamp
    @Column(name = "created_date", nullable = false, updatable = false)
    private LocalDateTime createdDate;
    
    @Column(name = "image_url", length = 255)
    private String imageUrl;
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", insertable = false, updatable = false)
    private Product product;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_product_id", insertable = false, updatable = false)
    private OrderProduct orderProduct;
    
    /**
     * 엔티티가 저장되기 전에 실행되는 메서드
     */
    @PrePersist
    protected void onCreate() {
        if (createdDate == null) {
            createdDate = LocalDateTime.now();
        }
        // 평점 유효성 검사
        if (rating != null && (rating < 1 || rating > 5)) {
            throw new IllegalArgumentException("평점은 1~5 사이의 값이어야 합니다.");
        }
    }
}