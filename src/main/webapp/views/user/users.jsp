<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>사용자 관리 - Samyang 농업 플랫폼</title>
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
        .search-box input {
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
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
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
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
        .btn-new {
            background-color: #4CAF50;
            color: white;
            padding: 10px 20px;
            margin-bottom: 20px;
            display: inline-block;
        }
        .btn-new:hover {
            background-color: #45a049;
        }
        .role-badge {
            padding: 4px 8px;
            border-radius: 4px;
            font-size: 12px;
            font-weight: bold;
        }
        .role-admin {
            background-color: #ff5722;
            color: white;
        }
        .role-user {
            background-color: #2196F3;
            color: white;
        }
        .pagination {
            margin-top: 20px;
            text-align: center;
        }
        .pagination a {
            padding: 8px 12px;
            margin: 0 4px;
            text-decoration: none;
            background-color: #f1f1f1;
            color: #333;
            border-radius: 4px;
        }
        .pagination a:hover {
            background-color: #ddd;
        }
        .pagination .active {
            background-color: #4CAF50;
            color: white;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>사용자 관리</h1>
        
        <a href="/user/register" class="btn btn-new">새 사용자 추가</a>
        
        <div class="search-box">
            <form action="/admin/users" method="get">
                <input type="text" name="keyword" placeholder="이름, 사용자명, 이메일로 검색" value="${param.keyword}">
                <select name="role">
                    <option value="">전체 권한</option>
                    <option value="USER" ${param.role == 'USER' ? 'selected' : ''}>일반 사용자</option>
                    <option value="ADMIN" ${param.role == 'ADMIN' ? 'selected' : ''}>관리자</option>
                </select>
                <button type="submit">검색</button>
                <a href="/admin/users" class="btn">초기화</a>
            </form>
        </div>
        
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>사용자명</th>
                    <th>이름</th>
                    <th>이메일</th>
                    <th>전화번호</th>
                    <th>권한</th>
                    <th>가입일</th>
                    <th>작업</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.id}</td>
                        <td>${user.username}</td>
                        <td>${user.name}</td>
                        <td>${user.email}</td>
                        <td>${user.phone}</td>
                        <td>
                            <span class="role-badge ${user.role == 'ADMIN' ? 'role-admin' : 'role-user'}">
                                ${user.role == 'ADMIN' ? '관리자' : '일반 사용자'}
                            </span>
                        </td>
                        <td><fmt:formatDate value="${user.createdAt}" pattern="yyyy-MM-dd"/></td>
                        <td class="actions">
                            <a href="/user/detail/${user.id}" class="btn btn-view">보기</a>
                            <a href="/user/edit/${user.id}" class="btn btn-edit">수정</a>
                            <form action="/user/delete/${user.id}" method="post" style="display:inline;" onsubmit="return confirm('정말 삭제하시겠습니까?');">
                                <button type="submit" class="btn btn-delete">삭제</button>
                            </form>
                        </td>
                    </tr>
                </c:forEach>
                <c:if test="${empty userList}">
                    <tr>
                        <td colspan="8" style="text-align: center; padding: 40px;">사용자가 없습니다.</td>
                    </tr>
                </c:if>
            </tbody>
        </table>
        
        <div style="margin-top: 20px; text-align: right;">
            총 ${totalCount}명의 사용자
        </div>
        
        <div class="pagination">
            <c:if test="${currentPage > 1}">
                <a href="?page=${currentPage - 1}&keyword=${param.keyword}&role=${param.role}">이전</a>
            </c:if>
            
            <c:forEach begin="1" end="${totalPages}" var="page">
                <a href="?page=${page}&keyword=${param.keyword}&role=${param.role}" 
                   class="${page == currentPage ? 'active' : ''}">${page}</a>
            </c:forEach>
            
            <c:if test="${currentPage < totalPages}">
                <a href="?page=${currentPage + 1}&keyword=${param.keyword}&role=${param.role}">다음</a>
            </c:if>
        </div>
        
        <div style="margin-top: 30px;">
            <a href="/" class="btn btn-secondary">메인으로</a>
        </div>
    </div>
</body>
</html>