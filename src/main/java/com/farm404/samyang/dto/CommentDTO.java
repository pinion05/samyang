package com.farm404.samyang.dto;

import java.util.Date;

public class CommentDTO {
    private Integer commentId;
    private Integer userId;
    // DB 스키마 필수 필드 추가 (Comment 테이블의 PostID 필드와 매핑)
    private Integer postId;
    
    // TODO: [임시필드] Post 테이블이 없어서 관련 컨텐츠 매핑용으로 사용
    // 추후 Post 테이블 생성 시 제거 예정
    private Integer relatedId;
    private String relatedType; // 'CROP', 'DIARY', 'REVIEW'
    private String content;
    // TODO: [이상적개선] Date -> LocalDateTime으로 통일
    private Date createdAt;
    private Date updatedAt;
    
    // 조인 필드
    private String userName;
    private String relatedTitle; // 관련 컨텐츠의 제목
    
    // 생성자
    public CommentDTO() {}
    
    // Getters and Setters
    public Integer getCommentId() {
        return commentId;
    }
    
    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public Integer getPostId() {
        return postId;
    }
    
    public void setPostId(Integer postId) {
        this.postId = postId;
    }
    
    public Integer getRelatedId() {
        return relatedId;
    }
    
    public void setRelatedId(Integer relatedId) {
        this.relatedId = relatedId;
    }
    
    public String getRelatedType() {
        return relatedType;
    }
    
    public void setRelatedType(String relatedType) {
        this.relatedType = relatedType;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public Date getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
    
    public Date getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getUserName() {
        return userName;
    }
    
    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    public String getRelatedTitle() {
        return relatedTitle;
    }
    
    public void setRelatedTitle(String relatedTitle) {
        this.relatedTitle = relatedTitle;
    }
    
    // 유틸리티 메서드
    public String getRelatedTypeText() {
        if (relatedType == null) return "";
        switch (relatedType) {
            case "CROP":
                return "작물";
            case "DIARY":
                return "농업일지";
            case "REVIEW":
                return "리뷰";
            default:
                return relatedType;
        }
    }
    
    public String getShortContent() {
        if (content == null) return "";
        if (content.length() <= 100) return content;
        return content.substring(0, 100) + "...";
    }
    
    public boolean isEdited() {
        return updatedAt != null && !updatedAt.equals(createdAt);
    }
}