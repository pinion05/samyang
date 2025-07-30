<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        .header {
            background-color: #2c3e50;
            color: white;
            padding: 0;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
            position: sticky;
            top: 0;
            z-index: 1000;
        }
        
        .header-top {
            background-color: #34495e;
            padding: 10px 0;
            font-size: 14px;
        }
        
        .header-top-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .header-info {
            display: flex;
            gap: 20px;
        }
        
        .header-info span {
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .header-actions {
            display: flex;
            gap: 15px;
        }
        
        .header-actions a {
            color: white;
            text-decoration: none;
            transition: color 0.3s;
        }
        
        .header-actions a:hover {
            color: #4CAF50;
        }
        
        .header-main {
            background-color: white;
            padding: 15px 0;
            border-bottom: 3px solid #4CAF50;
        }
        
        .header-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 0 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .logo {
            display: flex;
            align-items: center;
            text-decoration: none;
            color: #2c3e50;
        }
        
        .logo i {
            font-size: 32px;
            color: #4CAF50;
            margin-right: 10px;
        }
        
        .logo-text {
            font-size: 24px;
            font-weight: bold;
        }
        
        .nav-menu {
            display: flex;
            list-style: none;
            gap: 30px;
        }
        
        .nav-menu li {
            position: relative;
        }
        
        .nav-menu a {
            color: #2c3e50;
            text-decoration: none;
            font-weight: 600;
            padding: 10px 0;
            transition: color 0.3s;
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .nav-menu a:hover {
            color: #4CAF50;
        }
        
        .dropdown {
            position: absolute;
            top: 100%;
            left: 0;
            background-color: white;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            border-radius: 5px;
            min-width: 200px;
            display: none;
            z-index: 1000;
            margin-top: 10px;
        }
        
        .nav-menu li:hover .dropdown {
            display: block;
        }
        
        .dropdown a {
            display: block;
            padding: 12px 20px;
            color: #333;
            font-weight: normal;
            border-bottom: 1px solid #eee;
        }
        
        .dropdown a:last-child {
            border-bottom: none;
        }
        
        .dropdown a:hover {
            background-color: #f8f9fa;
            color: #4CAF50;
        }
        
        .user-menu {
            display: flex;
            align-items: center;
            gap: 20px;
        }
        
        .user-info {
            display: flex;
            align-items: center;
            gap: 10px;
            padding: 8px 16px;
            background-color: #f8f9fa;
            border-radius: 25px;
            color: #333;
        }
        
        .user-info i {
            color: #4CAF50;
        }
        
        .btn-logout {
            background-color: #dc3545;
            color: white;
            padding: 8px 20px;
            border-radius: 5px;
            text-decoration: none;
            font-weight: 600;
            transition: background-color 0.3s;
        }
        
        .btn-logout:hover {
            background-color: #c82333;
        }
        
        .mobile-menu-toggle {
            display: none;
            background: none;
            border: none;
            font-size: 24px;
            color: #2c3e50;
            cursor: pointer;
        }
        
        @media (max-width: 768px) {
            .header-info {
                display: none;
            }
            
            .header-main {
                padding: 10px 0;
            }
            
            .nav-menu {
                position: fixed;
                top: 0;
                left: -100%;
                width: 280px;
                height: 100vh;
                background-color: white;
                flex-direction: column;
                padding: 20px;
                gap: 10px;
                box-shadow: 2px 0 10px rgba(0,0,0,0.1);
                transition: left 0.3s;
                z-index: 1001;
                overflow-y: auto;
            }
            
            .nav-menu.active {
                left: 0;
            }
            
            .nav-menu li {
                width: 100%;
            }
            
            .nav-menu a {
                padding: 15px 10px;
                border-bottom: 1px solid #eee;
            }
            
            .dropdown {
                position: static;
                box-shadow: none;
                margin-top: 0;
                display: block;
                background-color: #f8f9fa;
                margin-left: 20px;
            }
            
            .mobile-menu-toggle {
                display: block;
            }
            
            .user-menu {
                flex-direction: column;
                gap: 10px;
                align-items: flex-start;
            }
            
            .logo-text {
                font-size: 20px;
            }
        }
    </style>
</head>
<body>
    <header class="header">
        <div class="header-top">
            <div class="header-top-content">
                <div class="header-info">
                    <span><i class="fas fa-phone"></i> 1588-0000</span>
                    <span><i class="fas fa-envelope"></i> info@samyang.com</span>
                    <span><i class="fas fa-clock"></i> 평일 09:00 - 18:00</span>
                </div>
                <div class="header-actions">
                    <c:choose>
                        <c:when test="${empty sessionScope.user}">
                            <a href="${pageContext.request.contextPath}/user/login">로그인</a>
                            <a href="${pageContext.request.contextPath}/user/register">회원가입</a>
                        </c:when>
                        <c:otherwise>
                            <a href="${pageContext.request.contextPath}/user/profile">내 정보</a>
                            <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                <a href="${pageContext.request.contextPath}/admin">관리자</a>
                            </c:if>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>
        </div>
        
        <div class="header-main">
            <div class="header-content">
                <a href="${pageContext.request.contextPath}/" class="logo">
                    <i class="fas fa-seedling"></i>
                    <span class="logo-text">삼양농업플랫폼</span>
                </a>
                
                <button class="mobile-menu-toggle" onclick="toggleMobileMenu()">
                    <i class="fas fa-bars"></i>
                </button>
                
                <nav>
                    <ul class="nav-menu" id="navMenu">
                        <li>
                            <a href="${pageContext.request.contextPath}/">
                                <i class="fas fa-home"></i> 홈
                            </a>
                        </li>
                        
                        <li>
                            <a href="${pageContext.request.contextPath}/crop/list">
                                <i class="fas fa-leaf"></i> 작물관리
                                <i class="fas fa-chevron-down"></i>
                            </a>
                            <div class="dropdown">
                                <a href="${pageContext.request.contextPath}/crop/list">작물 목록</a>
                                <a href="${pageContext.request.contextPath}/crop/form">작물 등록</a>
                                <a href="${pageContext.request.contextPath}/crop/search">작물 검색</a>
                            </div>
                        </li>
                        
                        <li>
                            <a href="${pageContext.request.contextPath}/diary/list">
                                <i class="fas fa-book"></i> 농업일지
                                <i class="fas fa-chevron-down"></i>
                            </a>
                            <div class="dropdown">
                                <a href="${pageContext.request.contextPath}/diary/list">일지 목록</a>
                                <a href="${pageContext.request.contextPath}/diary/form">일지 작성</a>
                                <a href="${pageContext.request.contextPath}/diary/calendar">캘린더 보기</a>
                            </div>
                        </li>
                        
                        <li>
                            <a href="${pageContext.request.contextPath}/review/list">
                                <i class="fas fa-star"></i> 리뷰
                                <i class="fas fa-chevron-down"></i>
                            </a>
                            <div class="dropdown">
                                <a href="${pageContext.request.contextPath}/review/list">전체 리뷰</a>
                                <a href="${pageContext.request.contextPath}/review/form">리뷰 작성</a>
                                <a href="${pageContext.request.contextPath}/review/my">내 리뷰</a>
                            </div>
                        </li>
                        
                        <li>
                            <a href="${pageContext.request.contextPath}/payment/list">
                                <i class="fas fa-credit-card"></i> 결제수단
                            </a>
                        </li>
                        
                        <c:if test="${sessionScope.user.role == 'ADMIN'}">
                            <li>
                                <a href="${pageContext.request.contextPath}/report/list">
                                    <i class="fas fa-flag"></i> 신고관리
                                </a>
                            </li>
                        </c:if>
                        
                        <c:if test="${not empty sessionScope.user}">
                            <li class="user-menu">
                                <div class="user-info">
                                    <i class="fas fa-user-circle"></i>
                                    <span>${sessionScope.user.name}님</span>
                                </div>
                                <a href="${pageContext.request.contextPath}/user/logout" class="btn-logout">
                                    로그아웃
                                </a>
                            </li>
                        </c:if>
                    </ul>
                </nav>
            </div>
        </div>
    </header>
    
    <script>
        function toggleMobileMenu() {
            const navMenu = document.getElementById('navMenu');
            navMenu.classList.toggle('active');
        }
        
        // 모바일 메뉴 외부 클릭 시 닫기
        document.addEventListener('click', function(event) {
            const navMenu = document.getElementById('navMenu');
            const menuToggle = document.querySelector('.mobile-menu-toggle');
            
            if (!navMenu.contains(event.target) && !menuToggle.contains(event.target)) {
                navMenu.classList.remove('active');
            }
        });
    </script>
</body>
</html>