package com.farm404.samyang.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class CustomErrorController implements ErrorController {
    
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request, Model model) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        Object exception = request.getAttribute(RequestDispatcher.ERROR_EXCEPTION);
        Object message = request.getAttribute(RequestDispatcher.ERROR_MESSAGE);
        Object requestUri = request.getAttribute(RequestDispatcher.ERROR_REQUEST_URI);
        
        if (status != null) {
            Integer statusCode = Integer.valueOf(status.toString());
            
            // 에러 정보를 모델에 추가
            model.addAttribute("errorCode", statusCode);
            model.addAttribute("exception", exception);
            model.addAttribute("message", message);
            model.addAttribute("requestURI", requestUri);
            
            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return "error/404";
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return "error/500";
            } else if (statusCode == HttpStatus.FORBIDDEN.value()) {
                model.addAttribute("errorTitle", "접근 권한이 없습니다");
                model.addAttribute("errorMessage", "이 페이지에 접근할 권한이 없습니다.");
                return "error/403";
            } else if (statusCode == HttpStatus.BAD_REQUEST.value()) {
                model.addAttribute("errorTitle", "잘못된 요청입니다");
                model.addAttribute("errorMessage", "요청이 올바르지 않습니다. 다시 확인해 주세요.");
                return "error/400";
            }
        }
        
        // 기본적으로 500 에러 페이지 반환
        return "error/500";
    }
    
    public String getErrorPath() {
        return "/error";
    }
}