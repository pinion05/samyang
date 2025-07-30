package com.farm404.samyang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.mapper.UserMapper;

@Service
public class UserService {
	
	@Autowired
	private UserMapper userMapper;
	
	public UserDTO getUserById(String id) {
		return userMapper.selectUserById(id);
	}
	
	public UserDTO getUserByUsername(String username) {
		return userMapper.selectUserByUsername(username);
	}
	
	public List<UserDTO> getAllUsers() {
		return userMapper.selectAllUsers();
	}
	
	public boolean registerUser(UserDTO user) {
		return userMapper.insertUser(user) > 0;
	}
	
	public boolean updateUser(UserDTO user) {
		return userMapper.updateUser(user) > 0;
	}
	
	public boolean deleteUser(String id) {
		return userMapper.deleteUser(id) > 0;
	}
	
	public UserDTO login(String username, String password) {
		return userMapper.checkLogin(username, password);
	}
	
	public List<UserDTO> getUserList(UserDTO searchCondition) {
		return userMapper.selectUserList(searchCondition);
	}
	
	public int getUserCount(UserDTO searchCondition) {
		return userMapper.selectUserCount(searchCondition);
	}
	
	public boolean checkEmailExists(String email) {
		return userMapper.checkEmailExists(email) > 0;
	}
	
	/**
	 * 프로필 업데이트 (비밀번호 제외)
	 */
	public boolean updateProfile(UserDTO user) {
		return userMapper.updateUserProfile(user) > 0;
	}
	
	/**
	 * 비밀번호 변경
	 */
	public boolean changePassword(String userId, String currentPassword, String newPassword) {
		// 현재 비밀번호 확인
		UserDTO user = userMapper.selectUserById(userId);
		if (user == null) {
			return false;
		}
		
		// 현재 비밀번호 검증
		UserDTO loginResult = userMapper.checkLogin(user.getLoginId(), currentPassword);
		if (loginResult == null) {
			return false;
		}
		
		// 비밀번호 업데이트
		return userMapper.updatePassword(userId, newPassword) > 0;
	}
}