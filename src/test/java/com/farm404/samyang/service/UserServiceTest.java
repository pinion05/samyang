package com.farm404.samyang.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.UserMapper;

/**
 * UserService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private UserService userService;
    
    private UserDTO testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new UserDTO();
        testUser.set사용자ID(1);
        testUser.set이름("테스트사용자");
        testUser.set이메일("test@example.com");
        testUser.set비밀번호("password123");
        testUser.set전화번호("010-1234-5678");
        testUser.set주소("서울시 강남구");
        testUser.set관리자여부(false);
    }
    
    @Test
    @DisplayName("사용자 등록 성공")
    void registerUser_Success() {
        // Given
        when(userMapper.checkEmailExists(anyString())).thenReturn(0);
        when(userMapper.insertUser(any(UserDTO.class))).thenReturn(1);
        
        // When
        int result = userService.registerUser(testUser);
        
        // Then
        assertEquals(1, result);
        verify(userMapper).checkEmailExists(testUser.get이메일());
        verify(userMapper).insertUser(testUser);
    }
    
    @Test
    @DisplayName("사용자 등록 실패 - 이메일 중복")
    void registerUser_EmailDuplicated() {
        // Given
        when(userMapper.checkEmailExists(anyString())).thenReturn(1);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.registerUser(testUser));
        
        assertEquals(ErrorCode.USER_EMAIL_DUPLICATED, exception.getErrorCode());
        verify(userMapper).checkEmailExists(testUser.get이메일());
        verify(userMapper, never()).insertUser(any(UserDTO.class));
    }
    
    @Test
    @DisplayName("사용자 조회 성공")
    void getUserById_Success() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        
        // When
        UserDTO result = userService.getUserById(1);
        
        // Then
        assertNotNull(result);
        assertEquals(testUser.get사용자ID(), result.get사용자ID());
        assertEquals(testUser.get이메일(), result.get이메일());
        verify(userMapper).selectUserById(1);
    }
    
    @Test
    @DisplayName("사용자 조회 실패 - 사용자 없음")
    void getUserById_UserNotFound() {
        // Given
        when(userMapper.selectUserById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.getUserById(999));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(999);
    }
    
    @Test
    @DisplayName("로그인 성공")
    void login_Success() {
        // Given
        when(userMapper.selectUserByEmail("test@example.com")).thenReturn(testUser);
        
        // When
        UserDTO result = userService.login("test@example.com", "password123");
        
        // Then
        assertNotNull(result);
        assertEquals(testUser.get이메일(), result.get이메일());
        assertNull(result.get비밀번호()); // 비밀번호는 null로 설정되어야 함
        verify(userMapper).selectUserByEmail("test@example.com");
    }
    
    @Test
    @DisplayName("로그인 실패 - 존재하지 않는 이메일")
    void login_EmailNotFound() {
        // Given
        when(userMapper.selectUserByEmail("nonexistent@example.com")).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.login("nonexistent@example.com", "password123"));
        
        assertEquals(ErrorCode.USER_EMAIL_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserByEmail("nonexistent@example.com");
    }
    
    @Test
    @DisplayName("로그인 실패 - 비밀번호 불일치")
    void login_PasswordMismatch() {
        // Given
        when(userMapper.selectUserByEmail("test@example.com")).thenReturn(testUser);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.login("test@example.com", "wrongpassword"));
        
        assertEquals(ErrorCode.USER_PASSWORD_MISMATCH, exception.getErrorCode());
        verify(userMapper).selectUserByEmail("test@example.com");
    }
    
    @Test
    @DisplayName("이메일 중복 확인")
    void checkEmailExists() {
        // Given
        when(userMapper.checkEmailExists("existing@example.com")).thenReturn(1);
        when(userMapper.checkEmailExists("new@example.com")).thenReturn(0);
        
        // When & Then
        assertTrue(userService.checkEmailExists("existing@example.com"));
        assertFalse(userService.checkEmailExists("new@example.com"));
        
        verify(userMapper).checkEmailExists("existing@example.com");
        verify(userMapper).checkEmailExists("new@example.com");
    }
    
    @Test
    @DisplayName("사용자 정보 수정 성공")
    void updateUser_Success() {
        // Given
        UserDTO existingUser = new UserDTO();
        existingUser.set사용자ID(1);
        existingUser.set이메일("old@example.com");
        
        UserDTO updateUser = new UserDTO();
        updateUser.set사용자ID(1);
        updateUser.set이름("수정된사용자");
        updateUser.set이메일("new@example.com");
        
        when(userMapper.selectUserById(1)).thenReturn(existingUser);
        when(userMapper.checkEmailExists("new@example.com")).thenReturn(0);
        when(userMapper.updateUser(updateUser)).thenReturn(1);
        
        // When
        int result = userService.updateUser(updateUser);
        
        // Then
        assertEquals(1, result);
        verify(userMapper).selectUserById(1);
        verify(userMapper).checkEmailExists("new@example.com");
        verify(userMapper).updateUser(updateUser);
    }
    
    @Test
    @DisplayName("사용자 정보 수정 실패 - 사용자 없음")
    void updateUser_UserNotFound() {
        // Given
        UserDTO updateUser = new UserDTO();
        updateUser.set사용자ID(999);
        
        when(userMapper.selectUserById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.updateUser(updateUser));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(999);
        verify(userMapper, never()).updateUser(any(UserDTO.class));
    }
    
    @Test
    @DisplayName("사용자 정보 수정 실패 - 이메일 중복")
    void updateUser_EmailDuplicated() {
        // Given
        UserDTO existingUser = new UserDTO();
        existingUser.set사용자ID(1);
        existingUser.set이메일("old@example.com");
        
        UserDTO updateUser = new UserDTO();
        updateUser.set사용자ID(1);
        updateUser.set이메일("duplicate@example.com");
        
        when(userMapper.selectUserById(1)).thenReturn(existingUser);
        when(userMapper.checkEmailExists("duplicate@example.com")).thenReturn(1);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.updateUser(updateUser));
        
        assertEquals(ErrorCode.USER_EMAIL_DUPLICATED, exception.getErrorCode());
        verify(userMapper).selectUserById(1);
        verify(userMapper).checkEmailExists("duplicate@example.com");
        verify(userMapper, never()).updateUser(any(UserDTO.class));
    }
    
    @Test
    @DisplayName("사용자 삭제 성공")
    void deleteUser_Success() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(userMapper.deleteUser(1)).thenReturn(1);
        
        // When
        int result = userService.deleteUser(1);
        
        // Then
        assertEquals(1, result);
        verify(userMapper).selectUserById(1);
        verify(userMapper).deleteUser(1);
    }
    
    @Test
    @DisplayName("사용자 삭제 실패 - 사용자 없음")
    void deleteUser_UserNotFound() {
        // Given
        when(userMapper.selectUserById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> userService.deleteUser(999));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(999);
        verify(userMapper, never()).deleteUser(anyInt());
    }
}