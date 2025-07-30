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
}