package com.farm404.samyang;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(HelloSpringBoot.class);

	@Autowired
	private UserService uService;
	
	
    private final SamyangApplication samyangApplication;

    HelloSpringBoot(SamyangApplication samyangApplication) {
        this.samyangApplication = samyangApplication;
    }

	@GetMapping("/hello")
	@ResponseBody
	public String HelloMain() {
		
		logger.info("HelloSpringBoot Hello Main 엔드포인트 실행");
		
		//실행방법
		// 1) http://localhost:8080/hello
		// 2) http://127.0.0.1:8080/hello
		
		return "HelloSpringBoot";
		
	}
	
	//requestMapping == get, post 둘 다 사용가능하게 하고 vo 같은 객체로 구분함 
	@RequestMapping("/sample")
	public String samplePage() {
		
		return "user/login";
	}
	
	@RequestMapping("/confirmSample")
	public String confirmSample(@ModelAttribute UserDTO userVo) {
		
		logger.debug("사용자 입력 정보 - 이름: {}, 이메일: {}", userVo.getName(), userVo.getEmail());
		
		try {
			int getLoginCnt = uService.getLogin(userVo.getEmail(), userVo.getPassword());
			logger.info("로그인 시도 결과: {}", getLoginCnt > 0 ? "성공" : "실패");
			return "redirect:/";
		} catch (Exception e) {
			logger.error("로그인 처리 중 오류 발생", e);
			return "error";
		}
	}
	
}
