package com.farm404.samyang.interceptor;

import com.farm404.samyang.dto.UserDTO;
import org.springframework.web.servlet.HandlerInterceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        UserDTO user = (UserDTO) session.getAttribute("user");
        
        String requestURI = request.getRequestURI();
        
        // 로그인이 필요없는 경로
        if (isPublicPath(requestURI)) {
            return true;
        }
        
        // 로그인 체크
        if (user == null) {
            response.sendRedirect("/user/login");
            return false;
        }
        
        // 관리자 권한 체크
        if (isAdminPath(requestURI) && !"ADMIN".equals(user.getRole())) {
            response.sendRedirect("/");
            return false;
        }
        
        return true;
    }
    
    private boolean isPublicPath(String uri) {
        return uri.equals("/") || 
               uri.startsWith("/user/login") || 
               uri.startsWith("/user/register") ||
               uri.startsWith("/test/") ||
               uri.startsWith("/static/") ||
               uri.startsWith("/css/") ||
               uri.startsWith("/js/") ||
               uri.startsWith("/images/");
    }
    
    private boolean isAdminPath(String uri) {
        return uri.startsWith("/admin/") || 
               uri.equals("/user/users");
    }
}