package com.farm404.samyang.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.farm404.samyang.UserDTO.UserDTO;
import com.farm404.samyang.UserRepository.UserMapper;


@Service
public class UserService {
	
	@Autowired
	private UserMapper uMapper;
	
	public int getLogin(UserDTO userVo) {
		
		return uMapper.getLogin(userVo);
		
	}
}
