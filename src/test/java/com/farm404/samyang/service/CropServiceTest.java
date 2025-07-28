package com.farm404.samyang.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.CropMapper;
import com.farm404.samyang.mapper.UserMapper;

/**
 * CropService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class CropServiceTest {
    
    @Mock
    private CropMapper cropMapper;
    
    @Mock
    private UserMapper userMapper;
    
    @InjectMocks
    private CropService cropService;
    
    private CropDTO testCrop;
    private UserDTO testUser;
    
    @BeforeEach
    void setUp() {
        testUser = new UserDTO();
        testUser.set사용자ID(1);
        testUser.set이름("테스트사용자");
        testUser.set이메일("test@example.com");
        
        testCrop = new CropDTO();
        testCrop.set작물ID(1);
        testCrop.set작물명("토마토");
        testCrop.set사용자ID(1);
        testCrop.set상태("파종");
        testCrop.set심은날짜(LocalDate.of(2024, 1, 1));
        testCrop.set예상수확일(LocalDate.of(2024, 4, 1));
    }
    
    @Test
    @DisplayName("작물 등록 성공")
    void registerCrop_Success() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(cropMapper.insertCrop(any(CropDTO.class))).thenReturn(1);
        
        // When
        int result = cropService.registerCrop(testCrop);
        
        // Then
        assertEquals(1, result);
        verify(userMapper).selectUserById(1);
        verify(cropMapper).insertCrop(testCrop);
    }
    
    @Test
    @DisplayName("작물 등록 실패 - 사용자 없음")
    void registerCrop_UserNotFound() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.registerCrop(testCrop));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(1);
        verify(cropMapper, never()).insertCrop(any(CropDTO.class));
    }
    
    @Test
    @DisplayName("작물 조회 성공")
    void getCropById_Success() {
        // Given
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        
        // When
        CropDTO result = cropService.getCropById(1);
        
        // Then
        assertNotNull(result);
        assertEquals(testCrop.get작물ID(), result.get작물ID());
        assertEquals(testCrop.get작물명(), result.get작물명());
        verify(cropMapper).selectCropById(1);
    }
    
    @Test
    @DisplayName("작물 조회 실패 - 작물 없음")
    void getCropById_CropNotFound() {
        // Given
        when(cropMapper.selectCropById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.getCropById(999));
        
        assertEquals(ErrorCode.CROP_NOT_FOUND, exception.getErrorCode());
        verify(cropMapper).selectCropById(999);
    }
    
    @Test
    @DisplayName("사용자별 작물 목록 조회 성공")
    void getCropListByUserId_Success() {
        // Given
        List<CropDTO> cropList = Arrays.asList(testCrop);
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(cropMapper.selectCropListByUserId(1)).thenReturn(cropList);
        
        // When
        List<CropDTO> result = cropService.getCropListByUserId(1);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testCrop.get작물명(), result.get(0).get작물명());
        verify(userMapper).selectUserById(1);
        verify(cropMapper).selectCropListByUserId(1);
    }
    
    @Test
    @DisplayName("사용자별 작물 목록 조회 실패 - 사용자 없음")
    void getCropListByUserId_UserNotFound() {
        // Given
        when(userMapper.selectUserById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.getCropListByUserId(999));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(999);
        verify(cropMapper, never()).selectCropListByUserId(anyInt());
    }
    
    @Test
    @DisplayName("작물 정보 수정 성공")
    void updateCrop_Success() {
        // Given
        CropDTO updateCrop = new CropDTO();
        updateCrop.set작물ID(1);
        updateCrop.set작물명("수정된토마토");
        updateCrop.set사용자ID(1);
        updateCrop.set상태("성장");
        
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(cropMapper.updateCrop(updateCrop)).thenReturn(1);
        
        // When
        int result = cropService.updateCrop(updateCrop);
        
        // Then
        assertEquals(1, result);
        verify(cropMapper).selectCropById(1);
        verify(cropMapper).updateCrop(updateCrop);
    }
    
    @Test
    @DisplayName("작물 정보 수정 실패 - 작물 없음")
    void updateCrop_CropNotFound() {
        // Given
        CropDTO updateCrop = new CropDTO();
        updateCrop.set작물ID(999);
        
        when(cropMapper.selectCropById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.updateCrop(updateCrop));
        
        assertEquals(ErrorCode.CROP_NOT_FOUND, exception.getErrorCode());
        verify(cropMapper).selectCropById(999);
        verify(cropMapper, never()).updateCrop(any(CropDTO.class));
    }
    
    @Test
    @DisplayName("작물 정보 수정 실패 - 사용자 변경 시 사용자 없음")
    void updateCrop_UserNotFoundWhenChangingOwner() {
        // Given
        CropDTO updateCrop = new CropDTO();
        updateCrop.set작물ID(1);
        updateCrop.set사용자ID(999); // 다른 사용자로 변경
        
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(userMapper.selectUserById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.updateCrop(updateCrop));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(cropMapper).selectCropById(1);
        verify(userMapper).selectUserById(999);
        verify(cropMapper, never()).updateCrop(any(CropDTO.class));
    }
    
    @Test
    @DisplayName("작물 삭제 성공")
    void deleteCrop_Success() {
        // Given
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(cropMapper.deleteCrop(1)).thenReturn(1);
        
        // When
        int result = cropService.deleteCrop(1);
        
        // Then
        assertEquals(1, result);
        verify(cropMapper).selectCropById(1);
        verify(cropMapper).deleteCrop(1);
    }
    
    @Test
    @DisplayName("작물 삭제 실패 - 작물 없음")
    void deleteCrop_CropNotFound() {
        // Given
        when(cropMapper.selectCropById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> cropService.deleteCrop(999));
        
        assertEquals(ErrorCode.CROP_NOT_FOUND, exception.getErrorCode());
        verify(cropMapper).selectCropById(999);
        verify(cropMapper, never()).deleteCrop(anyInt());
    }
    
    @Test
    @DisplayName("작물 상태 업데이트 성공")
    void updateCropStatus_Success() {
        // Given
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(cropMapper.updateCrop(any(CropDTO.class))).thenReturn(1);
        
        // When
        int result = cropService.updateCropStatus(1, "수확완료");
        
        // Then
        assertEquals(1, result);
        verify(cropMapper).selectCropById(1);
        verify(cropMapper).updateCrop(any(CropDTO.class));
    }
}