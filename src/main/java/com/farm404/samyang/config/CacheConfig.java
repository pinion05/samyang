package com.farm404.samyang.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.cache.interceptor.KeyGenerator;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * 캐싱 설정 클래스
 * Spring Cache를 사용하여 메모리 캐싱 구현
 */
@Configuration
@EnableCaching
public class CacheConfig {
    
    /**
     * 캐시 매니저 빈 설정
     * 메모리 기반 캐시 사용
     */
    @Bean
    public CacheManager cacheManager() {
        ConcurrentMapCacheManager cacheManager = new ConcurrentMapCacheManager();
        // 캐시 이름 정의
        cacheManager.setCacheNames(Arrays.asList(
            "users",           // 사용자 정보 캐시
            "userList",        // 사용자 목록 캐시
            "crops",           // 작물 정보 캐시
            "cropList",        // 작물 목록 캐시
            "diaries",         // 농사일지 캐시
            "diaryList",       // 농사일지 목록 캐시
            "reviews",         // 리뷰 캐시
            "comments",        // 댓글 캐시
            "paymentMethods"   // 결제수단 캐시
        ));
        return cacheManager;
    }
    
    /**
     * 커스텀 키 생성기
     * 클래스명, 메소드명, 파라미터를 조합하여 캐시 키 생성
     */
    @Bean("customKeyGenerator")
    public KeyGenerator keyGenerator() {
        return new KeyGenerator() {
            @Override
            public Object generate(Object target, Method method, Object... params) {
                StringBuilder sb = new StringBuilder();
                sb.append(target.getClass().getSimpleName()).append(".");
                sb.append(method.getName()).append(":");
                for (Object param : params) {
                    sb.append(param != null ? param.toString() : "null").append(",");
                }
                return sb.toString();
            }
        };
    }
}