package com.farm404.samyang.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;
import com.farm404.samyang.util.SecurityUtils;

/**
 * XSS 공격 방지를 위한 필터
 * 모든 요청 파라미터를 자동으로 이스케이프 처리
 */
@WebFilter("/*")
public class XssFilter implements Filter {
    
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 필터 초기화
    }
    
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) 
            throws IOException, ServletException {
        chain.doFilter(new XssRequestWrapper((HttpServletRequest) request), response);
    }
    
    @Override
    public void destroy() {
        // 필터 종료
    }
    
    /**
     * XSS 방지를 위한 HttpServletRequest 래퍼 클래스
     */
    private static class XssRequestWrapper extends HttpServletRequestWrapper {
        
        public XssRequestWrapper(HttpServletRequest request) {
            super(request);
        }
        
        @Override
        public String[] getParameterValues(String parameter) {
            String[] values = super.getParameterValues(parameter);
            if (values == null) {
                return null;
            }
            
            String[] encodedValues = new String[values.length];
            for (int i = 0; i < values.length; i++) {
                encodedValues[i] = cleanXss(values[i]);
            }
            return encodedValues;
        }
        
        @Override
        public String getParameter(String parameter) {
            String value = super.getParameter(parameter);
            return cleanXss(value);
        }
        
        @Override
        public String getHeader(String name) {
            String value = super.getHeader(name);
            return cleanXss(value);
        }
        
        private String cleanXss(String value) {
            if (value == null) {
                return null;
            }
            
            // HTML 태그 이스케이프
            value = SecurityUtils.escapeHtml(value);
            
            // 추가적인 XSS 패턴 제거
            value = value.replaceAll("(?i)<script.*?>.*?</script>", "");
            value = value.replaceAll("(?i)<iframe.*?>.*?</iframe>", "");
            value = value.replaceAll("(?i)<object.*?>.*?</object>", "");
            value = value.replaceAll("(?i)<embed.*?>.*?</embed>", "");
            value = value.replaceAll("(?i)javascript:", "");
            value = value.replaceAll("(?i)vbscript:", "");
            value = value.replaceAll("(?i)onload=", "");
            value = value.replaceAll("(?i)onclick=", "");
            value = value.replaceAll("(?i)onerror=", "");
            
            return value;
        }
    }
}