package com.farm404.samyang.exception;

/**
 * 에러 코드 정의
 * 애플리케이션에서 발생하는 모든 에러를 분류하고 코드화
 */
public enum ErrorCode {
    
    // 공통 에러 (1000번대)
    INVALID_INPUT("E1001", "잘못된 입력값입니다."),
    RESOURCE_NOT_FOUND("E1002", "요청한 리소스를 찾을 수 없습니다."),
    INTERNAL_ERROR("E1003", "내부 서버 오류가 발생했습니다."),
    ACCESS_DENIED("E1004", "접근 권한이 없습니다."),
    
    // 사용자 관련 에러 (2000번대)
    USER_NOT_FOUND("E2001", "사용자를 찾을 수 없습니다."),
    DUPLICATE_EMAIL("E2002", "이미 사용 중인 이메일입니다."),
    INVALID_PASSWORD("E2003", "비밀번호가 올바르지 않습니다."),
    USER_REGISTRATION_FAILED("E2004", "사용자 등록에 실패했습니다."),
    
    // 인증 관련 에러 (3000번대)
    AUTHENTICATION_FAILED("E3001", "인증에 실패했습니다."),
    SESSION_EXPIRED("E3002", "세션이 만료되었습니다."),
    UNAUTHORIZED("E3003", "인증되지 않은 사용자입니다."),
    
    // 데이터 관련 에러 (4000번대)
    DATA_NOT_FOUND("E4001", "데이터를 찾을 수 없습니다."),
    DATA_SAVE_FAILED("E4002", "데이터 저장에 실패했습니다."),
    DATA_UPDATE_FAILED("E4003", "데이터 수정에 실패했습니다."),
    DATA_DELETE_FAILED("E4004", "데이터 삭제에 실패했습니다."),
    DUPLICATE_DATA("E4005", "중복된 데이터입니다."),
    
    // 파일 관련 에러 (5000번대)
    FILE_UPLOAD_FAILED("E5001", "파일 업로드에 실패했습니다."),
    FILE_NOT_FOUND("E5002", "파일을 찾을 수 없습니다."),
    INVALID_FILE_TYPE("E5003", "허용되지 않은 파일 형식입니다."),
    FILE_SIZE_EXCEEDED("E5004", "파일 크기가 제한을 초과했습니다."),
    
    // 비즈니스 로직 에러 (6000번대)
    INVALID_CROP_STATUS("E6001", "잘못된 작물 상태입니다."),
    INVALID_PAYMENT_METHOD("E6002", "잘못된 결제 수단입니다."),
    REVIEW_ALREADY_EXISTS("E6003", "이미 리뷰를 작성했습니다."),
    INVALID_REPORT_TYPE("E6004", "잘못된 신고 유형입니다.");
    
    private final String code;
    private final String message;
    
    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
    
    public String getCode() {
        return code;
    }
    
    public String getMessage() {
        return message;
    }
}