package com.farm404.samyang.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.UserMapper;

/**
 * 사용자 서비스 클래스
 * 사용자 관련 비즈니스 로직 처리
 */
@Service
@Transactional
public class UserService {
    
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 사용자 등록
     */
    public int registerUser(UserDTO user) {
        logger.info("사용자 등록 시도: {}", user.get이메일());
        
        try {
            // 이메일 중복 체크
            if (userMapper.checkEmailExists(user.get이메일()) > 0) {
                logger.warn("중복된 이메일로 등록 시도: {}", user.get이메일());
                throw new SamyangException(ErrorCode.USER_EMAIL_DUPLICATED);
            }
            
            // 기본값 설정
            if (user.get관리자여부() == null) {
                user.set관리자여부(false);
            }
            
            int result = userMapper.insertUser(user);
            logger.info("사용자 등록 완료: {} (ID: {})", user.get이메일(), user.get사용자ID());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("사용자 등록 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
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
        logger.debug("사용자 조회 요청: ID {}", 사용자ID);
        
        UserDTO user = userMapper.selectUserById(사용자ID);
        if (user == null) {
            logger.warn("존재하지 않는 사용자 ID 조회 시도: {}", 사용자ID);
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        logger.debug("사용자 조회 완료: {} ({})", user.get이름(), user.get이메일());
        return user;
    }
    
    /**
     * 사용자 정보 수정
     */
    public int updateUser(UserDTO user) {
        logger.info("사용자 정보 수정 시도: ID {}", user.get사용자ID());
        
        try {
            // 존재하는 사용자인지 확인
            UserDTO existingUser = userMapper.selectUserById(user.get사용자ID());
            if (existingUser == null) {
                logger.warn("존재하지 않는 사용자 ID 수정 시도: {}", user.get사용자ID());
                throw new SamyangException(ErrorCode.USER_NOT_FOUND);
            }
            
            // 이메일 변경 시 중복 체크
            if (!existingUser.get이메일().equals(user.get이메일())) {
                if (userMapper.checkEmailExists(user.get이메일()) > 0) {
                    logger.warn("이미 사용중인 이메일로 수정 시도: {}", user.get이메일());
                    throw new SamyangException(ErrorCode.USER_EMAIL_DUPLICATED);
                }
            }
            
            int result = userMapper.updateUser(user);
            logger.info("사용자 정보 수정 완료: {} ({})", user.get이름(), user.get이메일());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("사용자 정보 수정 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 사용자 삭제
     */
    public int deleteUser(Integer 사용자ID) {
        logger.info("사용자 삭제 시도: ID {}", 사용자ID);
        
        try {
            // 존재하는 사용자인지 확인
            UserDTO existingUser = userMapper.selectUserById(사용자ID);
            if (existingUser == null) {
                logger.warn("존재하지 않는 사용자 ID 삭제 시도: {}", 사용자ID);
                throw new SamyangException(ErrorCode.USER_NOT_FOUND);
            }
            
            int result = userMapper.deleteUser(사용자ID);
            logger.info("사용자 삭제 완료: {} ({})", existingUser.get이름(), existingUser.get이메일());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("사용자 삭제 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 로그인 처리
     */
    @Transactional(readOnly = true)
    public UserDTO login(String 이메일, String 비밀번호) {
        logger.info("로그인 시도: {}", 이메일);
        
        try {
            // 사용자 조회
            UserDTO user = userMapper.selectUserByEmail(이메일);
            if (user == null) {
                logger.warn("존재하지 않는 이메일로 로그인 시도: {}", 이메일);
                throw new SamyangException(ErrorCode.USER_EMAIL_NOT_FOUND);
            }
            
            // 비밀번호 확인 (실제 환경에서는 해시화된 비밀번호 비교)
            if (!user.get비밀번호().equals(비밀번호)) {
                logger.warn("비밀번호 불일치: {}", 이메일);
                throw new SamyangException(ErrorCode.USER_PASSWORD_MISMATCH);
            }
            
            // 비밀번호는 응답에서 제거
            user.set비밀번호(null);
            logger.info("로그인 성공: {} ({})", user.get이름(), user.get이메일());
            return user;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("로그인 처리 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
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