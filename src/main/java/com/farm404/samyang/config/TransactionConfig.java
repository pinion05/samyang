package com.farm404.samyang.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.support.TransactionTemplate;

/**
 * 트랜잭션 관리 설정
 * 선언적 트랜잭션과 프로그래매틱 트랜잭션 모두 지원
 */
@Configuration
@EnableTransactionManagement
public class TransactionConfig {
    
    /**
     * 프로그래매틱 트랜잭션을 위한 TransactionTemplate 빈
     */
    @Bean
    public TransactionTemplate transactionTemplate(PlatformTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate(transactionManager);
        // 기본 트랜잭션 격리 수준 설정
        template.setIsolationLevel(TransactionTemplate.ISOLATION_READ_COMMITTED);
        // 기본 타임아웃 설정 (30초)
        template.setTimeout(30);
        return template;
    }
}