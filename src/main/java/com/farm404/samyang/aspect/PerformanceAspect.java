package com.farm404.samyang.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 성능 모니터링 AOP
 * 느린 쿼리 및 메소드 실행 감지
 */
@Aspect
@Component
public class PerformanceAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(PerformanceAspect.class);
    
    // 성능 임계값 (밀리초)
    private static final long SLOW_METHOD_THRESHOLD = 1000; // 1초
    private static final long SLOW_QUERY_THRESHOLD = 500;   // 500ms
    
    /**
     * Mapper 메소드 성능 모니터링
     */
    @Around("execution(* com.farm404.samyang.mapper.*.*(..))")
    public Object monitorMapperPerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (executionTime > SLOW_QUERY_THRESHOLD) {
                logger.warn("느린 쿼리 감지: {}.{} - 실행시간: {}ms", 
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    executionTime);
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("쿼리 실행 실패: {}.{} - 실행시간: {}ms", 
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                executionTime, e);
            throw e;
        }
    }
    
    /**
     * Service 메소드 성능 모니터링
     */
    @Around("execution(* com.farm404.samyang.service.*.*(..))")
    public Object monitorServicePerformance(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        
        try {
            Object result = joinPoint.proceed();
            long executionTime = System.currentTimeMillis() - startTime;
            
            if (executionTime > SLOW_METHOD_THRESHOLD) {
                logger.warn("느린 메소드 감지: {}.{} - 실행시간: {}ms", 
                    joinPoint.getTarget().getClass().getSimpleName(),
                    joinPoint.getSignature().getName(),
                    executionTime);
            }
            
            return result;
        } catch (Exception e) {
            long executionTime = System.currentTimeMillis() - startTime;
            logger.error("메소드 실행 실패: {}.{} - 실행시간: {}ms", 
                joinPoint.getTarget().getClass().getSimpleName(),
                joinPoint.getSignature().getName(),
                executionTime, e);
            throw e;
        }
    }
}