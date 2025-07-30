package com.farm404.samyang.validator;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

/**
 * 사용자 데이터 검증 클래스
 * 사용자 관련 입력값 검증 로직 제공
 */
@Component
public class UserValidator {
    
    // 이메일 정규식 패턴
    private static final Pattern EMAIL_PATTERN = Pattern.compile(
        "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$"
    );
    
    // 비밀번호 정규식 패턴 (최소 8자, 영문, 숫자, 특수문자 포함)
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(
        "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"
    );
    
    // 전화번호 정규식 패턴
    private static final Pattern PHONE_PATTERN = Pattern.compile(
        "^(\\+82|0)[1-9]\\d{1,2}-\\d{3,4}-\\d{4}$"
    );
    
    /**
     * 사용자 등록 데이터 검증
     */
    public void validateForRegistration(UserDTO user) {
        // 필수 필드 검증
        if (user.getName() == null || user.getName().trim().isEmpty()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "이름은 필수 입력값입니다.");
        }
        
        if (user.getEmail() == null || user.getEmail().trim().isEmpty()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "이메일은 필수 입력값입니다.");
        }
        
        if (user.getPassword() == null || user.getPassword().trim().isEmpty()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "비밀번호는 필수 입력값입니다.");
        }
        
        // 이메일 형식 검증
        if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "올바른 이메일 형식이 아닙니다.");
        }
        
        // 비밀번호 형식 검증
        if (!PASSWORD_PATTERN.matcher(user.getPassword()).matches()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, 
                "비밀번호는 최소 8자 이상이며, 영문, 숫자, 특수문자를 포함해야 합니다.");
        }
        
        // 비밀번호 확인 검증
        if (user.getPasswordConfirm() != null && 
            !user.getPassword().equals(user.getPasswordConfirm())) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "비밀번호가 일치하지 않습니다.");
        }
        
        // 전화번호 형식 검증 (선택사항)
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
                throw new SamyangException(ErrorCode.INVALID_INPUT, 
                    "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)");
            }
        }
        
        // 이름 길이 검증
        if (user.getName().length() < 2 || user.getName().length() > 50) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, 
                "이름은 2자 이상 50자 이하로 입력해주세요.");
        }
    }
    
    /**
     * 사용자 수정 데이터 검증
     */
    public void validateForUpdate(UserDTO user) {
        // userId 필수
        if (user.getUserId() == null) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "사용자 ID는 필수입니다.");
        }
        
        // 이메일 형식 검증 (변경 시)
        if (user.getEmail() != null && !user.getEmail().trim().isEmpty()) {
            if (!EMAIL_PATTERN.matcher(user.getEmail()).matches()) {
                throw new SamyangException(ErrorCode.INVALID_INPUT, "올바른 이메일 형식이 아닙니다.");
            }
        }
        
        // 전화번호 형식 검증 (변경 시)
        if (user.getPhoneNumber() != null && !user.getPhoneNumber().trim().isEmpty()) {
            if (!PHONE_PATTERN.matcher(user.getPhoneNumber()).matches()) {
                throw new SamyangException(ErrorCode.INVALID_INPUT, 
                    "올바른 전화번호 형식이 아닙니다. (예: 010-1234-5678)");
            }
        }
        
        // 이름 길이 검증 (변경 시)
        if (user.getName() != null && !user.getName().trim().isEmpty()) {
            if (user.getName().length() < 2 || user.getName().length() > 50) {
                throw new SamyangException(ErrorCode.INVALID_INPUT, 
                    "이름은 2자 이상 50자 이하로 입력해주세요.");
            }
        }
    }
    
    /**
     * 로그인 데이터 검증
     */
    public void validateForLogin(String email, String password) {
        if (email == null || email.trim().isEmpty()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "이메일을 입력해주세요.");
        }
        
        if (password == null || password.trim().isEmpty()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "비밀번호를 입력해주세요.");
        }
        
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new SamyangException(ErrorCode.INVALID_INPUT, "올바른 이메일 형식이 아닙니다.");
        }
    }
}