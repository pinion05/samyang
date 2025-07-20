package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

/**
 * 작물 엔티티 클래스
 * MySQL 데이터베이스의 crop 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "crop")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Crop {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "crop_id")
    private Long cropId;
    
    @Column(name = "user_id", nullable = false)
    private Long userId;
    
    @Column(name = "crop_name", nullable = false, length = 255)
    private String cropName;
    
    @Column(name = "planting_date")
    private LocalDate plantingDate;
    
    @Column(name = "expected_harvest_date")
    private LocalDate expectedHarvestDate;
    
    // 관계 매핑
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private User user;
}