package com.farm404.samyang.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.UUID;

/**
 * API 요청/응답 로깅 인터셉터
 * 모든 API 요청에 대한 로깅 처리
 */
@Component
public class ApiLoggingInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(ApiLoggingInterceptor.class);
    private static final String REQUEST_ID_HEADER = "X-Request-ID";
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        // Request ID 생성 및 설정
        String requestId = request.getHeader(REQUEST_ID_HEADER);
        if (requestId == null) {
            requestId = UUID.randomUUID().toString();
        }
        request.setAttribute("requestId", requestId);
        response.setHeader(REQUEST_ID_HEADER, requestId);
        
        // API 요청 로깅
        logger.info("API 요청 시작 - RequestID: {}, Method: {}, URI: {}, IP: {}, User-Agent: {}", 
            requestId,
            request.getMethod(),
            request.getRequestURI(),
            request.getRemoteAddr(),
            request.getHeader("User-Agent")
        );
        
        // 요청 시작 시간 저장
        request.setAttribute("startTime", System.currentTimeMillis());
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // 뷰 처리 후 로직 (API는 주로 사용하지 않음)
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        
        // 요청 처리 시간 계산
        long startTime = (Long) request.getAttribute("startTime");
        long duration = System.currentTimeMillis() - startTime;
        String requestId = (String) request.getAttribute("requestId");
        
        // API 응답 로깅
        if (ex != null) {
            logger.error("API 요청 실패 - RequestID: {}, Duration: {}ms, Status: {}, Error: {}", 
                requestId,
                duration,
                response.getStatus(),
                ex.getMessage()
            );
        } else {
            logger.info("API 요청 완료 - RequestID: {}, Duration: {}ms, Status: {}", 
                requestId,
                duration,
                response.getStatus()
            );
        }
    }
}