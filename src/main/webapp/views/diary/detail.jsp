<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${diary.activityType} - 영농일지 상세 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .diary-detail-container {
            max-width: 900px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .diary-detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .diary-detail-header h1 {
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
        
        .diary-detail-content {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .diary-photo {
            width: 100%;
            max-height: 500px;
            object-fit: contain;
            background-color: #f8f9fa;
        }
        
        .no-photo {
            width: 100%;
            height: 300px;
            background-color: #e9ecef;
            display: flex;
            align-items: center;
            justify-content: center;
            color: #6c757d;
            font-size: 72px;
        }
        
        .diary-info {
            padding: 30px;
        }
        
        .diary-meta {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 8px;
        }
        
        .meta-item {
            display: flex;
            flex-direction: column;
        }
        
        .meta-label {
            font-size: 14px;
            color: #666;
            margin-bottom: 5px;
            font-weight: 600;
        }
        
        .meta-value {
            font-size: 16px;
            color: #333;
        }
        
        .activity-type-badge {
            display: inline-block;
            padding: 8px 16px;
            background-color: #e3f2fd;
            color: #1976d2;
            border-radius: 20px;
            font-weight: 600;
        }
        
        .diary-content-section {
            margin-top: 30px;
        }
        
        .content-title {
            font-size: 20px;
            color: #333;
            margin-bottom: 15px;
            font-weight: 600;
        }
        
        .content-text {
            font-size: 16px;
            line-height: 1.8;
            color: #555;
            white-space: pre-wrap;
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            min-height: 150px;
        }
        
        .diary-actions {
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
        
        .confirm-delete {
            background-color: #dc3545;
            color: white;
        }
        
        .confirm-delete:hover {
            background-color: #c82333;
        }
        
        @media (max-width: 768px) {
            .diary-detail-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
            
            .diary-meta {
                grid-template-columns: 1fr;
            }
            
            .diary-actions {
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
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="diary-detail-container">
        <div class="diary-detail-header">
            <h1>영농일지 상세</h1>
            <a href="${pageContext.request.contextPath}/diary/list" class="btn btn-secondary">
                <i class="fas fa-arrow-left"></i> 목록으로
            </a>
        </div>
        
        <div class="diary-detail-content">
            <c:choose>
                <c:when test="${not empty diary.photoUrl}">
                    <img src="${pageContext.request.contextPath}${diary.photoUrl}" 
                         alt="영농일지 사진" class="diary-photo">
                </c:when>
                <c:otherwise>
                    <div class="no-photo">
                        <i class="fas fa-image"></i>
                    </div>
                </c:otherwise>
            </c:choose>
            
            <div class="diary-info">
                <div class="diary-meta">
                    <div class="meta-item">
                        <span class="meta-label">작성일</span>
                        <span class="meta-value">
                            <i class="fas fa-calendar-alt"></i> ${diary.date}
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">활동 유형</span>
                        <span class="meta-value">
                            <span class="activity-type-badge">${diary.activityType}</span>
                        </span>
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">작성자</span>
                        <span class="meta-value">
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
                    </div>
                    <div class="meta-item">
                        <span class="meta-label">등록일시</span>
                        <span class="meta-value">
                            <i class="fas fa-clock"></i> ${diary.createdAt}
                        </span>
                    </div>
                </div>
                
                <div class="diary-content-section">
                    <h2 class="content-title">작업 내용</h2>
                    <div class="content-text">${diary.content}</div>
                </div>
                
                <c:if test="${sessionScope.user.id == diary.userId || sessionScope.user.role == 'ADMIN'}">
                    <div class="diary-actions">
                        <div class="action-buttons">
                            <a href="${pageContext.request.contextPath}/diary/form?diaryId=${diary.farmingDiaryId}" 
                               class="btn btn-primary">
                                <i class="fas fa-edit"></i> 수정
                            </a>
                            <form action="${pageContext.request.contextPath}/diary/delete/${diary.farmingDiaryId}" 
                                  method="post" class="delete-form" 
                                  onsubmit="return confirm('정말로 이 영농일지를 삭제하시겠습니까?');">
                                <button type="submit" class="btn btn-danger">
                                    <i class="fas fa-trash"></i> 삭제
                                </button>
                            </form>
                        </div>
                        <a href="${pageContext.request.contextPath}/diary/calendar?year=${diary.date.year}&month=${diary.date.monthValue}" 
                           class="btn btn-secondary">
                            <i class="fas fa-calendar"></i> 달력에서 보기
                        </a>
                    </div>
                </c:if>
            </div>
        </div>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>