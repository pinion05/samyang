package com.farm404.samyang.dto;

import java.util.List;

/**
 * 페이징 응답 DTO
 * 페이징된 결과를 반환하기 위한 공통 DTO
 */
public class PageResponse<T> {
    
    private List<T> content;        // 페이지 데이터
    private int page;               // 현재 페이지
    private int size;               // 페이지 크기
    private long totalElements;     // 전체 항목 수
    private int totalPages;         // 전체 페이지 수
    private boolean first;          // 첫 페이지 여부
    private boolean last;           // 마지막 페이지 여부
    
    public PageResponse() {
    }
    
    public PageResponse(List<T> content, int page, int size, long totalElements) {
        this.content = content;
        this.page = page;
        this.size = size;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / size);
        this.first = page == 1;
        this.last = page >= totalPages;
    }
    
    // Getters and Setters
    public List<T> getContent() {
        return content;
    }
    
    public void setContent(List<T> content) {
        this.content = content;
    }
    
    public int getPage() {
        return page;
    }
    
    public void setPage(int page) {
        this.page = page;
    }
    
    public int getSize() {
        return size;
    }
    
    public void setSize(int size) {
        this.size = size;
    }
    
    public long getTotalElements() {
        return totalElements;
    }
    
    public void setTotalElements(long totalElements) {
        this.totalElements = totalElements;
    }
    
    public int getTotalPages() {
        return totalPages;
    }
    
    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
    
    public boolean isFirst() {
        return first;
    }
    
    public void setFirst(boolean first) {
        this.first = first;
    }
    
    public boolean isLast() {
        return last;
    }
    
    public void setLast(boolean last) {
        this.last = last;
    }
    
    // 유틸리티 메소드
    public boolean hasNext() {
        return !last;
    }
    
    public boolean hasPrevious() {
        return !first;
    }
    
    public int getNextPage() {
        return hasNext() ? page + 1 : page;
    }
    
    public int getPreviousPage() {
        return hasPrevious() ? page - 1 : page;
    }
}