<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>작물 상세정보 - Samyang 농업 플랫폼</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 800px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .detail-section {
            margin-bottom: 30px;
        }
        .detail-item {
            display: flex;
            margin-bottom: 15px;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        .detail-label {
            font-weight: bold;
            width: 150px;
            color: #666;
        }
        .detail-value {
            color: #333;
        }
        .btn-group {
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-right: 10px;
            text-decoration: none;
            border-radius: 5px;
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
            background-color: #666;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #555;
        }
        .btn-danger {
            background-color: #f44336;
            color: white;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
        .status-badge {
            padding: 6px 12px;
            border-radius: 4px;
            font-size: 14px;
            font-weight: bold;
            display: inline-block;
        }
        .status-growing {
            background-color: #4CAF50;
            color: white;
        }
        .status-harvested {
            background-color: #2196F3;
            color: white;
        }
        .status-planning {
            background-color: #ff9800;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🌱 작물 상세정보</h1>
        
        <div class="detail-section">
            <div class="detail-item">
                <div class="detail-label">작물 ID</div>
                <div class="detail-value">${crop.cropId}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">작물명</div>
                <div class="detail-value">${crop.cropName}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">품종</div>
                <div class="detail-value">${crop.variety != null ? crop.variety : '-'}</div>
            </div>
            <div class="detail-item">
                <div class="detail-label">식재일</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${crop.plantedDate != null}">
                            <fmt:formatDate value="${crop.plantedDate}" pattern="yyyy-MM-dd"/>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-item">
                <div class="detail-label">예상 수확일</div>
                <div class="detail-value">
                    <c:choose>
                        <c:when test="${crop.expectedHarvestDate != null}">
                            <fmt:formatDate value="${crop.expectedHarvestDate}" pattern="yyyy-MM-dd"/>
                        </c:when>
                        <c:otherwise>-</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="detail-item">
                <div class="detail-label">상태</div>
                <div class="detail-value">
                    <span class="status-badge status-${crop.status}">
                        <c:choose>
                            <c:when test="${crop.status == 'planning'}">계획중</c:when>
                            <c:when test="${crop.status == 'growing'}">재배중</c:when>
                            <c:when test="${crop.status == 'harvested'}">수확완료</c:when>
                            <c:otherwise>${crop.status}</c:otherwise>
                        </c:choose>
                    </span>
                </div>
            </div>
            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                <div class="detail-item">
                    <div class="detail-label">소유자</div>
                    <div class="detail-value">${crop.userName}</div>
                </div>
            </c:if>
        </div>
        
        <div class="btn-group">
            <a href="/crop/form?cropId=${crop.cropId}" class="btn btn-primary">수정</a>
            <form action="/crop/delete/${crop.cropId}" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                <button type="submit" class="btn btn-danger">삭제</button>
            </form>
            <a href="/crop/list" class="btn btn-secondary">목록으로</a>
        </div>
    </div>
</body>
</html>