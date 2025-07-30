package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.CropDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CropMapper {
    // 작물 정보 조회
    CropDTO selectCropById(Integer cropId);
    
    // 사용자별 작물 목록 조회
    List<CropDTO> selectCropsByUserId(Integer userId);
    
    // 모든 작물 목록 조회
    List<CropDTO> selectAllCrops();
    
    // 작물 검색 (이름, 품종)
    List<CropDTO> searchCrops(@Param("keyword") String keyword);
    
    // 작물 등록
    int insertCrop(CropDTO crop);
    
    // 작물 정보 수정
    int updateCrop(CropDTO crop);
    
    // 작물 삭제
    int deleteCrop(Integer cropId);
    
    // 작물 상태별 조회
    List<CropDTO> selectCropsByStatus(@Param("status") String status);
}