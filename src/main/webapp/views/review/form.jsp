<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? '리뷰 수정' : '리뷰 작성'} - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .review-form-container {
            max-width: 700px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .form-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .form-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .form-description {
            color: #666;
            margin-top: 10px;
        }
        
        .review-form {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-label {
            display: block;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        
        .required {
            color: #dc3545;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 0.2rem rgba(76, 175, 80, 0.25);
        }
        
        select.form-control {
            height: 48px;
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 150px;
        }
        
        .product-select-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .product-select-info h3 {
            margin: 0 0 10px 0;
            font-size: 18px;
            color: #333;
        }
        
        .product-select-info p {
            margin: 0;
            color: #666;
            font-size: 14px;
        }
        
        .rating-selector {
            display: flex;
            gap: 10px;
            margin-top: 10px;
        }
        
        .rating-option {
            position: relative;
        }
        
        .rating-option input[type="radio"] {
            position: absolute;
            opacity: 0;
        }
        
        .rating-label {
            display: flex;
            flex-direction: column;
            align-items: center;
            padding: 15px 20px;
            border: 2px solid #ddd;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
            background-color: white;
        }
        
        .rating-label:hover {
            border-color: #4CAF50;
            background-color: #f8f9fa;
        }
        
        .rating-option input[type="radio"]:checked + .rating-label {
            border-color: #4CAF50;
            background-color: #e8f5e9;
        }
        
        .rating-stars {
            font-size: 24px;
            color: #ffc107;
            margin-bottom: 5px;
        }
        
        .rating-text {
            font-size: 14px;
            color: #666;
        }
        
        .form-actions {
            margin-top: 40px;
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            text-align: center;
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
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: 500;
        }
        
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .help-text {
            font-size: 14px;
            color: #6c757d;
            margin-top: 5px;
        }
        
        .char-counter {
            text-align: right;
            font-size: 14px;
            color: #6c757d;
            margin-top: 5px;
        }
        
        @media (max-width: 768px) {
            .review-form-container {
                padding: 15px;
            }
            
            .review-form {
                padding: 20px;
            }
            
            .rating-selector {
                flex-wrap: wrap;
            }
            
            .rating-option {
                flex: 1 1 calc(50% - 5px);
                min-width: 120px;
            }
            
            .form-actions {
                flex-direction: column-reverse;
            }
            
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="review-form-container">
        <div class="form-header">
            <h1>${isEdit ? '리뷰 수정' : '리뷰 작성'}</h1>
            <p class="form-description">
                ${isEdit ? '작성하신 리뷰를 수정합니다.' : '솔직한 리뷰를 남겨주세요.'}
            </p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <form class="review-form" action="${pageContext.request.contextPath}/review/save" method="post">
            <c:if test="${isEdit}">
                <input type="hidden" name="reviewId" value="${review.reviewId}">
                <input type="hidden" name="productId" value="${review.productId}">
            </c:if>
            
            <c:choose>
                <c:when test="${not empty product}">
                    <div class="product-select-info">
                        <h3>${product.cropName}</h3>
                        <p>${product.variety} | ${product.userName}</p>
                    </div>
                    <input type="hidden" name="productId" value="${product.cropId}">
                </c:when>
                <c:when test="${not isEdit}">
                    <div class="form-group">
                        <label class="form-label" for="productId">
                            상품 선택 <span class="required">*</span>
                        </label>
                        <select class="form-control" id="productId" name="productId" required>
                            <option value="">상품을 선택하세요</option>
                            <c:forEach var="crop" items="${cropList}">
                                <option value="${crop.cropId}" ${review.productId == crop.cropId ? 'selected' : ''}>
                                    ${crop.cropName} - ${crop.variety} (${crop.userName})
                                </option>
                            </c:forEach>
                        </select>
                        <p class="help-text">리뷰를 작성할 상품을 선택해주세요.</p>
                    </div>
                </c:when>
            </c:choose>
            
            <div class="form-group">
                <label class="form-label">
                    평점 <span class="required">*</span>
                </label>
                <div class="rating-selector">
                    <c:forEach begin="1" end="5" var="rating">
                        <div class="rating-option">
                            <input type="radio" 
                                   id="rating${rating}" 
                                   name="rating" 
                                   value="${rating}" 
                                   ${review.rating == rating ? 'checked' : ''}
                                   required>
                            <label for="rating${rating}" class="rating-label">
                                <div class="rating-stars">
                                    <c:forEach begin="1" end="5" var="star">
                                        <c:choose>
                                            <c:when test="${star <= rating}">★</c:when>
                                            <c:otherwise>☆</c:otherwise>
                                        </c:choose>
                                    </c:forEach>
                                </div>
                                <div class="rating-text">
                                    <c:choose>
                                        <c:when test="${rating == 1}">매우 나쁨</c:when>
                                        <c:when test="${rating == 2}">나쁨</c:when>
                                        <c:when test="${rating == 3}">보통</c:when>
                                        <c:when test="${rating == 4}">좋음</c:when>
                                        <c:when test="${rating == 5}">매우 좋음</c:when>
                                    </c:choose>
                                </div>
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="comment">
                    리뷰 내용 <span class="required">*</span>
                </label>
                <textarea class="form-control" 
                          id="comment" 
                          name="comment" 
                          placeholder="상품에 대한 솔직한 후기를 작성해주세요." 
                          maxlength="1000"
                          onkeyup="updateCharCount(this)"
                          required>${review.comment}</textarea>
                <div class="char-counter">
                    <span id="charCount">0</span> / 1000
                </div>
                <p class="help-text">
                    다른 고객님들에게 도움이 될 수 있도록 자세하고 솔직한 리뷰를 작성해주세요.
                </p>
            </div>
            
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/review/list<c:if test="${not empty product}">?productId=${product.cropId}</c:if>" 
                   class="btn btn-secondary">
                    취소
                </a>
                <button type="submit" class="btn btn-primary">
                    ${isEdit ? '수정하기' : '작성하기'}
                </button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // 문자수 카운터 업데이트
        function updateCharCount(textarea) {
            const charCount = textarea.value.length;
            document.getElementById('charCount').textContent = charCount;
        }
        
        // 페이지 로드 시 초기 문자수 표시
        window.onload = function() {
            const commentTextarea = document.getElementById('comment');
            if (commentTextarea) {
                updateCharCount(commentTextarea);
            }
        };
    </script>
</body>
</html>