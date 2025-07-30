<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>댓글 관리 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .comment-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .comment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .comment-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .comment-filters {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .filter-group {
            display: flex;
            gap: 15px;
            flex-wrap: wrap;
            align-items: center;
        }
        
        .filter-label {
            font-weight: 600;
            color: #333;
        }
        
        .filter-select {
            padding: 8px 15px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 14px;
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
        
        .comment-list {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }
        
        .comment-card {
            background-color: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .comment-card:hover {
            transform: translateY(-2px);
            box-shadow: 0 4px 20px rgba(0,0,0,0.15);
        }
        
        .comment-header-info {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 15px;
        }
        
        .comment-meta {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .commenter-name {
            font-weight: 600;
            color: #333;
            font-size: 16px;
        }
        
        .comment-related {
            display: flex;
            align-items: center;
            gap: 10px;
            color: #666;
            font-size: 14px;
        }
        
        .related-type-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: 600;
            background-color: #e9ecef;
        }
        
        .type-crop {
            background-color: #d4edda;
            color: #155724;
        }
        
        .type-diary {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .type-review {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .comment-date {
            color: #999;
            font-size: 14px;
        }
        
        .comment-content {
            color: #555;
            line-height: 1.6;
            margin-bottom: 15px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
        
        .comment-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .comment-actions a,
        .comment-actions button {
            padding: 6px 12px;
            font-size: 14px;
            border-radius: 4px;
            text-decoration: none;
            transition: all 0.3s;
        }
        
        .btn-edit {
            background-color: #007bff;
            color: white;
        }
        
        .btn-edit:hover {
            background-color: #0056b3;
        }
        
        .btn-delete {
            background-color: #dc3545;
            color: white;
            border: none;
            cursor: pointer;
        }
        
        .btn-delete:hover {
            background-color: #c82333;
        }
        
        .no-comments {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .no-comments i {
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
        
        .delete-form {
            display: inline;
        }
        
        .edited-mark {
            color: #999;
            font-size: 12px;
            font-style: italic;
        }
        
        @media (max-width: 768px) {
            .comment-header {
                flex-direction: column;
                gap: 15px;
            }
            
            .filter-group {
                flex-direction: column;
                align-items: stretch;
            }
            
            .comment-header-info {
                flex-direction: column;
                gap: 10px;
            }
            
            .comment-actions {
                justify-content: flex-start;
                flex-wrap: wrap;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="comment-container">
        <div class="comment-header">
            <h1>댓글 관리</h1>
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
        
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="comment-filters">
                <form action="${pageContext.request.contextPath}/comment/list" method="get" class="filter-group">
                    <label class="filter-label">관련 타입:</label>
                    <select name="relatedType" class="filter-select" onchange="this.form.submit()">
                        <option value="">전체</option>
                        <option value="CROP" ${param.relatedType == 'CROP' ? 'selected' : ''}>작물</option>
                        <option value="DIARY" ${param.relatedType == 'DIARY' ? 'selected' : ''}>농업일지</option>
                        <option value="REVIEW" ${param.relatedType == 'REVIEW' ? 'selected' : ''}>리뷰</option>
                    </select>
                </form>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty commentList}">
                <div class="no-comments">
                    <i class="fas fa-comments"></i>
                    <h3>작성된 댓글이 없습니다</h3>
                    <p>아직 댓글을 작성하지 않으셨네요.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="comment-list">
                    <c:forEach var="comment" items="${commentList}">
                        <div class="comment-card">
                            <div class="comment-header-info">
                                <div class="comment-meta">
                                    <div class="commenter-name">
                                        <i class="fas fa-user-circle"></i>
                                        <c:choose>
                                            <c:when test="${not empty comment.userName}">
                                                ${comment.userName}
                                            </c:when>
                                            <c:otherwise>
                                                사용자 ${comment.userId}
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="comment-related">
                                        <span class="related-type-badge type-${comment.relatedType.toLowerCase()}">
                                            ${comment.relatedTypeText}
                                        </span>
                                        <span>${comment.relatedTitle}</span>
                                    </div>
                                    <div class="comment-date">
                                        <fmt:formatDate value="${comment.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                        <c:if test="${comment.edited}">
                                            <span class="edited-mark">(수정됨)</span>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
                            
                            <div class="comment-content">
                                ${comment.content}
                            </div>
                            
                            <c:if test="${sessionScope.user.id == comment.userId || sessionScope.user.role == 'ADMIN'}">
                                <div class="comment-actions">
                                    <a href="${pageContext.request.contextPath}/comment/form?commentId=${comment.commentId}" 
                                       class="btn btn-edit">
                                        <i class="fas fa-edit"></i> 수정
                                    </a>
                                    <form action="${pageContext.request.contextPath}/comment/delete/${comment.commentId}" 
                                          method="post" class="delete-form" 
                                          onsubmit="return confirm('정말로 이 댓글을 삭제하시겠습니까?');">
                                        <button type="submit" class="btn btn-delete">
                                            <i class="fas fa-trash"></i> 삭제
                                        </button>
                                    </form>
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