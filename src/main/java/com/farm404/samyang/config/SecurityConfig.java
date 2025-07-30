package com.farm404.samyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 보안 설정 클래스
 * 비밀번호 암호화 등 보안 관련 빈 설정
 */
@Configuration
public class SecurityConfig {
    
    /**
     * BCrypt 비밀번호 인코더 빈 등록
     * 비밀번호 암호화에 사용
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}