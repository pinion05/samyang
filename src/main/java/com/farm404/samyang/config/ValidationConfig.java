package com.farm404.samyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;

/**
 * 검증 설정 클래스
 * 메소드 레벨 검증 활성화
 */
@Configuration
public class ValidationConfig {
    
    /**
     * 메소드 검증 프로세서
     * 컨트롤러 메소드 파라미터에 @Valid 사용 가능
     */
    @Bean
    public MethodValidationPostProcessor methodValidationPostProcessor() {
        return new MethodValidationPostProcessor();
    }
}