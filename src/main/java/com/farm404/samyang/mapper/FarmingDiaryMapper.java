package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.FarmingDiaryDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

@Mapper
public interface FarmingDiaryMapper {
    // 농업일지 조회
    FarmingDiaryDTO selectDiaryById(Integer farmingDiaryId);
    
    // 사용자별 농업일지 목록 조회
    List<FarmingDiaryDTO> selectDiariesByUserId(Integer userId);
    
    // 날짜별 농업일지 조회
    List<FarmingDiaryDTO> selectDiariesByDate(@Param("date") LocalDate date);
    
    // 날짜 범위로 농업일지 조회
    List<FarmingDiaryDTO> selectDiariesByDateRange(
        @Param("userId") Integer userId,
        @Param("startDate") LocalDate startDate, 
        @Param("endDate") LocalDate endDate
    );
    
    // 활동 유형별 농업일지 조회
    List<FarmingDiaryDTO> selectDiariesByActivityType(
        @Param("userId") Integer userId,
        @Param("activityType") String activityType
    );
    
    // 모든 농업일지 조회 (관리자용)
    List<FarmingDiaryDTO> selectAllDiaries();
    
    // 농업일지 등록
    int insertDiary(FarmingDiaryDTO diary);
    
    // 농업일지 수정
    int updateDiary(FarmingDiaryDTO diary);
    
    // 농업일지 삭제
    int deleteDiary(Integer farmingDiaryId);
    
    // 월별 농업일지 조회 (캘린더용)
    List<FarmingDiaryDTO> selectDiariesByMonth(
        @Param("userId") Integer userId,
        @Param("year") int year,
        @Param("month") int month
    );
}