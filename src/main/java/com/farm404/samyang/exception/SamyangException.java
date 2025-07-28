package com.farm404.samyang.exception;

/**
 * 삼양 농업 플랫폼 전용 예외 클래스
 */
public class SamyangException extends RuntimeException {
    
    private final ErrorCode errorCode;
    
    public SamyangException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
    
    public SamyangException(ErrorCode errorCode, String customMessage) {
        super(customMessage);
        this.errorCode = errorCode;
    }
    
    public SamyangException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.errorCode = errorCode;
    }
    
    public ErrorCode getErrorCode() {
        return errorCode;
    }
    
    public int getStatus() {
        return errorCode.getStatus();
    }
}