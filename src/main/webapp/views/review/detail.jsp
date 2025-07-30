<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>리뷰 상세 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .review-detail-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .review-detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .review-detail-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .btn-group {
            display: flex;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .product-card {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .product-card h2 {
            color: #333;
            margin: 0 0 10px 0;
            font-size: 20px;
        }
        
        .product-info {
            display: flex;
            gap: 20px;
            color: #666;
            font-size: 14px;
        }
        
        .review-detail-content {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .review-info {
            padding: 30px;
        }
        
        .review-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 25px;
            padding-bottom: 20px;
            border-bottom: 1px solid #e9ecef;
        }
        
        .reviewer-info {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .reviewer-name {
            font-size: 18px;
            font-weight: 600;
            color: #333;
        }
        
        .review-dates {
            font-size: 14px;
            color: #666;
        }
        
        .review-rating-large {
            display: flex;
            flex-direction: column;
            align-items: center;
            gap: 5px;
        }
        
        .rating-stars-large {
            font-size: 32px;
            color: #ffc107;
        }
        
        .rating-text {
            font-size: 16px;
            color: #666;
            font-weight: 500;
        }
        
        .review-content-section {
            margin-top: 30px;
        }
        
        .content-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 15px;
            font-weight: 600;
        }
        
        .content-text {
            font-size: 16px;
            line-height: 1.8;
            color: #555;
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            white-space: pre-wrap;
        }
        
        .review-actions {
            margin-top: 40px;
            padding-top: 30px;
            border-top: 1px solid #e9ecef;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .action-buttons {
            display: flex;
            gap: 10px;
        }
        
        .delete-form {
            display: inline;
        }
        
        @media (max-width: 768px) {
            .review-detail-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
            
            .review-meta {
                flex-direction: column;
                gap: 20px;
            }
            
            .review-actions {
                flex-direction: column;
                gap: 15px;
            }
            
            .btn-group,
            .action-buttons {
                width: 100%;
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
                text-align: center;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="review-detail-container">
        <div class="review-detail-header">
            <h1>리뷰 상세</h1>
            <a href="${pageContext.request.contextPath}/review/list<c:if test="${not empty product}">?productId=${product.cropId}</c:if>" 
               class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> 목록으로
            </a>
        </div>
        
        <c:if test="${not empty product}">
            <div class="product-card">
                <h2>${product.cropName}</h2>
                <div class="product-info">
                    <span><i class="fas fa-seedling"></i> ${product.variety}</span>
                    <span><i class="fas fa-user"></i> ${product.userName}</span>
                    <span><i class="fas fa-info-circle"></i> ${product.status}</span>
                </div>
            </div>
        </c:if>
        
        <div class="review-detail-content">
            <div class="review-info">
                <div class="review-meta">
                    <div class="reviewer-info">
                        <div class="reviewer-name">
                            <i class="fas fa-user-circle"></i> 
                            <c:choose>
                                <c:when test="${not empty review.userName}">
                                    ${review.userName}
                                </c:when>
                                <c:otherwise>
                                    사용자 ${review.userId}
                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class="review-dates">
                            <div>
                                <i class="fas fa-calendar-alt"></i> 
                                작성일: <fmt:formatDate value="${review.createdAt}" pattern="yyyy년 MM월 dd일 HH:mm"/>
                            </div>
                            <c:if test="${review.createdAt != review.updatedAt}">
                                <div>
                                    <i class="fas fa-edit"></i> 
                                    수정일: <fmt:formatDate value="${review.updatedAt}" pattern="yyyy년 MM월 dd일 HH:mm"/>
                                </div>
                            </c:if>
                        </div>
                    </div>
                    
                    <div class="review-rating-large">
                        <div class="rating-stars-large">
                            ${review.starRating}
                        </div>
                        <div class="rating-text">
                            ${review.ratingText}
                        </div>
                    </div>
                </div>
                
                <div class="review-content-section">
                    <h3 class="content-title">리뷰 내용</h3>
                    <div class="content-text">${review.comment}</div>
                </div>
                
                <c:if test="${sessionScope.user.id == review.userId || sessionScope.user.role == 'ADMIN'}">
                    <div class="review-actions">
                        <div class="action-buttons">
                            <a href="${pageContext.request.contextPath}/review/form?reviewId=${review.reviewId}" 
                               class="btn btn-primary">
                                <i class="fas fa-edit"></i> 수정
                            </a>
                            <form action="${pageContext.request.contextPath}/review/delete/${review.reviewId}" 
                                  method="post" class="delete-form" 
                                  onsubmit="return confirm('정말로 이 리뷰를 삭제하시겠습니까?');">
                                <button type="submit" class="btn btn-danger">
                                    <i class="fas fa-trash"></i> 삭제
                                </button>
                            </form>
                        </div>
                        <c:if test="${not empty product}">
                            <a href="${pageContext.request.contextPath}/crop/detail/${product.cropId}" 
                               class="btn btn-secondary">
                                <i class="fas fa-seedling"></i> 상품 상세보기
                            </a>
                        </c:if>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>