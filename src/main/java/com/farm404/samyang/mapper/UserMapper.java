package com.farm404.samyang.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import com.farm404.samyang.dto.UserDTO;

/**
 * 사용자 매퍼 인터페이스
 * 사용자 테이블에 대한 CRUD 작업 정의
 */
@Mapper
public interface UserMapper {
    
    /**
     * 사용자 등록
     */
    int insertUser(UserDTO user);
    
    /**
     * 사용자 목록 조회
     */
    List<UserDTO> selectUserList(UserDTO searchCondition);
    
    /**
     * 사용자 상세 조회
     */
    UserDTO selectUserById(Integer 사용자ID);
    
    /**
     * 이메일로 사용자 조회 (로그인용)
     */
    UserDTO selectUserByEmail(String 이메일);
    
    /**
     * 사용자 정보 수정
     */
    int updateUser(UserDTO user);
    
    /**
     * 사용자 삭제
     */
    int deleteUser(Integer 사용자ID);
    
    /**
     * 로그인 검증 (기존 메서드 유지)
     */
    int getLogin(@Param("이메일") String 이메일, @Param("비밀번호") String 비밀번호);
    
    /**
     * 이메일 중복 체크
     */
    int checkEmailExists(String 이메일);
    
    /**
     * 전체 사용자 수 조회
     */
    int selectUserCount(UserDTO searchCondition);
}