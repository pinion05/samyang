<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>상품 리뷰 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .review-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .review-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .review-header h1 {
            color: #2c3e50;
            margin: 0 0 10px 0;
        }
        
        .product-info {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .product-info h2 {
            color: #333;
            margin: 0 0 10px 0;
            font-size: 24px;
        }
        
        .product-meta {
            display: flex;
            gap: 20px;
            color: #666;
            font-size: 14px;
        }
        
        .review-statistics {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 30px;
        }
        
        .stats-header {
            display: flex;
            align-items: center;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        
        .average-rating {
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .rating-number {
            font-size: 48px;
            font-weight: bold;
            color: #333;
        }
        
        .rating-stars {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .stars {
            color: #ffc107;
            font-size: 24px;
        }
        
        .review-count {
            color: #666;
            font-size: 14px;
        }
        
        .rating-distribution {
            margin-top: 20px;
        }
        
        .rating-bar {
            display: flex;
            align-items: center;
            margin-bottom: 10px;
            gap: 10px;
        }
        
        .rating-label {
            width: 60px;
            font-size: 14px;
            color: #666;
        }
        
        .bar-container {
            flex: 1;
            height: 20px;
            background-color: #e9ecef;
            border-radius: 10px;
            overflow: hidden;
        }
        
        .bar-fill {
            height: 100%;
            background-color: #4CAF50;
            transition: width 0.3s ease;
        }
        
        .rating-percentage {
            width: 50px;
            text-align: right;
            font-size: 14px;
            color: #666;
        }
        
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .filter-buttons {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
        }
        
        .filter-btn {
            padding: 8px 16px;
            border: 1px solid #ddd;
            background-color: white;
            border-radius: 20px;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            color: #333;
        }
        
        .filter-btn:hover {
            background-color: #f8f9fa;
        }
        
        .filter-btn.active {
            background-color: #4CAF50;
            color: white;
            border-color: #4CAF50;
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
        
        .review-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        
        .review-card {
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .review-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
        }
        
        .review-header-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .reviewer-info {
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .reviewer-name {
            font-weight: 600;
            color: #333;
        }
        
        .review-date {
            color: #666;
            font-size: 14px;
        }
        
        .review-rating {
            color: #ffc107;
            font-size: 18px;
        }
        
        .review-content {
            color: #555;
            line-height: 1.6;
            margin-bottom: 15px;
        }
        
        .review-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .review-actions a {
            color: #666;
            text-decoration: none;
            font-size: 14px;
            padding: 5px 10px;
            border-radius: 4px;
            transition: all 0.3s;
        }
        
        .review-actions a:hover {
            background-color: #f8f9fa;
            color: #333;
        }
        
        .no-reviews {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .no-reviews i {
            font-size: 48px;
            color: #ddd;
            margin-bottom: 20px;
        }
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: 500;
        }
        
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        @media (max-width: 768px) {
            .stats-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 20px;
            }
            
            .rating-number {
                font-size: 36px;
            }
            
            .filter-buttons {
                justify-content: center;
            }
            
            .review-header-info {
                flex-direction: column;
                align-items: flex-start;
                gap: 10px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="review-container">
        <div class="review-header">
            <h1>상품 리뷰</h1>
        </div>
        
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                ${successMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty product}">
            <div class="product-info">
                <h2>${product.cropName}</h2>
                <div class="product-meta">
                    <span><i class="fas fa-seedling"></i> ${product.variety}</span>
                    <span><i class="fas fa-info-circle"></i> ${product.status}</span>
                </div>
            </div>
            
            <div class="review-statistics">
                <div class="stats-header">
                    <div class="average-rating">
                        <div class="rating-number">
                            <fmt:formatNumber value="${statistics.averageRating}" pattern="#.#"/>
                        </div>
                        <div class="rating-stars">
                            <div class="stars">
                                <c:forEach begin="1" end="5" var="i">
                                    <c:choose>
                                        <c:when test="${i <= statistics.averageRating}">★</c:when>
                                        <c:otherwise>☆</c:otherwise>
                                    </c:choose>
                                </c:forEach>
                            </div>
                            <div class="review-count">${statistics.totalReviews}개의 리뷰</div>
                        </div>
                    </div>
                    <a href="${pageContext.request.contextPath}/review/form?productId=${product.cropId}" class="btn btn-primary">
                        <i class="fas fa-pen"></i> 리뷰 작성
                    </a>
                </div>
                
                <div class="rating-distribution">
                    <c:forEach begin="5" end="1" step="-1" var="rating">
                        <div class="rating-bar">
                            <div class="rating-label">${rating}점</div>
                            <div class="bar-container">
                                <div class="bar-fill" style="width: ${statistics.ratingPercentage[rating]}%"></div>
                            </div>
                            <div class="rating-percentage">
                                <fmt:formatNumber value="${statistics.ratingPercentage[rating]}" pattern="#"/>%
                            </div>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="filter-section">
                <div class="filter-buttons">
                    <a href="${pageContext.request.contextPath}/review/list?productId=${product.cropId}" 
                       class="filter-btn ${empty selectedRating ? 'active' : ''}">
                        전체
                    </a>
                    <c:forEach begin="5" end="1" step="-1" var="rating">
                        <a href="${pageContext.request.contextPath}/review/list?productId=${product.cropId}&rating=${rating}" 
                           class="filter-btn ${selectedRating == rating ? 'active' : ''}">
                            ${rating}점
                        </a>
                    </c:forEach>
                </div>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty reviewList}">
                <div class="no-reviews">
                    <i class="fas fa-comment-slash"></i>
                    <h3>아직 작성된 리뷰가 없습니다</h3>
                    <p>첫 번째 리뷰를 작성해보세요!</p>
                    <c:if test="${not empty product}">
                        <a href="${pageContext.request.contextPath}/review/form?productId=${product.cropId}" class="btn btn-primary">
                            리뷰 작성하기
                        </a>
                    </c:if>
                </div>
            </c:when>
            <c:otherwise>
                <div class="review-list">
                    <c:forEach var="review" items="${reviewList}">
                        <div class="review-card">
                            <div class="review-header-info">
                                <div class="reviewer-info">
                                    <span class="reviewer-name">
                                        <i class="fas fa-user-circle"></i>
                                        <c:choose>
                                            <c:when test="${not empty review.userName}">
                                                ${review.userName}
                                            </c:when>
                                            <c:otherwise>
                                                사용자 ${review.userId}
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                    <span class="review-date">
                                        <fmt:formatDate value="${review.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                    </span>
                                </div>
                                <div class="review-rating">
                                    ${review.starRating}
                                </div>
                            </div>
                            
                            <c:if test="${empty product}">
                                <div style="margin-bottom: 10px; color: #666;">
                                    <strong>상품:</strong> ${review.productName}
                                </div>
                            </c:if>
                            
                            <div class="review-content">
                                ${review.comment}
                            </div>
                            
                            <c:if test="${sessionScope.user.id == review.userId || sessionScope.user.role == 'ADMIN'}">
                                <div class="review-actions">
                                    <a href="${pageContext.request.contextPath}/review/detail/${review.reviewId}">
                                        <i class="fas fa-eye"></i> 상세보기
                                    </a>
                                    <a href="${pageContext.request.contextPath}/review/form?reviewId=${review.reviewId}">
                                        <i class="fas fa-edit"></i> 수정
                                    </a>
                                </div>
                            </c:if>
                        </div>
                    </c:forEach>
                </div>
            </c:otherwise>
        </c:choose>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // 알림 메시지 자동 숨기기
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                alert.style.display = 'none';
            });
        }, 5000);
    </script>
</body>
</html>