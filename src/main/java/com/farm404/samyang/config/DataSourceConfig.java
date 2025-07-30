package com.farm404.samyang.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import javax.sql.DataSource;

/**
 * 데이터베이스 연결 풀 설정
 * HikariCP를 사용하여 효율적인 커넥션 관리
 */
@Configuration
public class DataSourceConfig {
    
    @Bean
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }
    
    @Bean
    @Primary
    public DataSource dataSource(HikariConfig hikariConfig) {
        // 프로덕션 환경 최적화 설정
        hikariConfig.setPoolName("Samyang-Pool");
        hikariConfig.setMaximumPoolSize(20);
        hikariConfig.setMinimumIdle(5);
        hikariConfig.setIdleTimeout(300000); // 5분
        hikariConfig.setConnectionTimeout(30000); // 30초
        hikariConfig.setMaxLifetime(1800000); // 30분
        hikariConfig.setLeakDetectionThreshold(60000); // 1분
        
        // 성능 최적화 설정
        hikariConfig.setConnectionTestQuery("SELECT 1");
        hikariConfig.setValidationTimeout(5000);
        
        // 트랜잭션 격리 수준 설정
        hikariConfig.setTransactionIsolation("TRANSACTION_READ_COMMITTED");
        
        return new HikariDataSource(hikariConfig);
    }
}