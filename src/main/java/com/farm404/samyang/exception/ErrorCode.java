package com.farm404.samyang.exception;

import org.springframework.http.HttpStatus;

/**
 * 에러 코드 정의
 */
public enum ErrorCode {
    
    // 사용자 관련 에러
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "U001", "사용자를 찾을 수 없습니다."),
    USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "U002", "이미 사용 중인 이메일입니다."),
    USER_LOGIN_FAILED(HttpStatus.UNAUTHORIZED, "U003", "이메일 또는 비밀번호가 일치하지 않습니다."),
    USER_EMAIL_NOT_FOUND(HttpStatus.NOT_FOUND, "U004", "존재하지 않는 이메일입니다."),
    USER_PASSWORD_MISMATCH(HttpStatus.UNAUTHORIZED, "U005", "비밀번호가 일치하지 않습니다."),
    
    // 작물 관련 에러
    CROP_NOT_FOUND(HttpStatus.NOT_FOUND, "C001", "작물을 찾을 수 없습니다."),
    CROP_ACCESS_DENIED(HttpStatus.FORBIDDEN, "C002", "다른 사용자의 작물입니다."),
    
    // 농사일지 관련 에러
    FARM_DIARY_NOT_FOUND(HttpStatus.NOT_FOUND, "F001", "농사일지를 찾을 수 없습니다."),
    FARM_DIARY_ACCESS_DENIED(HttpStatus.FORBIDDEN, "F002", "다른 사용자의 농사일지입니다."),
    
    // 일반적인 에러
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "G001", "잘못된 입력값입니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G002", "서버 내부 오류가 발생했습니다."),
    ACCESS_DENIED(HttpStatus.FORBIDDEN, "G003", "접근 권한이 없습니다."),
    DATABASE_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "G004", "데이터베이스 오류가 발생했습니다.");
    
    private final HttpStatus status;
    private final String code;
    private final String message;
    
    ErrorCode(HttpStatus status, String code, String message) {
        this.status = status;
        this.code = code;
        this.message = message;
    }
    
    public HttpStatus getHttpStatus() {
        return status;
    }
    
    public int getStatus() {
        return status.value();
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}