package com.farm404.samyang.exception;

/**
 * 삼양 애플리케이션 전용 예외 클래스
 * 비즈니스 로직에서 발생하는 예외를 통합 관리
 */
public class SamyangException extends RuntimeException {
    
    private final ErrorCode errorCode;
    private final String detail;
    
    public SamyangException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
        this.detail = null;
    }
    
    public SamyangException(ErrorCode errorCode, String detail) {
        super(errorCode.getMessage() + " - " + detail);
        this.errorCode = errorCode;
        this.detail = detail;
    }
    
    public SamyangException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
        this.detail = null;
    }
    
    public SamyangException(ErrorCode errorCode, String detail, Throwable cause) {
        super(errorCode.getMessage() + " - " + detail, cause);
        this.errorCode = errorCode;
        this.detail = detail;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    
    public String getDetail() {
        return detail;
    }
}