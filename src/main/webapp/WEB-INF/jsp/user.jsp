<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>사용자 정보 - Samyang</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f8f9fa;
        }
        .user-card {
            background-color: white;
            padding: 30px;
            border-radius: 15px;
            box-shadow: 0 4px 15px rgba(0,0,0,0.1);
            text-align: center;
        }
        .user-avatar {
            width: 100px;
            height: 100px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 50%;
            margin: 0 auto 20px;
            display: flex;
            align-items: center;
            justify-content: center;
            color: white;
            font-size: 36px;
            font-weight: bold;
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .user-info {
            text-align: left;
            margin: 20px 0;
        }
        .info-item {
            display: flex;
            justify-content: space-between;
            padding: 12px 0;
            border-bottom: 1px solid #eee;
        }
        .info-item:last-child {
            border-bottom: none;
        }
        .info-label {
            font-weight: bold;
            color: #666;
        }
        .info-value {
            color: #333;
        }
        .back-button {
            display: inline-block;
            margin-top: 30px;
            padding: 12px 24px;
            background-color: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            transition: background-color 0.3s;
        }
        .back-button:hover {
            background-color: #218838;
        }
        .age-badge {
            display: inline-block;
            padding: 4px 12px;
            background-color: #17a2b8;
            color: white;
            border-radius: 20px;
            font-size: 14px;
        }
    </style>
</head>
<body>
    <div class="user-card">
        <div class="user-avatar">
            ${userName.substring(0,1)}
        </div>
        
        <h1>사용자 정보</h1>
        
        <div class="user-info">
            <div class="info-item">
                <span class="info-label">이름:</span>
                <span class="info-value">${userName}</span>
            </div>
            
            <div class="info-item">
                <span class="info-label">이메일:</span>
                <span class="info-value">${userEmail}</span>
            </div>
            
            <div class="info-item">
                <span class="info-label">나이:</span>
                <span class="info-value">
                    <span class="age-badge">${userAge}세</span>
                </span>
            </div>
            
            <div class="info-item">
                <span class="info-label">상태:</span>
                <span class="info-value" style="color: #28a745;">✅ 활성</span>
            </div>
        </div>
        
        <a href="/jsp/home" class="back-button">홈으로 돌아가기</a>
    </div>
</body>
</html>