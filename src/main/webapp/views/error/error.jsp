<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>오류 발생 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .error-container {
            max-width: 800px;
            margin: 50px auto;
            padding: 30px;
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        .error-icon {
            font-size: 80px;
            color: #dc3545;
            margin-bottom: 20px;
        }
        .error-code {
            font-size: 24px;
            font-weight: bold;
            color: #dc3545;
            margin-bottom: 10px;
        }
        .error-message {
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
        }
        .error-detail {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
            text-align: left;
        }
        .error-id {
            color: #6c757d;
            font-size: 14px;
            margin-top: 20px;
        }
        .action-buttons {
            margin-top: 30px;
        }
        .action-buttons a {
            display: inline-block;
            padding: 10px 20px;
            margin: 0 10px;
            background: #28a745;
            color: white;
            text-decoration: none;
            border-radius: 5px;
            transition: background 0.3s;
        }
        .action-buttons a:hover {
            background: #218838;
        }
        .validation-errors {
            text-align: left;
            background: #fff3cd;
            border: 1px solid #ffeeba;
            padding: 15px;
            border-radius: 5px;
            margin: 20px 0;
        }
        .validation-errors ul {
            margin: 0;
            padding-left: 20px;
        }
        .debug-info {
            margin-top: 30px;
            padding: 20px;
            background: #f8f9fa;
            border: 1px solid #dee2e6;
            border-radius: 5px;
            text-align: left;
        }
        .debug-info pre {
            background: #fff;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 3px;
            overflow-x: auto;
            font-size: 12px;
        }
    </style>
</head>
<body>
    <div class="error-container">
        <div class="error-icon">⚠️</div>
        
        <c:if test="${not empty errorCode}">
            <div class="error-code">에러 코드: ${errorCode}</div>
        </c:if>
        
        <div class="error-message">
            ${errorMessage != null ? errorMessage : '요청을 처리하는 중 오류가 발생했습니다.'}
        </div>
        
        <c:if test="${not empty errorDetail}">
            <div class="error-detail">
                <strong>상세 정보:</strong><br>
                ${errorDetail}
            </div>
        </c:if>
        
        <c:if test="${not empty validationErrors}">
            <div class="validation-errors">
                <strong>입력값 오류:</strong>
                <ul>
                    <c:forEach items="${validationErrors}" var="error">
                        <li>${error.key}: ${error.value}</li>
                    </c:forEach>
                </ul>
            </div>
        </c:if>
        
        <div class="error-id">
            오류 ID: ${errorId}<br>
            발생 시간: <fmt:formatDate value="${timestamp}" pattern="yyyy-MM-dd HH:mm:ss"/>
        </div>
        
        <div class="action-buttons">
            <a href="javascript:history.back()">이전 페이지</a>
            <a href="${pageContext.request.contextPath}/">홈으로</a>
        </div>
        
        <c:if test="${not empty debugMessage || not empty stackTrace}">
            <div class="debug-info">
                <h3>디버그 정보 (개발 환경)</h3>
                <c:if test="${not empty debugMessage}">
                    <p><strong>메시지:</strong> ${debugMessage}</p>
                </c:if>
                <c:if test="${not empty stackTrace}">
                    <p><strong>스택 트레이스:</strong></p>
                    <pre>${stackTrace}</pre>
                </c:if>
            </div>
        </c:if>
    </div>
</body>
</html>