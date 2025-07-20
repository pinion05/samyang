<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${title}</title>
    <style>
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            max-width: 800px;
            margin: 0 auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .container {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            text-align: center;
            margin-bottom: 30px;
        }
        .message {
            background-color: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border: 1px solid #c3e6cb;
        }
        .info {
            background-color: #e7f3ff;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            border-left: 4px solid #007bff;
        }
        .nav-links {
            text-align: center;
            margin-top: 30px;
        }
        .nav-links a {
            display: inline-block;
            margin: 0 10px;
            padding: 10px 20px;
            background-color: #007bff;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background-color 0.3s;
        }
        .nav-links a:hover {
            background-color: #0056b3;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>${title}</h1>
        
        <div class="message">
            <strong>성공!</strong> ${message}
        </div>
        
        <div class="info">
            <h3>현재 시간</h3>
            <p><fmt:formatDate value="${currentTime}" pattern="yyyy년 MM월 dd일 HH:mm:ss" /></p>
        </div>
        
        <div class="info">
            <h3>JSP 기능 확인</h3>
            <ul>
                <li>✅ JSP 페이지 렌더링</li>
                <li>✅ JSTL 태그 라이브러리</li>
                <li>✅ 모델 데이터 바인딩</li>
                <li>✅ 날짜 포맷팅</li>
                <li>✅ CSS 스타일링</li>
            </ul>
        </div>
        
        <div class="nav-links">
            <a href="/jsp/user">사용자 페이지</a>
            <a href="/api/info">API 정보</a>
            <a href="/">홈으로</a>
        </div>
    </div>
</body>
</html>