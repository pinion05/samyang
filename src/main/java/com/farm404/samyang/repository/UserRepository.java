package com.farm404.samyang.repository;

import com.farm404.samyang.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 데이터 접근을 위한 리포지토리 인터페이스
 * Spring Data JPA를 사용하여 MySQL 데이터베이스에 접근합니다.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    
    /**
     * 사용자명으로 사용자 조회
     * @param username 사용자명
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByUsername(String username);
    
    /**
     * 이메일로 사용자 조회
     * @param email 이메일 주소
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByEmail(String email);
    
    /**
     * 사용자명 또는 이메일로 사용자 조회
     * @param username 사용자명
     * @param email 이메일 주소
     * @return 사용자 정보 (Optional)
     */
    Optional<User> findByUsernameOrEmail(String username, String email);
    
    /**
     * 관리자 여부별 사용자 목록 조회
     * @param isAdmin 관리자 여부
     * @return 사용자 목록
     */
    List<User> findByIsAdmin(Boolean isAdmin);
    
    /**
     * 사용자명 존재 여부 확인
     * @param username 사용자명
     * @return 존재 여부
     */
    boolean existsByUsername(String username);
    
    /**
     * 이메일 존재 여부 확인
     * @param email 이메일 주소
     * @return 존재 여부
     */
    boolean existsByEmail(String email);
    
    /**
     * 전체 사용자 수 조회
     * @return 전체 사용자 수
     */
    @Query("SELECT COUNT(u) FROM User u")
    long countAllUsers();
    
    /**
     * 사용자명으로 사용자 검색 (부분 일치)
     * @param username 검색할 사용자명
     * @return 사용자 목록
     */
    @Query("SELECT u FROM User u WHERE u.username LIKE %:username%")
    List<User> findByUsernameContaining(@Param("username") String username);
}