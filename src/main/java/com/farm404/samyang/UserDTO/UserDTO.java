package com.farm404.samyang.UserDTO;

import java.util.Date;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@Data
public class UserDTO {
	
	private String ID;
	private String pwd;
	private String name;
	private String post;
	private String addr;
	private String addr_dtl;
	private Date birth;
	private String phone;
	private String writer;
	private Date writeDate;
	private String modifier;
	private String useYN;
}
