package com.farm404.samyang.controller;

import com.farm404.samyang.dto.ReviewDTO;
import com.farm404.samyang.dto.UserDTO;
import com.farm404.samyang.dto.CropDTO;
import com.farm404.samyang.service.ReviewService;
import com.farm404.samyang.service.CropService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/review")
public class ReviewController {
    
    @Autowired
    private ReviewService reviewService;
    
    @Autowired
    private CropService cropService;
    
    @GetMapping("/list")
    public String reviewList(@RequestParam(required = false) Integer productId,
                            @RequestParam(required = false) Integer rating,
                            HttpSession session,
                            Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        List<ReviewDTO> reviewList;
        
        if (productId != null) {
            // 특정 상품의 리뷰 목록
            if (rating != null) {
                reviewList = reviewService.getReviewsByRating(productId, rating);
            } else {
                reviewList = reviewService.getReviewsByProductId(productId);
            }
            
            // 상품 정보 조회
            CropDTO product = cropService.getCropById(productId);
            model.addAttribute("product", product);
            
            // 리뷰 통계 정보
            Map<String, Object> statistics = reviewService.getProductReviewStatistics(productId);
            model.addAttribute("statistics", statistics);
        } else if ("ADMIN".equals(currentUser.getRole())) {
            // 관리자는 모든 리뷰 조회
            reviewList = reviewService.getAllReviews();
        } else {
            // 일반 사용자는 자신의 리뷰만 조회
            Integer userId = Integer.parseInt(currentUser.getId());
            reviewList = reviewService.getReviewsByUserId(userId);
        }
        
        model.addAttribute("reviewList", reviewList);
        model.addAttribute("selectedRating", rating);
        return "review/list";
    }
    
    @GetMapping("/detail/{reviewId}")
    public String reviewDetail(@PathVariable Integer reviewId,
                              HttpSession session,
                              Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        ReviewDTO review = reviewService.getReviewById(reviewId);
        if (review == null) {
            return "redirect:/review/list";
        }
        
        // 권한 체크
        Integer userId = Integer.parseInt(currentUser.getId());
        if (!review.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
            return "redirect:/review/list";
        }
        
        // 상품 정보 조회
        CropDTO product = cropService.getCropById(review.getProductId());
        
        model.addAttribute("review", review);
        model.addAttribute("product", product);
        return "review/detail";
    }
    
    @GetMapping("/form")
    public String reviewForm(@RequestParam(required = false) Integer reviewId,
                            @RequestParam(required = false) Integer productId,
                            HttpSession session,
                            Model model) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        ReviewDTO review;
        CropDTO product = null;
        
        if (reviewId != null) {
            // 수정 모드
            review = reviewService.getReviewById(reviewId);
            if (review == null) {
                return "redirect:/review/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!review.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                return "redirect:/review/list";
            }
            
            product = cropService.getCropById(review.getProductId());
        } else {
            // 등록 모드
            review = new ReviewDTO();
            review.setUserId(Integer.parseInt(currentUser.getId()));
            
            if (productId != null) {
                product = cropService.getCropById(productId);
                if (product == null) {
                    return "redirect:/crop/list";
                }
                review.setProductId(productId);
                
                // 이미 리뷰를 작성했는지 확인
                Integer userId = Integer.parseInt(currentUser.getId());
                if (reviewService.hasUserReviewed(userId, productId)) {
                    model.addAttribute("errorMessage", "이미 이 상품에 대한 리뷰를 작성하셨습니다.");
                    model.addAttribute("product", product);
                    return "review/list";
                }
            }
        }
        
        // 상품 목록 조회 (등록 시 상품 선택용)
        if (productId == null && reviewId == null) {
            List<CropDTO> cropList = cropService.getAllCrops();
            model.addAttribute("cropList", cropList);
        }
        
        model.addAttribute("review", review);
        model.addAttribute("product", product);
        model.addAttribute("isEdit", reviewId != null);
        return "review/form";
    }
    
    @PostMapping("/save")
    public String saveReview(@ModelAttribute ReviewDTO review,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            // 평점 유효성 검사
            if (!reviewService.isValidRating(review.getRating())) {
                redirectAttributes.addFlashAttribute("errorMessage", "평점은 1-5 사이의 값이어야 합니다.");
                return "redirect:/review/form";
            }
            
            boolean success;
            if (review.getReviewId() != null) {
                // 수정
                ReviewDTO existingReview = reviewService.getReviewById(review.getReviewId());
                if (existingReview == null) {
                    redirectAttributes.addFlashAttribute("errorMessage", "리뷰를 찾을 수 없습니다.");
                    return "redirect:/review/list";
                }
                
                // 권한 체크
                Integer userId = Integer.parseInt(currentUser.getId());
                if (!existingReview.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                    redirectAttributes.addFlashAttribute("errorMessage", "수정 권한이 없습니다.");
                    return "redirect:/review/list";
                }
                
                success = reviewService.updateReview(review);
            } else {
                // 등록
                review.setUserId(Integer.parseInt(currentUser.getId()));
                success = reviewService.registerReview(review);
            }
            
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", 
                    review.getReviewId() != null ? "리뷰가 수정되었습니다." : "리뷰가 등록되었습니다.");
                return "redirect:/review/list?productId=" + review.getProductId();
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "이미 이 상품에 대한 리뷰를 작성하셨습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/review/list";
    }
    
    @PostMapping("/delete/{reviewId}")
    public String deleteReview(@PathVariable Integer reviewId,
                              HttpSession session,
                              RedirectAttributes redirectAttributes) {
        UserDTO currentUser = (UserDTO) session.getAttribute("user");
        if (currentUser == null) {
            return "redirect:/user/login";
        }
        
        try {
            ReviewDTO review = reviewService.getReviewById(reviewId);
            if (review == null) {
                redirectAttributes.addFlashAttribute("errorMessage", "리뷰를 찾을 수 없습니다.");
                return "redirect:/review/list";
            }
            
            // 권한 체크
            Integer userId = Integer.parseInt(currentUser.getId());
            if (!review.getUserId().equals(userId) && !"ADMIN".equals(currentUser.getRole())) {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 권한이 없습니다.");
                return "redirect:/review/list";
            }
            
            boolean success = reviewService.deleteReview(reviewId);
            if (success) {
                redirectAttributes.addFlashAttribute("successMessage", "리뷰가 삭제되었습니다.");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "삭제 중 오류가 발생했습니다.");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "오류: " + e.getMessage());
        }
        
        return "redirect:/review/list";
    }
}