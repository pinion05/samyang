package com.farm404.samyang.controller.api;

import com.farm404.samyang.dto.ApiResponse;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.PageRequest;
import com.farm404.samyang.dto.PageResponse;
import com.farm404.samyang.service.UserService;
import com.farm404.samyang.validator.UserValidator;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

/**
 * 사용자 관리 REST API 컨트롤러
 * 표준화된 API 응답 형식 제공
 */
@RestController
@RequestMapping("/api/users")
public class UserApiController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private UserValidator userValidator;
    
    /**
     * 사용자 목록 조회 (페이징)
     */
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserDTO>>> getUsers(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "DESC") String sortDirection) {
        
        PageRequest pageRequest = new PageRequest(page, size);
        pageRequest.setSortBy(sortBy);
        pageRequest.setSortDirection(sortDirection);
        
        // TODO: UserService에 페이징 메소드 추가 필요
        List<UserDTO> users = userService.getAllUsers();
        PageResponse<UserDTO> pageResponse = new PageResponse<>(
            users, page, size, users.size()
        );
        
        return ResponseEntity.ok(ApiResponse.success("사용자 목록 조회 성공", pageResponse));
    }
    
    /**
     * 사용자 상세 조회
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> getUser(@PathVariable String id) {
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        return ResponseEntity.ok(ApiResponse.success("사용자 조회 성공", user));
    }
    
    /**
     * 사용자 등록
     */
    @PostMapping
    public ResponseEntity<ApiResponse<UserDTO>> registerUser(@RequestBody UserDTO user) {
        // 입력값 검증
        userValidator.validateForRegistration(user);
        
        // 이메일 중복 체크
        if (userService.isEmailExists(user.getEmail())) {
            throw new SamyangException(ErrorCode.DUPLICATE_EMAIL);
        }
        
        // 사용자 등록
        boolean success = userService.registerUser(user);
        if (!success) {
            throw new SamyangException(ErrorCode.USER_REGISTRATION_FAILED);
        }
        
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(ApiResponse.success("사용자 등록 성공", user));
    }
    
    /**
     * 사용자 정보 수정
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<UserDTO>> updateUser(
            @PathVariable String id, 
            @RequestBody UserDTO user) {
        
        // ID 설정
        user.setId(id);
        
        // 입력값 검증
        userValidator.validateForUpdate(user);
        
        // 사용자 존재 확인
        UserDTO existingUser = userService.getUserById(id);
        if (existingUser == null) {
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        // 사용자 수정
        boolean success = userService.updateUser(user);
        if (!success) {
            throw new SamyangException(ErrorCode.DATA_UPDATE_FAILED);
        }
        
        return ResponseEntity.ok(ApiResponse.success("사용자 정보 수정 성공", user));
    }
    
    /**
     * 사용자 삭제
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteUser(@PathVariable String id) {
        // 사용자 존재 확인
        UserDTO user = userService.getUserById(id);
        if (user == null) {
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        // 사용자 삭제
        boolean success = userService.deleteUser(id);
        if (!success) {
            throw new SamyangException(ErrorCode.DATA_DELETE_FAILED);
        }
        
        return ResponseEntity.ok(ApiResponse.success("사용자 삭제 성공", null));
    }
    
    /**
     * 로그인
     */
    @PostMapping("/login")
    public ResponseEntity<ApiResponse<UserDTO>> login(@RequestBody UserDTO loginRequest) {
        // 입력값 검증
        userValidator.validateForLogin(loginRequest.getEmail(), loginRequest.getPassword());
        
        // 로그인 처리
        UserDTO user = userService.login(loginRequest.getEmail(), loginRequest.getPassword());
        if (user == null) {
            throw new SamyangException(ErrorCode.AUTHENTICATION_FAILED, "이메일 또는 비밀번호가 올바르지 않습니다.");
        }
        
        // 비밀번호 제거
        user.setPassword(null);
        
        return ResponseEntity.ok(ApiResponse.success("로그인 성공", user));
    }
}