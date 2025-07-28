package com.farm404.samyang.dto;

/**
 * 페이징 요청 DTO
 */
public class PageRequest {
    
    private int page = 1;           // 현재 페이지 (1부터 시작)
    private int size = 10;          // 페이지당 항목 수
    private String sortBy = "id";   // 정렬 기준 필드
    private String sortDir = "DESC"; // 정렬 방향 (ASC, DESC)
    
    // 계산된 오프셋
    public int getOffset() {
        return (page - 1) * size;
    }
    
    // Getter/Setter
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = Math.max(1, page);
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = Math.min(Math.max(1, size), 100); // 최대 100개로 제한
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDir() {
        return sortDir;
    }
    
    public void setSortDir(String sortDir) {
        this.sortDir = "ASC".equalsIgnoreCase(sortDir) ? "ASC" : "DESC";
    }
}