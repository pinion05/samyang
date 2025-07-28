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
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.CropMapper;
import com.farm404.samyang.mapper.FarmDiaryMapper;
import com.farm404.samyang.mapper.UserMapper;

/**
 * FarmDiaryService 단위 테스트
 */
@ExtendWith(MockitoExtension.class)
class FarmDiaryServiceTest {
    
    @Mock
    private FarmDiaryMapper farmDiaryMapper;
    
    @Mock
    private UserMapper userMapper;
    
    @Mock
    private CropMapper cropMapper;
    
    @InjectMocks
    private FarmDiaryService farmDiaryService;
    
    private FarmDiaryDTO testFarmDiary;
    private UserDTO testUser;
    private CropDTO testCrop;
    
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
        
        testFarmDiary = new FarmDiaryDTO();
        testFarmDiary.set농사일지ID(1);
        testFarmDiary.set사용자ID(1);
        testFarmDiary.set작물ID(1);
        testFarmDiary.set활동_유형("물주기");
        testFarmDiary.set내용("테스트 내용");
        testFarmDiary.set날짜(LocalDate.of(2024, 1, 1));
    }
    
    @Test
    @DisplayName("농사일지 등록 성공")
    void registerFarmDiary_Success() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(farmDiaryMapper.insertFarmDiary(any(FarmDiaryDTO.class))).thenReturn(1);
        
        // When
        int result = farmDiaryService.registerFarmDiary(testFarmDiary);
        
        // Then
        assertEquals(1, result);
        verify(userMapper).selectUserById(1);
        verify(cropMapper).selectCropById(1);
        verify(farmDiaryMapper).insertFarmDiary(testFarmDiary);
    }
    
    @Test
    @DisplayName("농사일지 등록 실패 - 사용자 없음")
    void registerFarmDiary_UserNotFound() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.registerFarmDiary(testFarmDiary));
        
        assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(1);
        verify(farmDiaryMapper, never()).insertFarmDiary(any(FarmDiaryDTO.class));
    }
    
    @Test
    @DisplayName("농사일지 등록 실패 - 작물 없음")
    void registerFarmDiary_CropNotFound() {
        // Given
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(cropMapper.selectCropById(1)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.registerFarmDiary(testFarmDiary));
        
        assertEquals(ErrorCode.CROP_NOT_FOUND, exception.getErrorCode());
        verify(userMapper).selectUserById(1);
        verify(cropMapper).selectCropById(1);
        verify(farmDiaryMapper, never()).insertFarmDiary(any(FarmDiaryDTO.class));
    }
    
    @Test
    @DisplayName("농사일지 등록 실패 - 다른 사용자의 작물")
    void registerFarmDiary_CropAccessDenied() {
        // Given
        CropDTO otherUserCrop = new CropDTO();
        otherUserCrop.set작물ID(1);
        otherUserCrop.set사용자ID(999); // 다른 사용자
        
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(cropMapper.selectCropById(1)).thenReturn(otherUserCrop);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.registerFarmDiary(testFarmDiary));
        
        assertEquals(ErrorCode.CROP_ACCESS_DENIED, exception.getErrorCode());
        verify(userMapper).selectUserById(1);
        verify(cropMapper).selectCropById(1);
        verify(farmDiaryMapper, never()).insertFarmDiary(any(FarmDiaryDTO.class));
    }
    
    @Test
    @DisplayName("농사일지 조회 성공")
    void getFarmDiaryById_Success() {
        // Given
        when(farmDiaryMapper.selectFarmDiaryById(1)).thenReturn(testFarmDiary);
        
        // When
        FarmDiaryDTO result = farmDiaryService.getFarmDiaryById(1);
        
        // Then
        assertNotNull(result);
        assertEquals(testFarmDiary.get농사일지ID(), result.get농사일지ID());
        assertEquals(testFarmDiary.get활동_유형(), result.get활동_유형());
        verify(farmDiaryMapper).selectFarmDiaryById(1);
    }
    
    @Test
    @DisplayName("농사일지 조회 실패 - 농사일지 없음")
    void getFarmDiaryById_DiaryNotFound() {
        // Given
        when(farmDiaryMapper.selectFarmDiaryById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.getFarmDiaryById(999));
        
        assertEquals(ErrorCode.FARM_DIARY_NOT_FOUND, exception.getErrorCode());
        verify(farmDiaryMapper).selectFarmDiaryById(999);
    }
    
    @Test
    @DisplayName("사용자별 농사일지 목록 조회 성공")
    void getFarmDiaryListByUserId_Success() {
        // Given
        List<FarmDiaryDTO> diaryList = Arrays.asList(testFarmDiary);
        when(userMapper.selectUserById(1)).thenReturn(testUser);
        when(farmDiaryMapper.selectFarmDiaryListByUserId(1)).thenReturn(diaryList);
        
        // When
        List<FarmDiaryDTO> result = farmDiaryService.getFarmDiaryListByUserId(1);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFarmDiary.get활동_유형(), result.get(0).get활동_유형());
        verify(userMapper).selectUserById(1);
        verify(farmDiaryMapper).selectFarmDiaryListByUserId(1);
    }
    
    @Test
    @DisplayName("작물별 농사일지 목록 조회 성공")
    void getFarmDiaryListByCropId_Success() {
        // Given
        List<FarmDiaryDTO> diaryList = Arrays.asList(testFarmDiary);
        when(cropMapper.selectCropById(1)).thenReturn(testCrop);
        when(farmDiaryMapper.selectFarmDiaryListByCropId(1)).thenReturn(diaryList);
        
        // When
        List<FarmDiaryDTO> result = farmDiaryService.getFarmDiaryListByCropId(1);
        
        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testFarmDiary.get활동_유형(), result.get(0).get활동_유형());
        verify(cropMapper).selectCropById(1);
        verify(farmDiaryMapper).selectFarmDiaryListByCropId(1);
    }
    
    @Test
    @DisplayName("농사일지 정보 수정 성공")
    void updateFarmDiary_Success() {
        // Given
        FarmDiaryDTO updateDiary = new FarmDiaryDTO();
        updateDiary.set농사일지ID(1);
        updateDiary.set작물ID(1); // 동일한 작물
        updateDiary.set활동_유형("수정된 활동");
        
        when(farmDiaryMapper.selectFarmDiaryById(1)).thenReturn(testFarmDiary);
        when(farmDiaryMapper.updateFarmDiary(updateDiary)).thenReturn(1);
        
        // When
        int result = farmDiaryService.updateFarmDiary(updateDiary);
        
        // Then
        assertEquals(1, result);
        verify(farmDiaryMapper).selectFarmDiaryById(1);
        verify(farmDiaryMapper).updateFarmDiary(updateDiary);
    }
    
    @Test
    @DisplayName("농사일지 정보 수정 실패 - 농사일지 없음")
    void updateFarmDiary_DiaryNotFound() {
        // Given
        FarmDiaryDTO updateDiary = new FarmDiaryDTO();
        updateDiary.set농사일지ID(999);
        
        when(farmDiaryMapper.selectFarmDiaryById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.updateFarmDiary(updateDiary));
        
        assertEquals(ErrorCode.FARM_DIARY_NOT_FOUND, exception.getErrorCode());
        verify(farmDiaryMapper).selectFarmDiaryById(999);
        verify(farmDiaryMapper, never()).updateFarmDiary(any(FarmDiaryDTO.class));
    }
    
    @Test
    @DisplayName("농사일지 삭제 성공")
    void deleteFarmDiary_Success() {
        // Given
        when(farmDiaryMapper.selectFarmDiaryById(1)).thenReturn(testFarmDiary);
        when(farmDiaryMapper.deleteFarmDiary(1)).thenReturn(1);
        
        // When
        int result = farmDiaryService.deleteFarmDiary(1);
        
        // Then
        assertEquals(1, result);
        verify(farmDiaryMapper).selectFarmDiaryById(1);
        verify(farmDiaryMapper).deleteFarmDiary(1);
    }
    
    @Test
    @DisplayName("농사일지 삭제 실패 - 농사일지 없음")
    void deleteFarmDiary_DiaryNotFound() {
        // Given
        when(farmDiaryMapper.selectFarmDiaryById(999)).thenReturn(null);
        
        // When & Then
        SamyangException exception = assertThrows(SamyangException.class, 
            () -> farmDiaryService.deleteFarmDiary(999));
        
        assertEquals(ErrorCode.FARM_DIARY_NOT_FOUND, exception.getErrorCode());
        verify(farmDiaryMapper).selectFarmDiaryById(999);
        verify(farmDiaryMapper, never()).deleteFarmDiary(anyInt());
    }
}