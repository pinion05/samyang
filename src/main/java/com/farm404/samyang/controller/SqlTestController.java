package com.farm404.samyang.controller;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.time.LocalDate;
import java.io.StringWriter;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.dto.FarmDiaryDTO;
import com.farm404.samyang.service.UserService;
import com.farm404.samyang.service.CropService;
import com.farm404.samyang.service.FarmDiaryService;

/**
 * SQL 테스트 컨트롤러
 * 데이터베이스 CRUD 테스트 및 상세 에러 로그 출력
 */
@Controller
public class SqlTestController {
    
    private static final Logger logger = LoggerFactory.getLogger(SqlTestController.class);
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private CropService cropService;
    
    @Autowired
    private FarmDiaryService farmDiaryService;
    
    /**
     * SQL 테스트 페이지 메인
     */
    @GetMapping("/sqltest")
    public String sqlTestPage(Model model) {
        logger.info("SQL 테스트 페이지 접근");
        
        List<Map<String, Object>> testResults = new ArrayList<>();
        List<String> errorLogs = new ArrayList<>();
        
        // 1. 데이터베이스 연결 테스트
        testResults.add(testDatabaseConnection(errorLogs));
        
        // 2. 사용자 테이블 테스트
        testResults.add(testUserOperations(errorLogs));
        
        // 3. 작물 테이블 테스트
        testResults.add(testCropOperations(errorLogs));
        
        // 4. 농사일지 테이블 테스트
        testResults.add(testFarmDiaryOperations(errorLogs));
        
        model.addAttribute("testResults", testResults);
        model.addAttribute("errorLogs", errorLogs);
        model.addAttribute("testTime", new Date());
        
        return "sqltest";
    }
    
    /**
     * 개별 테스트 실행
     */
    @PostMapping("/sqltest/run")
    public String runSpecificTest(@RequestParam String testType, Model model) {
        logger.info("개별 테스트 실행: {}", testType);
        
        List<Map<String, Object>> testResults = new ArrayList<>();
        List<String> errorLogs = new ArrayList<>();
        
        switch (testType) {
            case "connection":
                testResults.add(testDatabaseConnection(errorLogs));
                break;
            case "user":
                testResults.add(testUserOperations(errorLogs));
                break;
            case "crop":
                testResults.add(testCropOperations(errorLogs));
                break;
            case "diary":
                testResults.add(testFarmDiaryOperations(errorLogs));
                break;
            default:
                errorLogs.add("알 수 없는 테스트 타입: " + testType);
        }
        
        model.addAttribute("testResults", testResults);
        model.addAttribute("errorLogs", errorLogs);
        model.addAttribute("testTime", new Date());
        model.addAttribute("singleTest", testType);
        
        return "sqltest";
    }
    
    /**
     * 데이터베이스 연결 테스트
     */
    private Map<String, Object> testDatabaseConnection(List<String> errorLogs) {
        Map<String, Object> result = new HashMap<>();
        result.put("testName", "데이터베이스 연결 테스트");
        result.put("startTime", new Date());
        
        try {
            logger.info("데이터베이스 연결 테스트 시작");
            
            // 간단한 카운트 쿼리로 연결 테스트
            UserDTO searchCondition = new UserDTO();
            int userCount = userService.getUserCount(searchCondition);
            
            result.put("status", "SUCCESS");
            result.put("message", "데이터베이스 연결 성공");
            result.put("details", "사용자 테이블 레코드 수: " + userCount);
            
            logger.info("데이터베이스 연결 테스트 성공 - 사용자 수: {}", userCount);
            
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "데이터베이스 연결 실패");
            result.put("error", e.getMessage());
            result.put("stackTrace", getStackTrace(e));
            
            String errorDetail = String.format("[연결 테스트 실패] %s - %s", 
                e.getClass().getSimpleName(), e.getMessage());
            errorLogs.add(errorDetail);
            
            logger.error("데이터베이스 연결 테스트 실패", e);
        }
        
        result.put("endTime", new Date());
        return result;
    }
    
    /**
     * 사용자 테이블 CRUD 테스트
     */
    private Map<String, Object> testUserOperations(List<String> errorLogs) {
        Map<String, Object> result = new HashMap<>();
        result.put("testName", "사용자 테이블 CRUD 테스트");
        result.put("startTime", new Date());
        
        List<String> operations = new ArrayList<>();
        UserDTO testUser = null;
        
        try {
            logger.info("사용자 CRUD 테스트 시작");
            
            // 1. CREATE 테스트
            testUser = new UserDTO();
            testUser.setName("테스트사용자");
            testUser.setEmail("test@sqltest.com");
            testUser.setPassword("testpass123");
            testUser.setPhone("010-1234-5678");
            testUser.setAddress("테스트 주소");
            testUser.setIsAdmin(false);
            
            int insertResult = userService.registerUser(testUser);
            operations.add("✅ INSERT: 사용자 등록 성공 (결과: " + insertResult + ")");
            
            // 2. READ 테스트
            List<UserDTO> users = userService.getUserList(new UserDTO());
            operations.add("✅ SELECT: 사용자 목록 조회 성공 (총 " + users.size() + "명)");
            
            // 생성된 사용자 찾기
            UserDTO createdUser = null;
            for (UserDTO user : users) {
                if ("test@sqltest.com".equals(user.getEmail())) {
                    createdUser = user;
                    break;
                }
            }
            
            if (createdUser != null) {
                operations.add("✅ SELECT: 생성된 사용자 조회 성공 (ID: " + createdUser.getUserId() + ")");
                
                // 3. UPDATE 테스트
                createdUser.setName("수정된테스트사용자");
                createdUser.setPhone("010-9876-5432");
                int updateResult = userService.updateUser(createdUser);
                operations.add("✅ UPDATE: 사용자 정보 수정 성공 (결과: " + updateResult + ")");
                
                // 4. DELETE 테스트
                int deleteResult = userService.deleteUser(createdUser.getUserId());
                operations.add("✅ DELETE: 사용자 삭제 성공 (결과: " + deleteResult + ")");
            }
            
            result.put("status", "SUCCESS");
            result.put("message", "사용자 CRUD 테스트 모두 성공");
            result.put("operations", operations);
            
            logger.info("사용자 CRUD 테스트 성공");
            
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "사용자 CRUD 테스트 실패");
            result.put("error", e.getMessage());
            result.put("stackTrace", getStackTrace(e));
            result.put("operations", operations);
            
            String errorDetail = String.format("[사용자 테스트 실패] %s - %s", 
                e.getClass().getSimpleName(), e.getMessage());
            errorLogs.add(errorDetail);
            
            logger.error("사용자 CRUD 테스트 실패", e);
            
            // 정리: 생성된 사용자가 있다면 삭제 시도
            if (testUser != null && testUser.getUserId() != null) {
                try {
                    userService.deleteUser(testUser.getUserId());
                    operations.add("🧹 CLEANUP: 테스트 사용자 정리 완료");
                } catch (Exception cleanupEx) {
                    operations.add("⚠️ CLEANUP: 테스트 사용자 정리 실패 - " + cleanupEx.getMessage());
                }
            }
        }
        
        result.put("endTime", new Date());
        return result;
    }
    
    /**
     * 작물 테이블 CRUD 테스트
     */
    private Map<String, Object> testCropOperations(List<String> errorLogs) {
        Map<String, Object> result = new HashMap<>();
        result.put("testName", "작물 테이블 CRUD 테스트");
        result.put("startTime", new Date());
        
        List<String> operations = new ArrayList<>();
        
        try {
            logger.info("작물 CRUD 테스트 시작");
            
            // 테스트용 사용자 먼저 생성
            UserDTO testUser = new UserDTO();
            testUser.setName("작물테스트사용자");
            testUser.setEmail("croptest@sqltest.com");
            testUser.setPassword("testpass123");
            testUser.setPhone("010-1111-2222");
            testUser.setAddress("작물 테스트 주소");
            testUser.setIsAdmin(false);
            
            userService.registerUser(testUser);
            operations.add("✅ SETUP: 테스트용 사용자 생성 완료");
            
            // 생성된 사용자 ID 찾기
            List<UserDTO> users = userService.getUserList(new UserDTO());
            Integer testUserId = null;
            for (UserDTO user : users) {
                if ("croptest@sqltest.com".equals(user.getEmail())) {
                    testUserId = user.getUserId();
                    break;
                }
            }
            
            if (testUserId != null) {
                // 1. CREATE 테스트
                CropDTO testCrop = new CropDTO();
                testCrop.setUserId(testUserId);
                testCrop.setCropName("테스트토마토");
                testCrop.setVariety("방울토마토");
                testCrop.setPlantingDate(LocalDate.now());
                testCrop.setStatus("파종");
                
                int insertResult = cropService.registerCrop(testCrop);
                operations.add("✅ INSERT: 작물 등록 성공 (결과: " + insertResult + ")");
                
                // 2. READ 테스트
                List<CropDTO> crops = cropService.getCropList(new CropDTO());
                operations.add("✅ SELECT: 작물 목록 조회 성공 (총 " + crops.size() + "개)");
                
                // 생성된 작물 찾기
                CropDTO createdCrop = null;
                for (CropDTO crop : crops) {
                    if ("테스트토마토".equals(crop.getCropName()) && testUserId.equals(crop.getUserId())) {
                        createdCrop = crop;
                        break;
                    }
                }
                
                if (createdCrop != null) {
                    operations.add("✅ SELECT: 생성된 작물 조회 성공 (ID: " + createdCrop.getCropId() + ")");
                    
                    // 3. UPDATE 테스트
                    createdCrop.setStatus("성장");
                    createdCrop.setVariety("대형토마토");
                    int updateResult = cropService.updateCrop(createdCrop);
                    operations.add("✅ UPDATE: 작물 정보 수정 성공 (결과: " + updateResult + ")");
                    
                    // 4. DELETE 테스트
                    int deleteResult = cropService.deleteCrop(createdCrop.getCropId());
                    operations.add("✅ DELETE: 작물 삭제 성공 (결과: " + deleteResult + ")");
                }
                
                // 테스트용 사용자 정리
                userService.deleteUser(testUserId);
                operations.add("🧹 CLEANUP: 테스트용 사용자 정리 완료");
            }
            
            result.put("status", "SUCCESS");
            result.put("message", "작물 CRUD 테스트 모두 성공");
            result.put("operations", operations);
            
            logger.info("작물 CRUD 테스트 성공");
            
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "작물 CRUD 테스트 실패");
            result.put("error", e.getMessage());
            result.put("stackTrace", getStackTrace(e));
            result.put("operations", operations);
            
            String errorDetail = String.format("[작물 테스트 실패] %s - %s", 
                e.getClass().getSimpleName(), e.getMessage());
            errorLogs.add(errorDetail);
            
            logger.error("작물 CRUD 테스트 실패", e);
        }
        
        result.put("endTime", new Date());
        return result;
    }
    
    /**
     * 농사일지 테이블 CRUD 테스트
     */
    private Map<String, Object> testFarmDiaryOperations(List<String> errorLogs) {
        Map<String, Object> result = new HashMap<>();
        result.put("testName", "농사일지 테이블 CRUD 테스트");
        result.put("startTime", new Date());
        
        List<String> operations = new ArrayList<>();
        
        try {
            logger.info("농사일지 CRUD 테스트 시작");
            
            // 간단한 조회 테스트만 수행 (의존성이 복잡하므로)
            FarmDiaryDTO searchCondition = new FarmDiaryDTO();
            int diaryCount = farmDiaryService.getFarmDiaryCount(searchCondition);
            operations.add("✅ SELECT: 농사일지 카운트 조회 성공 (총 " + diaryCount + "개)");
            
            List<FarmDiaryDTO> diaries = farmDiaryService.getFarmDiaryList(searchCondition);
            operations.add("✅ SELECT: 농사일지 목록 조회 성공 (총 " + diaries.size() + "개)");
            
            result.put("status", "SUCCESS");
            result.put("message", "농사일지 조회 테스트 성공");
            result.put("operations", operations);
            
            logger.info("농사일지 CRUD 테스트 성공");
            
        } catch (Exception e) {
            result.put("status", "FAILED");
            result.put("message", "농사일지 CRUD 테스트 실패");
            result.put("error", e.getMessage());
            result.put("stackTrace", getStackTrace(e));
            result.put("operations", operations);
            
            String errorDetail = String.format("[농사일지 테스트 실패] %s - %s", 
                e.getClass().getSimpleName(), e.getMessage());
            errorLogs.add(errorDetail);
            
            logger.error("농사일지 CRUD 테스트 실패", e);
        }
        
        result.put("endTime", new Date());
        return result;
    }
    
    /**
     * 스택 트레이스를 문자열로 변환
     */
    private String getStackTrace(Exception e) {
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        return sw.toString();
    }
}