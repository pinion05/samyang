package com.farm404.samyang.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.NoHandlerFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.validation.FieldError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 전역 예외 처리기
 * 애플리케이션에서 발생하는 모든 예외를 통합 처리
 */
@ControllerAdvice
public class GlobalExceptionHandler {
    
    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);
    
    /**
     * SamyangException 처리
     */
    @ExceptionHandler(SamyangException.class)
    public ModelAndView handleSamyangException(SamyangException e, HttpServletRequest request) {
        String errorId = generateErrorId();
        logError(errorId, e, request);
        
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("errorCode", e.getErrorCode().getCode());
        mav.addObject("errorMessage", e.getErrorCode().getMessage());
        mav.addObject("errorDetail", e.getDetail());
        mav.addObject("errorId", errorId);
        mav.addObject("timestamp", LocalDateTime.now());
        
        return mav;
    }
    
    /**
     * 유효성 검증 실패 예외 처리
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ModelAndView handleValidationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        String errorId = generateErrorId();
        logError(errorId, e, request);
        
        Map<String, String> errors = new HashMap<>();
        for (FieldError error : e.getBindingResult().getFieldErrors()) {
            errors.put(error.getField(), error.getDefaultMessage());
        }
        
        ModelAndView mav = new ModelAndView("error/error");
        mav.addObject("errorCode", ErrorCode.INVALID_INPUT.getCode());
        mav.addObject("errorMessage", "입력값 검증에 실패했습니다.");
        mav.addObject("validationErrors", errors);
        mav.addObject("errorId", errorId);
        mav.addObject("timestamp", LocalDateTime.now());
        
        return mav;
    }
    
    /**
     * 404 Not Found 예외 처리
     */
    @ExceptionHandler(NoHandlerFoundException.class)
    public ModelAndView handleNotFoundException(NoHandlerFoundException e, HttpServletRequest request) {
        String errorId = generateErrorId();
        logger.warn("404 에러 발생 - ErrorID: {}, URL: {}", errorId, request.getRequestURL());
        
        ModelAndView mav = new ModelAndView("error/404");
        mav.addObject("errorId", errorId);
        mav.addObject("requestUrl", request.getRequestURL());
        mav.addObject("timestamp", LocalDateTime.now());
        
        return mav;
    }
    
    /**
     * 일반 예외 처리
     */
    @ExceptionHandler(Exception.class)
    public ModelAndView handleGeneralException(Exception e, HttpServletRequest request) {
        String errorId = generateErrorId();
        logError(errorId, e, request);
        
        ModelAndView mav = new ModelAndView("error/500");
        mav.addObject("errorCode", ErrorCode.INTERNAL_ERROR.getCode());
        mav.addObject("errorMessage", "서버 오류가 발생했습니다. 잠시 후 다시 시도해주세요.");
        mav.addObject("errorId", errorId);
        mav.addObject("timestamp", LocalDateTime.now());
        
        // 개발 환경에서만 상세 에러 정보 표시
        String profile = System.getProperty("spring.profiles.active", "local");
        if ("local".equals(profile) || "dev".equals(profile)) {
            mav.addObject("debugMessage", e.getMessage());
            mav.addObject("stackTrace", getStackTraceAsString(e));
        }
        
        return mav;
    }
    
    /**
     * REST API 예외 처리 (JSON 응답)
     */
    @ExceptionHandler(SamyangException.class)
    @ResponseBody
    public Object handleSamyangExceptionForRest(
            SamyangException e, HttpServletRequest request) {
        
        if (isApiRequest(request)) {
            String errorId = generateErrorId();
            logError(errorId, e, request);
            
            com.farm404.samyang.dto.ApiResponse<?> response = 
                com.farm404.samyang.dto.ApiResponse.error(
                    e.getErrorCode().getCode(), 
                    e.getDetail() != null ? e.getDetail() : e.getErrorCode().getMessage()
                );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
        
        // API 요청이 아닌 경우 웹 똑C로 처리
        return handleSamyangException(e, request);
    }
    
    /**
     * 에러 ID 생성
     */
    private String generateErrorId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
    
    /**
     * 에러 로깅
     */
    private void logError(String errorId, Exception e, HttpServletRequest request) {
        logger.error("에러 발생 - ErrorID: {}, URL: {}, Method: {}, IP: {}, User-Agent: {}", 
            errorId,
            request.getRequestURL(),
            request.getMethod(),
            request.getRemoteAddr(),
            request.getHeader("User-Agent"),
            e
        );
    }
    
    /**
     * API 요청 여부 확인
     */
    private boolean isApiRequest(HttpServletRequest request) {
        String uri = request.getRequestURI();
        String contentType = request.getHeader("Content-Type");
        String accept = request.getHeader("Accept");
        
        return uri.startsWith("/api/") || 
               (contentType != null && contentType.contains("application/json")) ||
               (accept != null && accept.contains("application/json"));
    }
    
    /**
     * 스택 트레이스를 문자열로 변환
     */
    private String getStackTraceAsString(Exception e) {
        StringBuilder sb = new StringBuilder();
        for (StackTraceElement element : e.getStackTrace()) {
            sb.append(element.toString()).append("\n");
        }
        return sb.toString();
    }
}