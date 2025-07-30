<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>서버 오류 - 삼양농업플랫폼</title>
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
            color: #dc3545;
            margin-bottom: 30px;
            position: relative;
            animation: pulse 2s infinite;
        }
        
        @keyframes pulse {
            0% {
                transform: scale(1);
                opacity: 1;
            }
            50% {
                transform: scale(1.05);
                opacity: 0.8;
            }
            100% {
                transform: scale(1);
                opacity: 1;
            }
        }
        
        .error-code {
            font-size: 120px;
            font-weight: bold;
            color: #dc3545;
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
        
        .error-details {
            margin-top: 40px;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
            text-align: left;
        }
        
        .error-details-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 15px;
        }
        
        .error-details h3 {
            color: #666;
            font-size: 16px;
            margin: 0;
        }
        
        .toggle-details {
            background: none;
            border: none;
            color: #4CAF50;
            cursor: pointer;
            font-size: 14px;
            text-decoration: underline;
        }
        
        .error-info {
            display: none;
            font-size: 14px;
            color: #666;
            line-height: 1.6;
        }
        
        .error-info.show {
            display: block;
        }
        
        .error-info dt {
            font-weight: 600;
            color: #333;
            margin-top: 10px;
        }
        
        .error-info dd {
            margin: 5px 0 10px 20px;
            word-break: break-word;
        }
        
        .support-info {
            margin-top: 60px;
            padding: 30px;
            background-color: #e8f5e9;
            border-radius: 10px;
            border-left: 4px solid #4CAF50;
        }
        
        .support-info h3 {
            color: #2e7d32;
            font-size: 20px;
            margin: 0 0 15px 0;
        }
        
        .support-info p {
            color: #2e7d32;
            margin: 10px 0;
            font-size: 16px;
        }
        
        .support-info i {
            margin-right: 8px;
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
            
            .support-info {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="error-page">
        <div class="error-container">
            <div class="error-icon">
                <i class="fas fa-exclamation-triangle"></i>
            </div>
            <h1 class="error-code">500</h1>
            <h2 class="error-title">서버 오류가 발생했습니다</h2>
            <p class="error-message">
                죄송합니다. 요청을 처리하는 중에 오류가 발생했습니다.<br>
                잠시 후 다시 시도해 주시거나 고객센터로 문의해 주세요.
            </p>
            
            <div class="error-actions">
                <a href="${pageContext.request.contextPath}/" class="btn btn-primary">
                    <i class="fas fa-home"></i>
                    홈으로 가기
                </a>
                <a href="javascript:location.reload();" class="btn btn-secondary">
                    <i class="fas fa-redo"></i>
                    새로고침
                </a>
            </div>
            
            <c:if test="${not empty exception}">
                <div class="error-details">
                    <div class="error-details-header">
                        <h3>오류 정보</h3>
                        <button class="toggle-details" onclick="toggleErrorDetails()">
                            상세 보기
                        </button>
                    </div>
                    <dl class="error-info" id="errorInfo">
                        <dt>오류 코드:</dt>
                        <dd>${errorCode != null ? errorCode : 'INTERNAL_SERVER_ERROR'}</dd>
                        
                        <dt>시간:</dt>
                        <dd><%= new java.util.Date() %></dd>
                        
                        <c:if test="${not empty exception.message}">
                            <dt>메시지:</dt>
                            <dd>${exception.message}</dd>
                        </c:if>
                        
                        <c:if test="${not empty requestURI}">
                            <dt>요청 URL:</dt>
                            <dd>${requestURI}</dd>
                        </c:if>
                    </dl>
                </div>
            </c:if>
            
            <div class="support-info">
                <h3><i class="fas fa-headset"></i> 고객지원</h3>
                <p>
                    <i class="fas fa-phone"></i> 
                    전화: 1588-0000 (평일 09:00 - 18:00)
                </p>
                <p>
                    <i class="fas fa-envelope"></i> 
                    이메일: support@samyang.com
                </p>
                <p>
                    <i class="fas fa-comment"></i> 
                    실시간 채팅 상담 가능 (평일 09:00 - 18:00)
                </p>
            </div>
        </div>
    </div>
    
    <script>
        function toggleErrorDetails() {
            const errorInfo = document.getElementById('errorInfo');
            const toggleBtn = document.querySelector('.toggle-details');
            
            if (errorInfo.classList.contains('show')) {
                errorInfo.classList.remove('show');
                toggleBtn.textContent = '상세 보기';
            } else {
                errorInfo.classList.add('show');
                toggleBtn.textContent = '닫기';
            }
        }
        
        // 일정 시간 후 자동으로 홈으로 이동
        setTimeout(function() {
            console.log('30초 후 자동으로 홈페이지로 이동합니다.');
        }, 30000);
    </script>
</body>
</html>