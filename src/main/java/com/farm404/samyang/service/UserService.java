package com.farm404.samyang.service;

import com.farm404.samyang.entity.User;
import com.farm404.samyang.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 클래스
 * MySQL 데이터베이스와의 상호작용을 담당합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class UserService {
    
    private final UserRepository userRepository;
    
    /**
     * 모든 사용자 조회
     * @return 사용자 목록
     */
    public List<User> getAllUsers() {
        log.info("모든 사용자 조회 요청");
        return userRepository.findAll();
    }
    
    /**
     * ID로 사용자 조회
     * @param id 사용자 ID
     * @return 사용자 정보 (Optional)
     */
    public Optional<User> getUserById(Long id) {
        log.info("사용자 조회 요청 - ID: {}", id);
        return userRepository.findById(id);
    }
    
    /**
     * 사용자명으로 사용자 조회
     * @param username 사용자명
     * @return 사용자 정보 (Optional)
     */
    public Optional<User> getUserByUsername(String username) {
        log.info("사용자 조회 요청 - 사용자명: {}", username);
        return userRepository.findByUsername(username);
    }
    
    /**
     * 이메일로 사용자 조회
     * @param email 이메일 주소
     * @return 사용자 정보 (Optional)
     */
    public Optional<User> getUserByEmail(String email) {
        log.info("사용자 조회 요청 - 이메일: {}", email);
        return userRepository.findByEmail(email);
    }
    
    /**
     * 새 사용자 생성
     * @param user 사용자 정보
     * @return 생성된 사용자 정보
     */
    @Transactional
    public User createUser(User user) {
        log.info("새 사용자 생성 요청 - 사용자명: {}, 이메일: {}", user.getUsername(), user.getEmail());
        
        // 중복 검사
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new IllegalArgumentException("이미 존재하는 사용자명입니다: " + user.getUsername());
        }
        
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + user.getEmail());
        }
        
        User savedUser = userRepository.save(user);
        log.info("사용자 생성 완료 - ID: {}", savedUser.getUserId());
        return savedUser;
    }
    
    /**
     * 사용자 정보 업데이트
     * @param id 사용자 ID
     * @param userDetails 업데이트할 사용자 정보
     * @return 업데이트된 사용자 정보
     */
    @Transactional
    public User updateUser(Long id, User userDetails) {
        log.info("사용자 정보 업데이트 요청 - ID: {}", id);
        
        User user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id));
        
        // 업데이트 가능한 필드들
        if (userDetails.getEmail() != null) {
            user.setEmail(userDetails.getEmail());
        }
        if (userDetails.getDeliveryAddress() != null) {
            user.setDeliveryAddress(userDetails.getDeliveryAddress());
        }
        if (userDetails.getIsAdmin() != null) {
            user.setIsAdmin(userDetails.getIsAdmin());
        }
        
        User updatedUser = userRepository.save(user);
        log.info("사용자 정보 업데이트 완료 - ID: {}", updatedUser.getUserId());
        return updatedUser;
    }
    
    /**
     * 사용자 삭제
     * @param id 사용자 ID
     */
    @Transactional
    public void deleteUser(Long id) {
        log.info("사용자 삭제 요청 - ID: {}", id);
        
        if (!userRepository.existsById(id)) {
            throw new IllegalArgumentException("사용자를 찾을 수 없습니다: " + id);
        }
        
        userRepository.deleteById(id);
        log.info("사용자 삭제 완료 - ID: {}", id);
    }
    
    /**
     * 관리자 사용자 목록 조회
     * @return 관리자 사용자 목록
     */
    public List<User> getAdminUsers() {
        log.info("관리자 사용자 목록 조회 요청");
        return userRepository.findByIsAdmin(true);
    }
    
    /**
     * 일반 사용자 목록 조회
     * @return 일반 사용자 목록
     */
    public List<User> getRegularUsers() {
        log.info("일반 사용자 목록 조회 요청");
        return userRepository.findByIsAdmin(false);
    }
    
    /**
     * 사용자명으로 사용자 검색
     * @param username 검색할 사용자명
     * @return 검색된 사용자 목록
     */
    public List<User> searchUsersByUsername(String username) {
        log.info("사용자명 검색 요청 - 검색어: {}", username);
        return userRepository.findByUsernameContaining(username);
    }
}