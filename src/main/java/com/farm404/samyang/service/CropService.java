package com.farm404.samyang.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.mapper.CropMapper;
import com.farm404.samyang.mapper.UserMapper;

/**
 * 작물 서비스 클래스
 * 작물 관련 비즈니스 로직 처리
 */
@Service
@Transactional
public class CropService {
    
    @Autowired
    private CropMapper cropMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 작물 등록
     */
    public int registerCrop(CropDTO crop) {
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(crop.get사용자ID());
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        
        // 기본값 설정
        if (crop.get상태() == null || crop.get상태().isEmpty()) {
            crop.set상태("파종");
        }
        
        return cropMapper.insertCrop(crop);
    }
    
    /**
     * 작물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CropDTO> getCropList(CropDTO searchCondition) {
        return cropMapper.selectCropList(searchCondition);
    }
    
    /**
     * 작물 상세 조회
     */
    @Transactional(readOnly = true)
    public CropDTO getCropById(Integer 작물ID) {
        CropDTO crop = cropMapper.selectCropById(작물ID);
        if (crop == null) {
            throw new RuntimeException("작물을 찾을 수 없습니다.");
        }
        return crop;
    }
    
    /**
     * 사용자별 작물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CropDTO> getCropListByUserId(Integer 사용자ID) {
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(사용자ID);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        
        return cropMapper.selectCropListByUserId(사용자ID);
    }
    
    /**
     * 작물 정보 수정
     */
    public int updateCrop(CropDTO crop) {
        // 존재하는 작물인지 확인
        CropDTO existingCrop = cropMapper.selectCropById(crop.get작물ID());
        if (existingCrop == null) {
            throw new RuntimeException("작물을 찾을 수 없습니다.");
        }
        
        // 사용자 변경 시 존재 여부 확인
        if (!existingCrop.get사용자ID().equals(crop.get사용자ID())) {
            UserDTO user = userMapper.selectUserById(crop.get사용자ID());
            if (user == null) {
                throw new RuntimeException("존재하지 않는 사용자입니다.");
            }
        }
        
        return cropMapper.updateCrop(crop);
    }
    
    /**
     * 작물 삭제
     */
    public int deleteCrop(Integer 작물ID) {
        // 존재하는 작물인지 확인
        CropDTO existingCrop = cropMapper.selectCropById(작물ID);
        if (existingCrop == null) {
            throw new RuntimeException("작물을 찾을 수 없습니다.");
        }
        
        return cropMapper.deleteCrop(작물ID);
    }
    
    /**
     * 작물 수 조회
     */
    @Transactional(readOnly = true)
    public int getCropCount(CropDTO searchCondition) {
        return cropMapper.selectCropCount(searchCondition);
    }
    
    /**
     * 사용자별 작물 수 조회
     */
    @Transactional(readOnly = true)
    public int getCropCountByUserId(Integer 사용자ID) {
        return cropMapper.selectCropCountByUserId(사용자ID);
    }
    
    /**
     * 작물 상태 업데이트 (편의 메서드)
     */
    public int updateCropStatus(Integer 작물ID, String 상태) {
        CropDTO crop = getCropById(작물ID);
        crop.set상태(상태);
        return cropMapper.updateCrop(crop);
    }
    
    /**
     * 수확 가능한 작물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CropDTO> getHarvestableCrops() {
        CropDTO searchCondition = new CropDTO();
        searchCondition.set상태필터("수확 완료");
        return cropMapper.selectCropList(searchCondition);
    }
}