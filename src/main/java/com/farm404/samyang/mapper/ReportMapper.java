package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.ReportDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReportMapper {
    // 신고 등록
    int insertReport(ReportDTO report);
    
    // 신고 조회
    ReportDTO selectReportById(Integer reportId);
    
    // 전체 신고 목록 조회 (관리자용)
    List<ReportDTO> selectAllReports();
    
    // 상태별 신고 목록 조회
    List<ReportDTO> selectReportsByStatus(String status);
    
    // 카테고리별 신고 목록 조회
    List<ReportDTO> selectReportsByCategory(String category);
    
    // 신고 유형별 목록 조회
    List<ReportDTO> selectReportsByType(String reportedType);
    
    // 사용자의 신고 목록 조회
    List<ReportDTO> selectReportsByReporter(Integer reporterId);
    
    // 특정 대상에 대한 신고 목록 조회
    List<ReportDTO> selectReportsByTarget(@Param("reportedId") Integer reportedId, 
                                         @Param("reportedType") String reportedType);
    
    // 신고 상태 업데이트
    int updateReportStatus(@Param("reportId") Integer reportId, 
                          @Param("status") String status);
    
    // 관리자 노트 업데이트
    int updateAdminNote(@Param("reportId") Integer reportId, 
                       @Param("adminNote") String adminNote,
                       @Param("resolvedAt") java.util.Date resolvedAt);
    
    // 신고 삭제
    int deleteReport(Integer reportId);
    
    // 중복 신고 확인
    int checkDuplicateReport(@Param("reporterId") Integer reporterId,
                            @Param("reportedId") Integer reportedId,
                            @Param("reportedType") String reportedType);
    
    // 신고 통계 조회
    int countReportsByStatus(String status);
    int countReportsByCategory(String category);
    int countReportsByType(String reportedType);
}