package com.farm404.samyang.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.farm404.samyang.dto.FarmDiaryDTO;

/**
 * 농사일지 매퍼 인터페이스
 * 농사일지 테이블에 대한 CRUD 작업 정의
 */
@Mapper
public interface FarmDiaryMapper {
    
    /**
     * 농사일지 등록
     */
    int insertFarmDiary(FarmDiaryDTO farmDiary);
    
    /**
     * 농사일지 목록 조회
     */
    List<FarmDiaryDTO> selectFarmDiaryList(FarmDiaryDTO searchCondition);
    
    /**
     * 농사일지 상세 조회
     */
    FarmDiaryDTO selectFarmDiaryById(Integer 농사일지ID);
    
    /**
     * 사용자별 농사일지 목록 조회
     */
    List<FarmDiaryDTO> selectFarmDiaryListByUserId(Integer 사용자ID);
    
    /**
     * 작물별 농사일지 목록 조회
     */
    List<FarmDiaryDTO> selectFarmDiaryListByCropId(Integer 작물ID);
    
    /**
     * 농사일지 정보 수정
     */
    int updateFarmDiary(FarmDiaryDTO farmDiary);
    
    /**
     * 농사일지 삭제
     */
    int deleteFarmDiary(Integer 농사일지ID);
    
    /**
     * 농사일지 수 조회
     */
    int selectFarmDiaryCount(FarmDiaryDTO searchCondition);
    
    /**
     * 사용자별 농사일지 수 조회
     */
    int selectFarmDiaryCountByUserId(Integer 사용자ID);
    
    /**
     * 작물별 농사일지 수 조회
     */
    int selectFarmDiaryCountByCropId(Integer 작물ID);
}