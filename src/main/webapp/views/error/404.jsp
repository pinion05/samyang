<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>페이지를 찾을 수 없습니다 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f8f9fa;
        }
        
        .error-page {
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 20px;
        }
        
        .error-container {
            text-align: center;
            max-width: 600px;
            background: white;
            padding: 60px 40px;
            border-radius: 20px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
        }
        
        .error-icon {
            font-size: 120px;
            color: #e9ecef;
            margin-bottom: 30px;
            position: relative;
        }
        
        .error-icon i {
            position: relative;
            z-index: 1;
        }
        
        .error-code {
            font-size: 120px;
            font-weight: bold;
            color: #4CAF50;
            margin: 0;
            line-height: 1;
            letter-spacing: -5px;
        }
        
        .error-title {
            font-size: 28px;
            color: #333;
            margin: 20px 0;
            font-weight: 600;
        }
        
        .error-message {
            font-size: 18px;
            color: #666;
            margin-bottom: 40px;
            line-height: 1.6;
        }
        
        .error-actions {
            display: flex;
            gap: 15px;
            justify-content: center;
            flex-wrap: wrap;
        }
        
        .btn {
            padding: 12px 30px;
            text-decoration: none;
            border-radius: 8px;
            font-weight: 600;
            font-size: 16px;
            transition: all 0.3s;
            display: inline-flex;
            align-items: center;
            gap: 8px;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(76, 175, 80, 0.3);
        }
        
        .btn-secondary {
            background-color: #f8f9fa;
            color: #333;
            border: 2px solid #e9ecef;
        }
        
        .btn-secondary:hover {
            background-color: #e9ecef;
            transform: translateY(-2px);
        }
        
        .search-box {
            margin: 40px 0;
            max-width: 400px;
            margin-left: auto;
            margin-right: auto;
        }
        
        .search-form {
            display: flex;
            gap: 10px;
        }
        
        .search-input {
            flex: 1;
            padding: 12px 20px;
            border: 2px solid #e9ecef;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .search-input:focus {
            outline: none;
            border-color: #4CAF50;
        }
        
        .search-btn {
            padding: 12px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            cursor: pointer;
            transition: all 0.3s;
        }
        
        .search-btn:hover {
            background-color: #45a049;
        }
        
        .helpful-links {
            margin-top: 60px;
            padding-top: 40px;
            border-top: 1px solid #e9ecef;
        }
        
        .helpful-links h3 {
            color: #666;
            font-size: 18px;
            margin-bottom: 20px;
        }
        
        .link-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 15px;
            text-align: left;
        }
        
        .link-item {
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 8px;
            text-decoration: none;
            color: #333;
            transition: all 0.3s;
            display: flex;
            align-items: center;
            gap: 10px;
        }
        
        .link-item:hover {
            background-color: #e8f5e9;
            transform: translateX(5px);
        }
        
        .link-item i {
            color: #4CAF50;
            font-size: 20px;
        }
        
        @media (max-width: 768px) {
            .error-container {
                padding: 40px 20px;
            }
            
            .error-code {
                font-size: 80px;
            }
            
            .error-icon {
                font-size: 80px;
            }
            
            .error-title {
                font-size: 24px;
            }
            
            .error-message {
                font-size: 16px;
            }
            
            .error-actions {
                flex-direction: column;
                width: 100%;
            }
            
            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <div class="error-page">
        <div class="error-container">
            <div class="error-icon">
                <i class="fas fa-map-marked-alt"></i>
            </div>
            <h1 class="error-code">404</h1>
            <h2 class="error-title">페이지를 찾을 수 없습니다</h2>
            <p class="error-message">
                요청하신 페이지가 존재하지 않거나 이동되었을 수 있습니다.<br>
                주소를 다시 확인해 주시거나 아래 버튼을 이용해 주세요.
            </p>
            
            <div class="search-box">
                <form class="search-form" action="${pageContext.request.contextPath}/search" method="get">
                    <input type="text" 
                           class="search-input" 
                           name="q" 
                           placeholder="검색어를 입력하세요"
                           required>
                    <button type="submit" class="search-btn">
                        <i class="fas fa-search"></i>
                    </button>
                </form>
            </div>
            
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="fas fa-home"></i>
                    홈으로 가기
                </a>
                <a href="javascript:history.back();" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i>
                    이전 페이지
                </a>
            </div>
            
            <div class="helpful-links">
                <h3>자주 찾는 페이지</h3>
                <div class="link-grid">
                    <a href="${pageContext.request.contextPath}/crop/list" class="link-item">
                        <i class="fas fa-leaf"></i>
                        <span>작물관리</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/diary/list" class="link-item">
                        <i class="fas fa-book"></i>
                        <span>농업일지</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/review/list" class="link-item">
                        <i class="fas fa-star"></i>
                        <span>리뷰</span>
                    </a>
                    <a href="${pageContext.request.contextPath}/user/login" class="link-item">
                        <i class="fas fa-sign-in-alt"></i>
                        <span>로그인</span>
                    </a>
                </div>
            </div>
        </div>
    </div>
</body>
</html>