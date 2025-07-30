package com.farm404.samyang.config;

import com.farm404.samyang.interceptor.AuthInterceptor;
import com.farm404.samyang.interceptor.SecurityInterceptor;
import com.farm404.samyang.interceptor.ApiLoggingInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.beans.factory.annotation.Autowired;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    @Autowired
    private SecurityInterceptor securityInterceptor;
    
    @Autowired
    private ApiLoggingInterceptor apiLoggingInterceptor;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // TODO: [최소수정] webapp 디렉토리의 CSS/JS 파일에 접근하려면 추가 설정 필요
        // 현재는 classpath:/static/만 참조하므로 src/main/webapp/css, js에 접근 불가
        // 이상적 해결: 파일을 src/main/resources/static으로 이동
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/css/**")
                .addResourceLocations("classpath:/static/css/", "/css/");  // webapp/css 추가
        registry.addResourceHandler("/js/**")
                .addResourceLocations("classpath:/static/js/", "/js/");    // webapp/js 추가
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/");
        registry.addResourceHandler("/upload/**")
                .addResourceLocations("file:///C:/dev/samyang/upload/");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("index");
    }
    
    @Bean
    public AuthInterceptor authInterceptor() {
        return new AuthInterceptor();
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 보안 인터셉터 (모든 요청에 적용)
        registry.addInterceptor(securityInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/css/**", "/js/**", "/images/**", "/fonts/**", "/static/**");
        
        // 인증 인터셉터
        registry.addInterceptor(authInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/", "/user/login", "/user/register", "/test/**", 
                                   "/static/**", "/css/**", "/js/**", "/images/**", "/upload/**",
                                   "/api/**"); // API는 별도 인증 처리
        
        // API 로깅 인터셉터
        registry.addInterceptor(apiLoggingInterceptor)
                .addPathPatterns("/api/**");
    }
    
    @Bean
    public MultipartResolver multipartResolver() {
        return new StandardServletMultipartResolver();
    }
}