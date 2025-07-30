package com.farm404.samyang.service;

import com.farm404.samyang.dto.FarmingDiaryDTO;
import com.farm404.samyang.mapper.FarmingDiaryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional(readOnly = true) // 기본적으로 읽기 전용 트랜잭션
public class FarmingDiaryService {
    
    @Autowired
    private FarmingDiaryMapper farmingDiaryMapper;
    
    public FarmingDiaryDTO getDiaryById(Integer farmingDiaryId) {
        return farmingDiaryMapper.selectDiaryById(farmingDiaryId);
    }
    
    public List<FarmingDiaryDTO> getDiariesByUserId(Integer userId) {
        return farmingDiaryMapper.selectDiariesByUserId(userId);
    }
    
    public List<FarmingDiaryDTO> getDiariesByDate(LocalDate date) {
        return farmingDiaryMapper.selectDiariesByDate(date);
    }
    
    public List<FarmingDiaryDTO> getDiariesByDateRange(Integer userId, LocalDate startDate, LocalDate endDate) {
        return farmingDiaryMapper.selectDiariesByDateRange(userId, startDate, endDate);
    }
    
    public List<FarmingDiaryDTO> getDiariesByActivityType(Integer userId, String activityType) {
        return farmingDiaryMapper.selectDiariesByActivityType(userId, activityType);
    }
    
    public List<FarmingDiaryDTO> getAllDiaries() {
        return farmingDiaryMapper.selectAllDiaries();
    }
    
    @Transactional // 쓰기 작업이므로 읽기 전용 해제
    public boolean registerDiary(FarmingDiaryDTO diary) {
        return farmingDiaryMapper.insertDiary(diary) > 0;
    }
    
    @Transactional // 쓰기 작업이므로 읽기 전용 해제
    public boolean updateDiary(FarmingDiaryDTO diary) {
        return farmingDiaryMapper.updateDiary(diary) > 0;
    }
    
    @Transactional // 쓰기 작업이므로 읽기 전용 해제
    public boolean deleteDiary(Integer farmingDiaryId) {
        return farmingDiaryMapper.deleteDiary(farmingDiaryId) > 0;
    }
    
    public List<FarmingDiaryDTO> getDiariesByMonth(Integer userId, int year, int month) {
        return farmingDiaryMapper.selectDiariesByMonth(userId, year, month);
    }
}