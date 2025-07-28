package com.farm404.samyang;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.service.UserService;



@Controller
public class HelloSpringBoot {

	@Autowired
	private UserService uService;
	
	
    private final SamyangApplication samyangApplication;

    HelloSpringBoot(SamyangApplication samyangApplication) {
        this.samyangApplication = samyangApplication;
    }

	@GetMapping("/hello")
	@ResponseBody
	public String HelloMain() {
		
		System.out.print("HelloSpringBoot의 Hello Main 할때 실행");
		System.out.println("");
		
		//실행방법
		// 1) http://localhost:8080
		// 1) http://127.0.0.1:8080
		
		return "HelloSpringBoot";
		
	}
	
	//requestMapping == get, post 둘 다 사용가능하게 하고 vo 같은 객체로 구분함 
	@RequestMapping("/sample")
	public String samplePage() {
		
		return "user/login";
	}
	
	@RequestMapping("/confirmSample")
	public String confirmSample(@ModelAttribute UserDTO userVo) {
		
		System.out.println("userVo.get이름() : " + userVo.get이름());
		System.out.println("userVo.get비밀번호() : " + userVo.get비밀번호());
		System.out.println("userVo() : " + userVo);
		
		int getLoginCnt = uService.getLogin(userVo.get이메일(), userVo.get비밀번호());
		
		System.out.println("=========================");
		System.out.println("loginCon" + getLoginCnt);
		System.out.println("=========================");	
		return null;
	}
	
}
