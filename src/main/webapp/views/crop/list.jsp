<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>작물 목록 - Samyang 농업 플랫폼</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 1200px;
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
        .search-box {
            margin-bottom: 20px;
            display: flex;
            gap: 10px;
        }
        .search-box input, .search-box select {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        .search-box input {
            flex: 1;
        }
        .search-box button {
            padding: 8px 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
        }
        .search-box button:hover {
            background-color: #45a049;
        }
        .btn-new {
            display: inline-block;
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .btn-new:hover {
            background-color: #45a049;
        }
        table {
            width: 100%;
            border-collapse: collapse;
        }
        th, td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #4CAF50;
            color: white;
        }
        tr:hover {
            background-color: #f5f5f5;
        }
        .actions {
            display: flex;
            gap: 10px;
        }
        .btn {
            padding: 6px 12px;
            text-decoration: none;
            border-radius: 4px;
            border: none;
            cursor: pointer;
            font-size: 14px;
        }
        .btn-view {
            background-color: #2196F3;
            color: white;
        }
        .btn-view:hover {
            background-color: #0b7dda;
        }
        .btn-edit {
            background-color: #ff9800;
            color: white;
        }
        .btn-edit:hover {
            background-color: #e68900;
        }
        .btn-delete {
            background-color: #f44336;
            color: white;
        }
        .btn-delete:hover {
            background-color: #da190b;
        }
        .status-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
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
        .message {
            padding: 10px;
            margin-bottom: 20px;
            border-radius: 4px;
        }
        .success-message {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        .error-message {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🌾 작물 목록</h1>
        
        <c:if test="${not empty successMessage}">
            <div class="message success-message">${successMessage}</div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="message error-message">${errorMessage}</div>
        </c:if>
        
        <a href="/crop/form" class="btn-new">새 작물 등록</a>
        
        <div class="search-box">
            <form action="/crop/list" method="get">
                <input type="text" name="keyword" placeholder="작물명 또는 품종으로 검색" value="${param.keyword}">
                <select name="status">
                    <option value="">전체 상태</option>
                    <option value="planning" ${param.status == 'planning' ? 'selected' : ''}>계획중</option>
                    <option value="growing" ${param.status == 'growing' ? 'selected' : ''}>재배중</option>
                    <option value="harvested" ${param.status == 'harvested' ? 'selected' : ''}>수확완료</option>
                </select>
                <button type="submit">검색</button>
                <a href="/crop/list" class="btn">초기화</a>
            </form>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>작물명</th>
                    <th>품종</th>
                    <th>식재일</th>
                    <th>예상 수확일</th>
                    <th>상태</th>
                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <th>소유자</th>
                    </c:if>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${cropList}" var="crop">
                    <tr>
                        <td>${crop.cropId}</td>
                        <td>${crop.cropName}</td>
                        <td>${crop.variety}</td>
                        <td><fmt:formatDate value="${crop.plantedDate}" pattern="yyyy-MM-dd"/></td>
                        <td><fmt:formatDate value="${crop.expectedHarvestDate}" pattern="yyyy-MM-dd"/></td>
                        <td>
                            <span class="status-badge status-${crop.status}">
                                <c:choose>
                                    <c:when test="${crop.status == 'planning'}">계획중</c:when>
                                    <c:when test="${crop.status == 'growing'}">재배중</c:when>
                                    <c:when test="${crop.status == 'harvested'}">수확완료</c:when>
                                    <c:otherwise>${crop.status}</c:otherwise>
                                </c:choose>
                            </span>
                        </td>
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <td>${crop.userName}</td>
                        </c:if>
                        <td class="actions">
                            <a href="/crop/detail/${crop.cropId}" class="btn btn-view">보기</a>
                            <a href="/crop/form?cropId=${crop.cropId}" class="btn btn-edit">수정</a>
                            <form action="/crop/delete/${crop.cropId}" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <button type="submit" class="btn btn-delete">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty cropList}">
                    <tr>
                        <td colspan="${sessionScope.user.role == 'ADMIN' ? '8' : '7'}" style="text-align: center; padding: 40px;">
                            등록된 작물이 없습니다.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <div style="margin-top: 30px;">
            <a href="/" class="btn" style="background-color: #666; color: white;">메인으로</a>
        </div>
    </div>
</body>
</html>