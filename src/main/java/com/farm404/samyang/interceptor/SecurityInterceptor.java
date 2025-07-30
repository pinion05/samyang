package com.farm404.samyang.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.farm404.samyang.util.SecurityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 보안 검증을 위한 인터셉터
 * SQL Injection, XSS 등의 공격 시도를 차단
 */
@Component
public class SecurityInterceptor implements HandlerInterceptor {
    
    private static final Logger logger = LoggerFactory.getLogger(SecurityInterceptor.class);
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) 
            throws Exception {
        
        // 모든 파라미터 검사
        for (String paramName : request.getParameterMap().keySet()) {
            String[] paramValues = request.getParameterValues(paramName);
            for (String value : paramValues) {
                // SQL Injection 검사
                if (SecurityUtils.hasSqlInjectionRisk(value)) {
                    logger.warn("SQL Injection 시도 감지 - IP: {}, Parameter: {}, Value: {}", 
                        request.getRemoteAddr(), paramName, value);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "잘못된 요청입니다.");
                    return false;
                }
                
                // 위험한 HTML 태그 검사
                if (SecurityUtils.hasDangerousHtml(value)) {
                    logger.warn("위험한 HTML 태그 감지 - IP: {}, Parameter: {}, Value: {}", 
                        request.getRemoteAddr(), paramName, value);
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST, "허용되지 않은 문자가 포함되어 있습니다.");
                    return false;
                }
            }
        }
        
        // 보안 헤더 설정
        response.setHeader("X-Content-Type-Options", "nosniff");
        response.setHeader("X-Frame-Options", "DENY");
        response.setHeader("X-XSS-Protection", "1; mode=block");
        response.setHeader("Content-Security-Policy", "default-src 'self'; script-src 'self' 'unsafe-inline' 'unsafe-eval' https://cdn.jsdelivr.net; style-src 'self' 'unsafe-inline' https://cdn.jsdelivr.net");
        
        return true;
    }
    
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
            ModelAndView modelAndView) throws Exception {
        // 응답 후 처리
    }
    
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
            Exception ex) throws Exception {
        // 요청 완료 후 처리
    }
}