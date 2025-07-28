package com.farm404.samyang.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * 전역 예외 처리기
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * SamyangException 처리
     */
    @ExceptionHandler(SamyangException.class)
    public String handleSamyangException(SamyangException e, Model model, RedirectAttributes redirectAttributes) {
        logger.warn("SamyangException 발생: {} - {}", e.getErrorCode().getCode(), e.getMessage());
        
        // AJAX 요청이 아닌 경우 리다이렉트로 처리
        redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        redirectAttributes.addFlashAttribute("errorCode", e.getErrorCode().getCode());
        
        return "redirect:/error";
    }
    
    /**
     * 일반 런타임 예외 처리
     */
    @ExceptionHandler(RuntimeException.class)
    public String handleRuntimeException(RuntimeException e, Model model, RedirectAttributes redirectAttributes) {
        logger.error("RuntimeException 발생", e);
        
        redirectAttributes.addFlashAttribute("errorMessage", "서버 내부 오류가 발생했습니다.");
        redirectAttributes.addFlashAttribute("errorCode", "G002");
        
        return "redirect:/error";
    }
    
    /**
     * 모든 예외 처리 (최종 후순위)
     */
    @ExceptionHandler(Exception.class)
    public String handleException(Exception e, Model model, RedirectAttributes redirectAttributes) {
        logger.error("예상하지 못한 예외 발생", e);
        
        redirectAttributes.addFlashAttribute("errorMessage", "시스템 오류가 발생했습니다. 관리자에게 문의해주세요.");
        redirectAttributes.addFlashAttribute("errorCode", "G002");
        
        return "redirect:/error";
    }
}