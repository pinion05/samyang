package com.farm404.samyang.service;

import com.farm404.samyang.dto.CommentDTO;
import com.farm404.samyang.mapper.CommentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class CommentService {
    
    @Autowired
    private CommentMapper commentMapper;
    
    // 댓글 등록
    public boolean registerComment(CommentDTO comment) {
        try {
            // 유효성 검사
            if (comment.getUserId() == null || comment.getRelatedId() == null || 
                comment.getRelatedType() == null || comment.getContent() == null) {
                return false;
            }
            
            // 관련 타입 검증
            if (!isValidRelatedType(comment.getRelatedType())) {
                return false;
            }
            
            // 댓글 내용 검증
            if (comment.getContent().trim().isEmpty() || comment.getContent().length() > 500) {
                return false;
            }
            
            return commentMapper.insertComment(comment) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 댓글 조회
    public CommentDTO getCommentById(Integer commentId) {
        if (commentId == null) {
            return null;
        }
        return commentMapper.selectCommentById(commentId);
    }
    
    // 특정 컨텐츠의 댓글 목록 조회
    public List<CommentDTO> getCommentsByRelated(Integer relatedId, String relatedType) {
        if (relatedId == null || relatedType == null) {
            return null;
        }
        return commentMapper.selectCommentsByRelated(relatedId, relatedType);
    }
    
    // 사용자의 댓글 목록 조회
    public List<CommentDTO> getCommentsByUserId(Integer userId) {
        if (userId == null) {
            return null;
        }
        return commentMapper.selectCommentsByUserId(userId);
    }
    
    // 모든 댓글 조회 (관리자용)
    public List<CommentDTO> getAllComments() {
        return commentMapper.selectAllComments();
    }
    
    // 댓글 수정
    public boolean updateComment(CommentDTO comment) {
        try {
            // 유효성 검사
            if (comment.getCommentId() == null || comment.getContent() == null) {
                return false;
            }
            
            // 댓글 존재 여부 확인
            CommentDTO existingComment = commentMapper.selectCommentById(comment.getCommentId());
            if (existingComment == null) {
                return false;
            }
            
            // 댓글 내용 검증
            if (comment.getContent().trim().isEmpty() || comment.getContent().length() > 500) {
                return false;
            }
            
            return commentMapper.updateComment(comment) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 댓글 삭제
    public boolean deleteComment(Integer commentId) {
        try {
            if (commentId == null) {
                return false;
            }
            
            // 댓글 존재 여부 확인
            CommentDTO existingComment = commentMapper.selectCommentById(commentId);
            if (existingComment == null) {
                return false;
            }
            
            return commentMapper.deleteComment(commentId) > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    // 특정 컨텐츠의 댓글 수 조회
    public int getCommentCount(Integer relatedId, String relatedType) {
        if (relatedId == null || relatedType == null) {
            return 0;
        }
        return commentMapper.countCommentsByRelated(relatedId, relatedType);
    }
    
    // 최근 댓글 조회
    public List<CommentDTO> getRecentComments(int limit) {
        if (limit <= 0) {
            limit = 10; // 기본값
        }
        return commentMapper.selectRecentComments(limit);
    }
    
    // 관련 타입 유효성 검사
    private boolean isValidRelatedType(String relatedType) {
        return "CROP".equals(relatedType) || 
               "DIARY".equals(relatedType) || 
               "REVIEW".equals(relatedType);
    }
    
    // 권한 확인 (댓글 작성자 또는 관리자)
    public boolean hasPermission(Integer commentId, Integer userId, String userRole) {
        if (commentId == null || userId == null) {
            return false;
        }
        
        // 관리자는 모든 권한 가짐
        if ("ADMIN".equals(userRole)) {
            return true;
        }
        
        // 댓글 작성자 확인
        CommentDTO comment = commentMapper.selectCommentById(commentId);
        return comment != null && comment.getUserId().equals(userId);
    }
}