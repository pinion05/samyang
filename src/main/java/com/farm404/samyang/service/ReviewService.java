package com.farm404.samyang.service;

import com.farm404.samyang.dto.ReviewDTO;
import com.farm404.samyang.mapper.ReviewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReviewService {
    
    @Autowired
    private ReviewMapper reviewMapper;
    
    public ReviewDTO getReviewById(Integer reviewId) {
        return reviewMapper.selectReviewById(reviewId);
    }
    
    public List<ReviewDTO> getReviewsByUserId(Integer userId) {
        return reviewMapper.selectReviewsByUserId(userId);
    }
    
    public List<ReviewDTO> getReviewsByProductId(Integer productId) {
        return reviewMapper.selectReviewsByProductId(productId);
    }
    
    public List<ReviewDTO> getAllReviews() {
        return reviewMapper.selectAllReviews();
    }
    
    public List<ReviewDTO> getReviewsByRating(Integer productId, Integer rating) {
        return reviewMapper.selectReviewsByRating(productId, rating);
    }
    
    public boolean registerReview(ReviewDTO review) {
        // 이미 리뷰를 작성했는지 확인
        if (hasUserReviewed(review.getUserId(), review.getProductId())) {
            return false;
        }
        return reviewMapper.insertReview(review) > 0;
    }
    
    public boolean updateReview(ReviewDTO review) {
        return reviewMapper.updateReview(review) > 0;
    }
    
    public boolean deleteReview(Integer reviewId) {
        return reviewMapper.deleteReview(reviewId) > 0;
    }
    
    public Double getAverageRating(Integer productId) {
        Double averageRating = reviewMapper.selectAverageRating(productId);
        return averageRating != null ? averageRating : 0.0;
    }
    
    public int getReviewCount(Integer productId) {
        return reviewMapper.selectReviewCount(productId);
    }
    
    public boolean hasUserReviewed(Integer userId, Integer productId) {
        return reviewMapper.checkUserReviewed(userId, productId) > 0;
    }
    
    // 상품의 리뷰 통계 정보 조회
    public Map<String, Object> getProductReviewStatistics(Integer productId) {
        Map<String, Object> statistics = new HashMap<>();
        
        // 평균 평점
        Double averageRating = getAverageRating(productId);
        statistics.put("averageRating", averageRating);
        
        // 총 리뷰 수
        int totalReviews = getReviewCount(productId);
        statistics.put("totalReviews", totalReviews);
        
        // 평점별 리뷰 수
        List<ReviewDTO> ratingStats = reviewMapper.selectRatingStatistics(productId);
        Map<Integer, Integer> ratingDistribution = new HashMap<>();
        
        // 1-5점 기본값 0으로 초기화
        for (int i = 1; i <= 5; i++) {
            ratingDistribution.put(i, 0);
        }
        
        // 실제 데이터로 업데이트
        for (ReviewDTO stat : ratingStats) {
            if (stat.getRating() != null) {
                ratingDistribution.put(stat.getRating(), Integer.parseInt(stat.getComment()));
            }
        }
        
        statistics.put("ratingDistribution", ratingDistribution);
        
        // 평점별 비율 계산
        Map<Integer, Double> ratingPercentage = new HashMap<>();
        if (totalReviews > 0) {
            for (Map.Entry<Integer, Integer> entry : ratingDistribution.entrySet()) {
                double percentage = (entry.getValue() * 100.0) / totalReviews;
                ratingPercentage.put(entry.getKey(), percentage);
            }
        }
        statistics.put("ratingPercentage", ratingPercentage);
        
        return statistics;
    }
    
    // 평점 유효성 검사
    public boolean isValidRating(Integer rating) {
        return rating != null && rating >= 1 && rating <= 5;
    }
}