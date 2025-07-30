<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>내 프로필 - Samyang 농업 플랫폼</title>
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
        .profile-section {
            margin-bottom: 30px;
        }
        .profile-item {
            display: flex;
            margin-bottom: 15px;
            padding: 10px;
            border-bottom: 1px solid #eee;
        }
        .profile-label {
            font-weight: bold;
            width: 150px;
            color: #666;
        }
        .profile-value {
            color: #333;
        }
        .btn-group {
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-right: 10px;
            background-color: #4CAF50;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
        }
        .btn:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #666;
        }
        .btn-secondary:hover {
            background-color: #555;
        }
        .btn-danger {
            background-color: #f44336;
        }
        .btn-danger:hover {
            background-color: #da190b;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>내 프로필</h1>
        
        <div class="profile-section">
            <div class="profile-item">
                <div class="profile-label">사용자 ID</div>
                <div class="profile-value">${user.id}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">사용자명</div>
                <div class="profile-value">${user.username}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">이름</div>
                <div class="profile-value">${user.name}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">이메일</div>
                <div class="profile-value">${user.email}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">전화번호</div>
                <div class="profile-value">${user.phone}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">주소</div>
                <div class="profile-value">${user.address}</div>
            </div>
            <div class="profile-item">
                <div class="profile-label">권한</div>
                <div class="profile-value">
                    <c:choose>
                        <c:when test="${user.role == 'ADMIN'}">관리자</c:when>
                        <c:otherwise>일반 사용자</c:otherwise>
                    </c:choose>
                </div>
            </div>
            <div class="profile-item">
                <div class="profile-label">가입일</div>
                <div class="profile-value">${user.createdAt}</div>
            </div>
        </div>
        
        <div class="btn-group">
            <a href="/user/edit/${user.id}" class="btn">프로필 수정</a>
            <a href="/user/password" class="btn btn-secondary">비밀번호 변경</a>
            <a href="/" class="btn btn-secondary">메인으로</a>
            <c:if test="${user.role == 'ADMIN'}">
                <a href="/admin/users" class="btn btn-secondary">사용자 관리</a>
            </c:if>
        </div>
    </div>
</body>
</html>