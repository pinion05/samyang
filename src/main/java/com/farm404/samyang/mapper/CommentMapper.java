package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.CommentDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CommentMapper {
    // 댓글 등록
    int insertComment(CommentDTO comment);
    
    // 댓글 조회
    CommentDTO selectCommentById(Integer commentId);
    
    // 특정 컨텐츠의 댓글 목록 조회
    List<CommentDTO> selectCommentsByRelated(@Param("relatedId") Integer relatedId, 
                                            @Param("relatedType") String relatedType);
    
    // 사용자의 댓글 목록 조회
    List<CommentDTO> selectCommentsByUserId(Integer userId);
    
    // 모든 댓글 조회 (관리자용)
    List<CommentDTO> selectAllComments();
    
    // 댓글 수정
    int updateComment(CommentDTO comment);
    
    // 댓글 삭제
    int deleteComment(Integer commentId);
    
    // 특정 컨텐츠의 댓글 수 조회
    int countCommentsByRelated(@Param("relatedId") Integer relatedId, 
                              @Param("relatedType") String relatedType);
    
    // 최근 댓글 조회
    List<CommentDTO> selectRecentComments(@Param("limit") int limit);
}