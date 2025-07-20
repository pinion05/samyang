package com.farm404.samyang.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * 공지사항 엔티티 클래스
 * MySQL 데이터베이스의 notice 테이블과 매핑됩니다.
 */
@Entity
@Table(name = "notice")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notice {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Long noticeId;
    
    @Column(name = "notice_name", nullable = false, unique = true, length = 100)
    private String noticeName; // 예: '공지사항', '자유게시판'
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    
    @Column(name = "photo_url", length = 255)
    private String photoUrl;
}