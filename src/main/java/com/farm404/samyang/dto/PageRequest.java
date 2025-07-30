package com.farm404.samyang.dto;

/**
 * 페이징 요청 DTO
 * 페이징 처리를 위한 공통 DTO
 */
public class PageRequest {
    
    private int page = 1;           // 현재 페이지 (기본값: 1)
    private int size = 10;          // 페이지당 항목 수 (기본값: 10)
    private String sortBy;          // 정렬 필드
    private String sortDirection = "DESC"; // 정렬 방향 (기본값: DESC)
    
    // 계산된 값
    private int offset;             // LIMIT 쿼리용 offset
    
    public PageRequest() {
        this.offset = 0;
    }
    
    public PageRequest(int page, int size) {
        this.page = page;
        this.size = size;
        this.offset = (page - 1) * size;
    }
    
    // Getters and Setters
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
        this.offset = (page - 1) * this.size;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
        this.offset = (this.page - 1) * size;
    }
    
    public String getSortBy() {
        return sortBy;
    }
    
    public void setSortBy(String sortBy) {
        this.sortBy = sortBy;
    }
    
    public String getSortDirection() {
        return sortDirection;
    }
    
    public void setSortDirection(String sortDirection) {
        this.sortDirection = sortDirection;
    }
    
    public int getOffset() {
        return offset;
    }
    
    // 유틸리티 메소드
    public boolean hasSorting() {
        return sortBy != null && !sortBy.isEmpty();
    }
    
    public String getOrderByClause() {
        if (hasSorting()) {
            return sortBy + " " + sortDirection;
        }
        return null;
    }
}