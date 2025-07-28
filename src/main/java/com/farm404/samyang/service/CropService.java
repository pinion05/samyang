package com.farm404.samyang.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.CropMapper;
import com.farm404.samyang.mapper.UserMapper;

/**
 * 작물 서비스 클래스
 * 작물 관련 비즈니스 로직 처리
 */
@Service
@Transactional
public class CropService {
    
    private static final Logger logger = LoggerFactory.getLogger(CropService.class);
    
    @Autowired
    private CropMapper cropMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    /**
     * 작물 등록
     */
    public int registerCrop(CropDTO crop) {
        logger.info("작물 등록 시도: {} (사용자ID: {})", crop.getCropName(), crop.getUserId());
        
        try {
            // 사용자 존재 여부 확인
            UserDTO user = userMapper.selectUserById(crop.getUserId());
            if (user == null) {
                logger.warn("존재하지 않는 사용자 ID로 작물 등록 시도: {}", crop.getUserId());
                throw new SamyangException(ErrorCode.USER_NOT_FOUND);
            }
            
            // 기본값 설정
            if (crop.getStatus() == null || crop.getStatus().isEmpty()) {
                crop.setStatus("파종");
            }
            
            int result = cropMapper.insertCrop(crop);
            logger.info("작물 등록 완료: {} (ID: {})", crop.getCropName(), crop.getCropId());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("작물 등록 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
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
    public CropDTO getCropById(Integer cropId) {
        logger.debug("작물 조회 요청: ID {}", cropId);
        
        CropDTO crop = cropMapper.selectCropById(cropId);
        if (crop == null) {
            logger.warn("존재하지 않는 작물 ID 조회 시도: {}", cropId);
            throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
        }
        
        logger.debug("작물 조회 완료: {} ({})", crop.getCropName(), crop.getStatus());
        return crop;
    }
    
    /**
     * 사용자별 작물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CropDTO> getCropListByUserId(Integer userId) {
        logger.debug("사용자별 작물 목록 조회: 사용자ID {}", userId);
        
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(userId);
        if (user == null) {
            logger.warn("존재하지 않는 사용자 ID로 작물 목록 조회 시도: {}", userId);
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        List<CropDTO> crops = cropMapper.selectCropListByUserId(userId);
        logger.debug("사용자별 작물 목록 조회 완료: {} 건", crops.size());
        return crops;
    }
    
    /**
     * 작물 정보 수정
     */
    public int updateCrop(CropDTO crop) {
        logger.info("작물 정보 수정 시도: ID {}", crop.getCropId());
        
        try {
            // 존재하는 작물인지 확인
            CropDTO existingCrop = cropMapper.selectCropById(crop.getCropId());
            if (existingCrop == null) {
                logger.warn("존재하지 않는 작물 ID 수정 시도: {}", crop.getCropId());
                throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
            }
            
            // 사용자 변경 시 존재 여부 확인
            if (!existingCrop.getUserId().equals(crop.getUserId())) {
                UserDTO user = userMapper.selectUserById(crop.getUserId());
                if (user == null) {
                    logger.warn("존재하지 않는 사용자 ID로 작물 소유자 변경 시도: {}", crop.getUserId());
                    throw new SamyangException(ErrorCode.USER_NOT_FOUND);
                }
            }
            
            int result = cropMapper.updateCrop(crop);
            logger.info("작물 정보 수정 완료: {} ({})", crop.getCropName(), crop.getStatus());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("작물 정보 수정 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 작물 삭제
     */
    public int deleteCrop(Integer cropId) {
        logger.info("작물 삭제 시도: ID {}", cropId);
        
        try {
            // 존재하는 작물인지 확인
            CropDTO existingCrop = cropMapper.selectCropById(cropId);
            if (existingCrop == null) {
                logger.warn("존재하지 않는 작물 ID 삭제 시도: {}", cropId);
                throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
            }
            
            int result = cropMapper.deleteCrop(cropId);
            logger.info("작물 삭제 완료: {} ({})", existingCrop.getCropName(), existingCrop.getStatus());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("작물 삭제 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
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
    public int getCropCountByUserId(Integer userId) {
        return cropMapper.selectCropCountByUserId(userId);
    }
    
    /**
     * 작물 상태 업데이트 (편의 메서드)
     */
    public int updateCropStatus(Integer cropId, String status) {
        CropDTO crop = getCropById(cropId);
        crop.setStatus(status);
        return cropMapper.updateCrop(crop);
    }
    
    /**
     * 수확 가능한 작물 목록 조회
     */
    @Transactional(readOnly = true)
    public List<CropDTO> getHarvestableCrops() {
        CropDTO searchCondition = new CropDTO();
        searchCondition.setStatusFilter("수확 완료");
        return cropMapper.selectCropList(searchCondition);
    }
}