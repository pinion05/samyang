package com.farm404.samyang.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/database")
public class DatabaseInfoController {

    @Autowired
    private DataSource dataSource;

    @GetMapping("/tables")
    public Map<String, Object> getAllTables() {
        Map<String, Object> result = new HashMap<>();
        List<String> tables = new ArrayList<>();
        Map<String, Boolean> targetTables = new HashMap<>();
        
        // 확인하고자 하는 테이블 목록
        String[] checkTables = {"User", "Crop", "FarmingDiary", "PaymentMethod", "Review", "Comment", "Report"};
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            // 모든 테이블 조회
            ResultSet rs = metaData.getTables(null, null, "%", new String[]{"TABLE"});
            
            while (rs.next()) {
                String tableName = rs.getString("TABLE_NAME");
                tables.add(tableName);
                
                // 대소문자 구분 없이 확인
                for (String checkTable : checkTables) {
                    if (tableName.equalsIgnoreCase(checkTable)) {
                        targetTables.put(checkTable, true);
                    }
                }
            }
            
            // 존재하지 않는 테이블 표시
            for (String checkTable : checkTables) {
                if (!targetTables.containsKey(checkTable)) {
                    targetTables.put(checkTable, false);
                }
            }
            
            result.put("allTables", tables);
            result.put("totalCount", tables.size());
            result.put("targetTablesStatus", targetTables);
            result.put("status", "success");
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
    
    @GetMapping("/table-structure")
    public Map<String, Object> getTableStructure() {
        Map<String, Object> result = new HashMap<>();
        Map<String, List<Map<String, String>>> tableStructures = new HashMap<>();
        
        String[] checkTables = {"User", "Crop", "FarmingDiary", "PaymentMethod", "Review", "Comment", "Report"};
        
        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            
            for (String tableName : checkTables) {
                List<Map<String, String>> columns = new ArrayList<>();
                
                // 대소문자 모두 시도
                ResultSet rs = metaData.getColumns(null, null, tableName, null);
                if (!rs.next()) {
                    rs = metaData.getColumns(null, null, tableName.toLowerCase(), null);
                    if (!rs.next()) {
                        rs = metaData.getColumns(null, null, tableName.toUpperCase(), null);
                    }
                }
                
                // ResultSet을 처음부터 다시 읽기 위해 재조회
                rs = metaData.getColumns(null, null, tableName, null);
                if (!rs.next()) {
                    rs = metaData.getColumns(null, null, tableName.toLowerCase(), null);
                    if (!rs.next()) {
                        rs = metaData.getColumns(null, null, tableName.toUpperCase(), null);
                    }
                }
                
                do {
                    Map<String, String> column = new HashMap<>();
                    column.put("columnName", rs.getString("COLUMN_NAME"));
                    column.put("dataType", rs.getString("TYPE_NAME"));
                    column.put("columnSize", rs.getString("COLUMN_SIZE"));
                    column.put("nullable", rs.getString("IS_NULLABLE"));
                    columns.add(column);
                } while (rs.next());
                
                if (!columns.isEmpty()) {
                    tableStructures.put(tableName, columns);
                }
            }
            
            result.put("tableStructures", tableStructures);
            result.put("status", "success");
            
        } catch (Exception e) {
            result.put("status", "error");
            result.put("message", e.getMessage());
            e.printStackTrace();
        }
        
        return result;
    }
}