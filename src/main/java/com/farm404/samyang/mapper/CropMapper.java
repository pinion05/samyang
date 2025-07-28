package com.farm404.samyang.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.farm404.samyang.dto.CropDTO;

/**
 * 작물 매퍼 인터페이스
 * 작물 테이블에 대한 CRUD 작업 정의
 */
@Mapper
public interface CropMapper {
    
    /**
     * 작물 등록
     */
    int insertCrop(CropDTO crop);
    
    /**
     * 작물 목록 조회
     */
    List<CropDTO> selectCropList(CropDTO searchCondition);
    
    /**
     * 작물 상세 조회
     */
    CropDTO selectCropById(Integer 작물ID);
    
    /**
     * 사용자별 작물 목록 조회
     */
    List<CropDTO> selectCropListByUserId(Integer 사용자ID);
    
    /**
     * 작물 정보 수정
     */
    int updateCrop(CropDTO crop);
    
    /**
     * 작물 삭제
     */
    int deleteCrop(Integer 작물ID);
    
    /**
     * 작물 수 조회
     */
    int selectCropCount(CropDTO searchCondition);
    
    /**
     * 사용자별 작물 수 조회
     */
    int selectCropCountByUserId(Integer 사용자ID);
}