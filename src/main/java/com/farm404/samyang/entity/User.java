package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 사용자 엔티티 클래스
 * MySQL 데이터베이스의 users 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "user")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "password")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "username", nullable = false, unique = true, length = 255)
    private String username;
    
    @Column(name = "password", nullable = false, length = 255)
    private String password;
    
    @Column(name = "email", nullable = false, unique = true, length = 255)
    private String email;
    
    @CreationTimestamp
    @Column(name = "join_date", nullable = false, updatable = false)
    private LocalDateTime joinDate;
    
    @Column(name = "is_admin", nullable = false)
    @Builder.Default
    private Boolean isAdmin = false;
    
    @UpdateTimestamp
    @Column(name = "user_info_updated_at", nullable = false)
    private LocalDateTime userInfoUpdatedAt;
    
    @Column(name = "delivery_address", nullable = false, length = 255)
    private String deliveryAddress;
    
    // 관계 매핑
    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Admin admin;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<PaymentMethod> paymentMethods;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Crop> crops;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<FarmDiary> farmDiaries;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Post> posts;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Comment> comments;
    
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Review> reviews;
    
    @OneToMany(mappedBy = "reporter", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Report> reports;
    
    /**
     * 엔티티가 저장되기 전에 실행되는 메서드
     */
    @PrePersist
    protected void onCreate() {
        if (joinDate == null) {
            joinDate = LocalDateTime.now();
        }
        if (userInfoUpdatedAt == null) {
            userInfoUpdatedAt = LocalDateTime.now();
        }
    }
    
    /**
     * 엔티티가 업데이트되기 전에 실행되는 메서드
     */
    @PreUpdate
    protected void onUpdate() {
        userInfoUpdatedAt = LocalDateTime.now();
    }
}