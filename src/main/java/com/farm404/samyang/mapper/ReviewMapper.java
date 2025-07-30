package com.farm404.samyang.mapper;

import com.farm404.samyang.dto.ReviewDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ReviewMapper {
    
    // 리뷰 조회
    ReviewDTO selectReviewById(Integer reviewId);
    
    // 사용자별 리뷰 목록 조회
    List<ReviewDTO> selectReviewsByUserId(Integer userId);
    
    // 상품별 리뷰 목록 조회
    List<ReviewDTO> selectReviewsByProductId(Integer productId);
    
    // 모든 리뷰 조회
    List<ReviewDTO> selectAllReviews();
    
    // 평점별 리뷰 조회
    List<ReviewDTO> selectReviewsByRating(@Param("productId") Integer productId, 
                                         @Param("rating") Integer rating);
    
    // 리뷰 등록
    int insertReview(ReviewDTO review);
    
    // 리뷰 수정
    int updateReview(ReviewDTO review);
    
    // 리뷰 삭제
    int deleteReview(Integer reviewId);
    
    // 상품의 평균 평점 조회
    Double selectAverageRating(Integer productId);
    
    // 상품의 리뷰 개수 조회
    int selectReviewCount(Integer productId);
    
    // 사용자가 특정 상품에 리뷰를 작성했는지 확인
    int checkUserReviewed(@Param("userId") Integer userId, 
                         @Param("productId") Integer productId);
    
    // 평점별 리뷰 통계
    List<ReviewDTO> selectRatingStatistics(Integer productId);
}