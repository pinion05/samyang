package com.farm404.samyang.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.mapper.UserMapper;

/**
 * 사용자 서비스 클래스
 * 사용자 관련 비즈니스 로직 처리
 */
@Service
@Transactional
public class UserService {
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 사용자 등록
     */
    public int registerUser(UserDTO user) {
        // 이메일 중복 체크
        if (userMapper.checkEmailExists(user.get이메일()) > 0) {
            throw new RuntimeException("이미 사용 중인 이메일입니다.");
        }
        
        // 기본값 설정
        if (user.get관리자여부() == null) {
            user.set관리자여부(false);
        }
        
        return userMapper.insertUser(user);
    }
    
    /**
     * 사용자 목록 조회
     */
    @Transactional(readOnly = true)
    public List<UserDTO> getUserList(UserDTO searchCondition) {
        return userMapper.selectUserList(searchCondition);
    }
    
    /**
     * 사용자 상세 조회
     */
    @Transactional(readOnly = true)
    public UserDTO getUserById(Integer 사용자ID) {
        UserDTO user = userMapper.selectUserById(사용자ID);
        if (user == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        return user;
    }
    
    /**
     * 사용자 정보 수정
     */
    public int updateUser(UserDTO user) {
        // 존재하는 사용자인지 확인
        UserDTO existingUser = userMapper.selectUserById(user.get사용자ID());
        if (existingUser == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        
        // 이메일 변경 시 중복 체크
        if (!existingUser.get이메일().equals(user.get이메일())) {
            if (userMapper.checkEmailExists(user.get이메일()) > 0) {
                throw new RuntimeException("이미 사용 중인 이메일입니다.");
            }
        }
        
        return userMapper.updateUser(user);
    }
    
    /**
     * 사용자 삭제
     */
    public int deleteUser(Integer 사용자ID) {
        // 존재하는 사용자인지 확인
        UserDTO existingUser = userMapper.selectUserById(사용자ID);
        if (existingUser == null) {
            throw new RuntimeException("사용자를 찾을 수 없습니다.");
        }
        
        return userMapper.deleteUser(사용자ID);
    }
    
    /**
     * 로그인 처리
     */
    @Transactional(readOnly = true)
    public UserDTO login(String 이메일, String 비밀번호) {
        // 사용자 조회
        UserDTO user = userMapper.selectUserByEmail(이메일);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 이메일입니다.");
        }
        
        // 비밀번호 확인 (실제 환경에서는 해시화된 비밀번호 비교)
        if (!user.get비밀번호().equals(비밀번호)) {
            throw new RuntimeException("비밀번호가 일치하지 않습니다.");
        }
        
        // 비밀번호는 응답에서 제거
        user.set비밀번호(null);
        return user;
    }
    
    /**
     * 로그인 검증 (기존 메서드 호환)
     */
    @Transactional(readOnly = true)
    public int getLogin(String 이메일, String 비밀번호) {
        return userMapper.getLogin(이메일, 비밀번호);
    }
    
    /**
     * 이메일 중복 체크
     */
    @Transactional(readOnly = true)
    public boolean checkEmailExists(String 이메일) {
        return userMapper.checkEmailExists(이메일) > 0;
    }
    
    /**
     * 사용자 수 조회
     */
    @Transactional(readOnly = true)
    public int getUserCount(UserDTO searchCondition) {
        return userMapper.selectUserCount(searchCondition);
    }
}