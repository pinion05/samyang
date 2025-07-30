package com.farm404.samyang.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.Arrays;

/**
 * 로깅 AOP
 * 메소드 실행 시간, 파라미터, 결과값 등을 자동으로 로깅
 */
@Aspect
@Component
public class LoggingAspect {
    
    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);
    
    /**
     * 컨트롤러 메소드 로깅
     */
    @Around("@within(org.springframework.stereotype.Controller) || @within(org.springframework.web.bind.annotation.RestController)")
    public Object logController(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        Object[] args = joinPoint.getArgs();
        
        logger.info("==> Controller 호출: {}.{}, 파라미터: {}", 
            className, methodName, Arrays.toString(args));
        
        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();
            
            logger.info("<== Controller 완료: {}.{}, 실행시간: {}ms", 
                className, methodName, stopWatch.getTotalTimeMillis());
            
            return result;
        } catch (Exception e) {
            stopWatch.stop();
            logger.error("<== Controller 에러: {}.{}, 실행시간: {}ms, 에러: {}", 
                className, methodName, stopWatch.getTotalTimeMillis(), e.getMessage());
            throw e;
        }
    }
    
    /**
     * 서비스 메소드 로깅
     */
    @Around("@within(org.springframework.stereotype.Service)")
    public Object logService(ProceedingJoinPoint joinPoint) throws Throwable {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.debug("==> Service 호출: {}.{}", className, methodName);
        
        try {
            Object result = joinPoint.proceed();
            stopWatch.stop();
            
            if (stopWatch.getTotalTimeMillis() > 1000) {
                logger.warn("<== Service 느린 실행: {}.{}, 실행시간: {}ms", 
                    className, methodName, stopWatch.getTotalTimeMillis());
            } else {
                logger.debug("<== Service 완료: {}.{}, 실행시간: {}ms", 
                    className, methodName, stopWatch.getTotalTimeMillis());
            }
            
            return result;
        } catch (Exception e) {
            stopWatch.stop();
            logger.error("<== Service 에러: {}.{}, 실행시간: {}ms, 에러: {}", 
                className, methodName, stopWatch.getTotalTimeMillis(), e.getMessage());
            throw e;
        }
    }
    
    /**
     * 리포지토리(Mapper) 메소드 로깅
     */
    @Around("@within(org.apache.ibatis.annotations.Mapper)")
    public Object logMapper(ProceedingJoinPoint joinPoint) throws Throwable {
        if (logger.isTraceEnabled()) {
            String className = joinPoint.getTarget().getClass().getSimpleName();
            String methodName = joinPoint.getSignature().getName();
            
            logger.trace("==> Mapper 호출: {}.{}", className, methodName);
            
            Object result = joinPoint.proceed();
            
            logger.trace("<== Mapper 완료: {}.{}", className, methodName);
            
            return result;
        } else {
            return joinPoint.proceed();
        }
    }
    
    /**
     * 예외 발생 로깅
     */
    @AfterThrowing(pointcut = "@within(org.springframework.stereotype.Service) || @within(org.springframework.stereotype.Controller)", 
                   throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        String className = joinPoint.getTarget().getClass().getSimpleName();
        String methodName = joinPoint.getSignature().getName();
        
        logger.error("예외 발생: {}.{} - {}: {}", 
            className, methodName, 
            exception.getClass().getSimpleName(), 
            exception.getMessage());
    }
}