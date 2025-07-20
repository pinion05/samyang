package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

/**
 * 관리자 엔티티 클래스
 * MySQL 데이터베이스의 admin 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "admin")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Admin {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "admin_id")
    private Long adminId;
    
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;
    
    // 관계 매핑
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
    
    @OneToMany(mappedBy = "admin", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Product> products;
}