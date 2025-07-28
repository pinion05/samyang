package com.farm404.samyang.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
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
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(farmDiary.get사용자ID());
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        
        // 작물 존재 여부 확인 (작물ID가 있는 경우)
        if (farmDiary.get작물ID() != null) {
            CropDTO crop = cropMapper.selectCropById(farmDiary.get작물ID());
            if (crop == null) {
                throw new RuntimeException("존재하지 않는 작물입니다.");
            }
            
            // 해당 작물이 사용자의 작물인지 확인
            if (!crop.get사용자ID().equals(farmDiary.get사용자ID())) {
                throw new RuntimeException("다른 사용자의 작물입니다.");
            }
        }
        
        return farmDiaryMapper.insertFarmDiary(farmDiary);
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
        FarmDiaryDTO farmDiary = farmDiaryMapper.selectFarmDiaryById(농사일지ID);
        if (farmDiary == null) {
            throw new RuntimeException("농사일지를 찾을 수 없습니다.");
        }
        return farmDiary;
    }
    
    /**
     * 사용자별 농사일지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getFarmDiaryListByUserId(Integer 사용자ID) {
        // 사용자 존재 여부 확인
        UserDTO user = userMapper.selectUserById(사용자ID);
        if (user == null) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        
        return farmDiaryMapper.selectFarmDiaryListByUserId(사용자ID);
    }
    
    /**
     * 작물별 농사일지 목록 조회
     */
    @Transactional(readOnly = true)
    public List<FarmDiaryDTO> getFarmDiaryListByCropId(Integer 작물ID) {
        // 작물 존재 여부 확인
        CropDTO crop = cropMapper.selectCropById(작물ID);
        if (crop == null) {
            throw new RuntimeException("존재하지 않는 작물입니다.");
        }
        
        return farmDiaryMapper.selectFarmDiaryListByCropId(작물ID);
    }
    
    /**
     * 농사일지 정보 수정
     */
    public int updateFarmDiary(FarmDiaryDTO farmDiary) {
        // 존재하는 농사일지인지 확인
        FarmDiaryDTO existingDiary = farmDiaryMapper.selectFarmDiaryById(farmDiary.get농사일지ID());
        if (existingDiary == null) {
            throw new RuntimeException("농사일지를 찾을 수 없습니다.");
        }
        
        // 작물 변경 시 존재 여부 및 소유권 확인
        if (farmDiary.get작물ID() != null && !farmDiary.get작물ID().equals(existingDiary.get작물ID())) {
            CropDTO crop = cropMapper.selectCropById(farmDiary.get작물ID());
            if (crop == null) {
                throw new RuntimeException("존재하지 않는 작물입니다.");
            }
            
            if (!crop.get사용자ID().equals(existingDiary.get사용자ID())) {
                throw new RuntimeException("다른 사용자의 작물입니다.");
            }
        }
        
        return farmDiaryMapper.updateFarmDiary(farmDiary);
    }
    
    /**
     * 농사일지 삭제
     */
    public int deleteFarmDiary(Integer 농사일지ID) {
        // 존재하는 농사일지인지 확인
        FarmDiaryDTO existingDiary = farmDiaryMapper.selectFarmDiaryById(농사일지ID);
        if (existingDiary == null) {
            throw new RuntimeException("농사일지를 찾을 수 없습니다.");
        }
        
        return farmDiaryMapper.deleteFarmDiary(농사일지ID);
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