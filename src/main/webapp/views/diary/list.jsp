<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>영농일지 목록 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .diary-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .diary-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .diary-header h1 {
            color: #2c3e50;
            margin: 0;
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
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
        }
        
        .btn-secondary {
            background-color: #2196F3;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #0b7dda;
        }
        
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .filter-form {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        
        .filter-form select,
        .filter-form button {
            padding: 8px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .filter-form button {
            background-color: #6c757d;
            color: white;
            cursor: pointer;
        }
        
        .filter-form button:hover {
            background-color: #5a6268;
        }
        
        .diary-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-top: 20px;
        }
        
        .diary-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .diary-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        
        .diary-card-image {
            width: 100%;
            height: 200px;
            object-fit: cover;
            background-color: #f0f0f0;
        }
        
        .diary-card-body {
            padding: 20px;
        }
        
        .diary-date {
            font-size: 14px;
            color: #666;
            margin-bottom: 10px;
        }
        
        .diary-type {
            display: inline-block;
            padding: 5px 10px;
            background-color: #e3f2fd;
            color: #1976d2;
            border-radius: 20px;
            font-size: 12px;
            margin-bottom: 10px;
        }
        
        .diary-content {
            color: #333;
            line-height: 1.6;
            margin-bottom: 15px;
            max-height: 60px;
            overflow: hidden;
            text-overflow: ellipsis;
            display: -webkit-box;
            -webkit-line-clamp: 3;
            -webkit-box-orient: vertical;
        }
        
        .diary-meta {
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 14px;
            color: #666;
        }
        
        .diary-actions {
            display: flex;
            gap: 10px;
        }
        
        .diary-actions a {
            color: #4CAF50;
            text-decoration: none;
            font-weight: 500;
        }
        
        .diary-actions a:hover {
            text-decoration: underline;
        }
        
        .no-diary {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .no-diary i {
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
        
        .no-image {
            width: 100%;
            height: 200px;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 48px;
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="diary-container">
        <div class="diary-header">
            <h1>영농일지</h1>
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/diary/form" class="btn btn-primary">
                    <i class="fas fa-plus"></i> 새 일지 작성
                </a>
                <a href="${pageContext.request.contextPath}/diary/calendar" class="btn btn-secondary">
                    <i class="fas fa-calendar"></i> 달력 보기
                </a>
            </div>
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
        
        <div class="filter-section">
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/diary/list">
                <label for="activityType">활동 유형:</label>
                <select name="activityType" id="activityType">
                    <option value="">전체</option>
                    <option value="파종" ${param.activityType == '파종' ? 'selected' : ''}>파종</option>
                    <option value="관수" ${param.activityType == '관수' ? 'selected' : ''}>관수</option>
                    <option value="시비" ${param.activityType == '시비' ? 'selected' : ''}>시비</option>
                    <option value="방제" ${param.activityType == '방제' ? 'selected' : ''}>방제</option>
                    <option value="수확" ${param.activityType == '수확' ? 'selected' : ''}>수확</option>
                    <option value="기타" ${param.activityType == '기타' ? 'selected' : ''}>기타</option>
                </select>
                <button type="submit">필터 적용</button>
                <c:if test="${not empty param.activityType}">
                    <a href="${pageContext.request.contextPath}/diary/list" class="btn btn-secondary">필터 초기화</a>
                </c:if>
            </form>
        </div>
        
        <c:choose>
            <c:when test="${empty diaryList}">
                <div class="no-diary">
                    <i class="fas fa-book-open"></i>
                    <h3>작성된 영농일지가 없습니다</h3>
                    <p>새로운 영농일지를 작성해보세요!</p>
                    <a href="${pageContext.request.contextPath}/diary/form" class="btn btn-primary">
                        일지 작성하기
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="diary-grid">
                    <c:forEach var="diary" items="${diaryList}">
                        <div class="diary-card">
                            <c:choose>
                                <c:when test="${not empty diary.photoUrl}">
                                    <img src="${pageContext.request.contextPath}${diary.photoUrl}" 
                                         alt="영농일지 사진" class="diary-card-image">
                                </c:when>
                                <c:otherwise>
                                    <div class="no-image">
                                        <i class="fas fa-image"></i>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                            
                            <div class="diary-card-body">
                                <div class="diary-date">
                                    <i class="fas fa-calendar-alt"></i> ${diary.date}
                                </div>
                                <span class="diary-type">${diary.activityType}</span>
                                <div class="diary-content">
                                    ${diary.content}
                                </div>
                                <div class="diary-meta">
                                    <span class="diary-author">
                                        <i class="fas fa-user"></i> 
                                        <c:choose>
                                            <c:when test="${not empty diary.userName}">
                                                ${diary.userName}
                                            </c:when>
                                            <c:otherwise>
                                                사용자 ${diary.userId}
                                            </c:otherwise>
                                        </c:choose>
                                    </span>
                                    <div class="diary-actions">
                                        <a href="${pageContext.request.contextPath}/diary/detail/${diary.farmingDiaryId}">
                                            상세보기
                                        </a>
                                        <c:if test="${sessionScope.user.id == diary.userId || sessionScope.user.role == 'ADMIN'}">
                                            <a href="${pageContext.request.contextPath}/diary/form?diaryId=${diary.farmingDiaryId}">
                                                수정
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </div>
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