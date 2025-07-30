package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.UserDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {
    // 사용자 정보 조회
    UserDTO selectUserById(String id);

    // 사용자 이름으로 조회
    UserDTO selectUserByUsername(String username);

    // 모든 사용자 조회
    List<UserDTO> selectAllUsers();

    // 사용자 등록
    int insertUser(UserDTO user);

    // 사용자 정보 수정
    int updateUser(UserDTO user);

    // 사용자 삭제
    int deleteUser(String id);

    // 로그인 체크
    UserDTO checkLogin(@Param("username") String username, @Param("password") String password);
    
    // 사용자 리스트 조회
    List<UserDTO> selectUserList(UserDTO searchCondition);
    
    // 사용자 수 조회
    int selectUserCount(UserDTO searchCondition);
    
    // 이메일 중복 체크
    int checkEmailExists(String email);
    
    // 프로필 업데이트 (비밀번호 제외)
    int updateUserProfile(UserDTO user);
    
    // 비밀번호 업데이트
    int updatePassword(@Param("userId") String userId, @Param("password") String password);
}