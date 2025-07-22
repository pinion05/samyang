package com.farm404.samyang.UserRepository;

import org.apache.ibatis.annotations.Mapper;

import com.farm404.samyang.UserDTO.UserDTO;

@Mapper
public interface UserMapper {
	
	public int getLogin(UserDTO userVo);
	// 사용자가 있냐 없냐 

}
