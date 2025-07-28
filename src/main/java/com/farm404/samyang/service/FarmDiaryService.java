package com.farm404.samyang.service;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.exception.ErrorCode;
import com.farm404.samyang.exception.SamyangException;
import com.farm404.samyang.mapper.FarmDiaryMapper;
import com.farm404.samyang.mapper.UserMapper;
import com.farm404.samyang.mapper.CropMapper;

/**
 * 농사일지 서비스 클래스
 * 농사일지 관련 비즈니스 로직 처리
 */
@Service
@Transactional
public class FarmDiaryService {
    
    private static final Logger logger = LoggerFactory.getLogger(FarmDiaryService.class);
    
    @Autowired
    private FarmDiaryMapper farmDiaryMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private CropMapper cropMapper;
    
    /**
     * 농사일지 등록
     */
    public int registerFarmDiary(FarmDiaryDTO farmDiary) {
        logger.info("농사일지 등록 시도: 사용자ID {}, 작물ID {}", farmDiary.get사용자ID(), farmDiary.get작물ID());
        
        try {
            // 사용자 존재 여부 확인
            UserDTO user = userMapper.selectUserById(farmDiary.get사용자ID());
            if (user == null) {
                logger.warn("존재하지 않는 사용자 ID로 농사일지 등록 시도: {}", farmDiary.get사용자ID());
                throw new SamyangException(ErrorCode.USER_NOT_FOUND);
            }
            
            // 작물 존재 여부 확인 (작물ID가 있는 경우)
            if (farmDiary.get작물ID() != null) {
                CropDTO crop = cropMapper.selectCropById(farmDiary.get작물ID());
                if (crop == null) {
                    logger.warn("존재하지 않는 작물 ID로 농사일지 등록 시도: {}", farmDiary.get작물ID());
                    throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
                }
                
                // 해당 작물이 사용자의 작물인지 확인
                if (!crop.get사용자ID().equals(farmDiary.get사용자ID())) {
                    logger.warn("다른 사용자의 작물로 농사일지 등록 시도: 작물소유자 {}, 요청사용자 {}", crop.get사용자ID(), farmDiary.get사용자ID());
                    throw new SamyangException(ErrorCode.CROP_ACCESS_DENIED);
                }
            }
            
            int result = farmDiaryMapper.insertFarmDiary(farmDiary);
            logger.info("농사일지 등록 완료: ID {}", farmDiary.get농사일지ID());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("농사일지 등록 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 농사일지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getFarmDiaryList(FarmDiaryDTO searchCondition) {
        return farmDiaryMapper.selectFarmDiaryList(searchCondition);
    }
    
    /**
     * 농사일지 상세 조회
     */
    @Transactional(readOnly = true)
    public FarmDiaryDTO getFarmDiaryById(Integer 농사일지ID) {
        logger.debug("농사일지 조회 요청: ID {}", 농사일지ID);
        
        FarmDiaryDTO farmDiary = farmDiaryMapper.selectFarmDiaryById(농사일지ID);
        if (farmDiary == null) {
            logger.warn("존재하지 않는 농사일지 ID 조회 시도: {}", 농사일지ID);
            throw new SamyangException(ErrorCode.FARM_DIARY_NOT_FOUND);
        }
        
        logger.debug("농사일지 조회 완료: ID {}", 농사일지ID);
        return farmDiary;
    }
    
    /**
     * 사용자별 농사일지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getFarmDiaryListByUserId(Integer 사용자ID) {
        logger.debug("사용자별 농사일지 목록 조회: 사용자ID {}", 사용자ID);
        
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(사용자ID);
        if (user == null) {
            logger.warn("존재하지 않는 사용자 ID로 농사일지 목록 조회 시도: {}", 사용자ID);
            throw new SamyangException(ErrorCode.USER_NOT_FOUND);
        }
        
        List<FarmDiaryDTO> diaries = farmDiaryMapper.selectFarmDiaryListByUserId(사용자ID);
        logger.debug("사용자별 농사일지 목록 조회 완료: {} 건", diaries.size());
        return diaries;
    }
    
    /**
     * 작물별 농사일지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getFarmDiaryListByCropId(Integer 작물ID) {
        logger.debug("작물별 농사일지 목록 조회: 작물ID {}", 작물ID);
        
        // 작물 존재 여부 확인
        CropDTO crop = cropMapper.selectCropById(작물ID);
        if (crop == null) {
            logger.warn("존재하지 않는 작물 ID로 농사일지 목록 조회 시도: {}", 작물ID);
            throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
        }
        
        List<FarmDiaryDTO> diaries = farmDiaryMapper.selectFarmDiaryListByCropId(작물ID);
        logger.debug("작물별 농사일지 목록 조회 완료: {} 건", diaries.size());
        return diaries;
    }
    
    /**
     * 농사일지 정보 수정
     */
    public int updateFarmDiary(FarmDiaryDTO farmDiary) {
        logger.info("농사일지 정보 수정 시도: ID {}", farmDiary.get농사일지ID());
        
        try {
            // 존재하는 농사일지인지 확인
            FarmDiaryDTO existingDiary = farmDiaryMapper.selectFarmDiaryById(farmDiary.get농사일지ID());
            if (existingDiary == null) {
                logger.warn("존재하지 않는 농사일지 ID 수정 시도: {}", farmDiary.get농사일지ID());
                throw new SamyangException(ErrorCode.FARM_DIARY_NOT_FOUND);
            }
            
            // 작물 변경 시 존재 여부 및 소유권 확인
            if (farmDiary.get작물ID() != null && !farmDiary.get작물ID().equals(existingDiary.get작물ID())) {
                CropDTO crop = cropMapper.selectCropById(farmDiary.get작물ID());
                if (crop == null) {
                    logger.warn("존재하지 않는 작물 ID로 농사일지 작물 변경 시도: {}", farmDiary.get작물ID());
                    throw new SamyangException(ErrorCode.CROP_NOT_FOUND);
                }
                
                if (!crop.get사용자ID().equals(existingDiary.get사용자ID())) {
                    logger.warn("다른 사용자의 작물로 농사일지 작물 변경 시도: 작물소유자 {}, 일지소유자 {}", crop.get사용자ID(), existingDiary.get사용자ID());
                    throw new SamyangException(ErrorCode.CROP_ACCESS_DENIED);
                }
            }
            
            int result = farmDiaryMapper.updateFarmDiary(farmDiary);
            logger.info("농사일지 정보 수정 완료: ID {}", farmDiary.get농사일지ID());
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("농사일지 정보 수정 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 농사일지 삭제
     */
    public int deleteFarmDiary(Integer 농사일지ID) {
        logger.info("농사일지 삭제 시도: ID {}", 농사일지ID);
        
        try {
            // 존재하는 농사일지인지 확인
            FarmDiaryDTO existingDiary = farmDiaryMapper.selectFarmDiaryById(농사일지ID);
            if (existingDiary == null) {
                logger.warn("존재하지 않는 농사일지 ID 삭제 시도: {}", 농사일지ID);
                throw new SamyangException(ErrorCode.FARM_DIARY_NOT_FOUND);
            }
            
            int result = farmDiaryMapper.deleteFarmDiary(농사일지ID);
            logger.info("농사일지 삭제 완료: ID {}", 농사일지ID);
            return result;
            
        } catch (SamyangException e) {
            throw e;
        } catch (Exception e) {
            logger.error("농사일지 삭제 중 데이터베이스 오류", e);
            throw new SamyangException(ErrorCode.DATABASE_ERROR, e);
        }
    }
    
    /**
     * 농사일지 수 조회
     */
    @Transactional(readOnly = true)
    public int getFarmDiaryCount(FarmDiaryDTO searchCondition) {
        return farmDiaryMapper.selectFarmDiaryCount(searchCondition);
    }
    
    /**
     * 사용자별 농사일지 수 조회
     */
    @Transactional(readOnly = true)
    public int getFarmDiaryCountByUserId(Integer 사용자ID) {
        return farmDiaryMapper.selectFarmDiaryCountByUserId(사용자ID);
    }
    
    /**
     * 작물별 농사일지 수 조회
     */
    @Transactional(readOnly = true)
    public int getFarmDiaryCountByCropId(Integer 작물ID) {
        return farmDiaryMapper.selectFarmDiaryCountByCropId(작물ID);
    }
    
    /**
     * 최근 농사일지 목록 조회 (편의 메서드)
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getRecentFarmDiaries(int limit) {
        FarmDiaryDTO searchCondition = new FarmDiaryDTO();
        List<FarmDiaryDTO> diaries = farmDiaryMapper.selectFarmDiaryList(searchCondition);
        
        // 최대 개수 제한
        if (diaries.size() > limit) {
            return diaries.subList(0, limit);
        }
        
        return diaries;
    }
}