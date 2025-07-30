<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .sidebar {
            width: 250px;
            background-color: #2c3e50;
            min-height: calc(100vh - 80px);
            padding: 20px 0;
            position: fixed;
            left: 0;
            top: 80px;
            overflow-y: auto;
            transition: transform 0.3s ease;
            z-index: 999;
        }
        
        .sidebar.collapsed {
            transform: translateX(-250px);
        }
        
        .sidebar-toggle {
            position: fixed;
            left: 250px;
            top: 100px;
            background-color: #2c3e50;
            color: white;
            border: none;
            padding: 10px;
            cursor: pointer;
            border-radius: 0 5px 5px 0;
            transition: left 0.3s ease;
            z-index: 1000;
        }
        
        .sidebar.collapsed + .sidebar-toggle {
            left: 0;
        }
        
        .sidebar-header {
            padding: 0 20px 20px;
            border-bottom: 1px solid #34495e;
            margin-bottom: 20px;
        }
        
        .user-profile {
            display: flex;
            align-items: center;
            gap: 15px;
            color: white;
        }
        
        .user-avatar {
            width: 50px;
            height: 50px;
            background-color: #4CAF50;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 24px;
        }
        
        .user-info h4 {
            margin: 0;
            font-size: 16px;
        }
        
        .user-info p {
            margin: 5px 0 0;
            font-size: 14px;
            color: #95a5a6;
        }
        
        .sidebar-menu {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        
        .sidebar-menu > li {
            margin-bottom: 5px;
        }
        
        .sidebar-menu a {
            display: flex;
            align-items: center;
            padding: 12px 20px;
            color: #ecf0f1;
            text-decoration: none;
            transition: all 0.3s;
            position: relative;
        }
        
        .sidebar-menu a:hover {
            background-color: #34495e;
            color: #4CAF50;
            padding-left: 25px;
        }
        
        .sidebar-menu a.active {
            background-color: #4CAF50;
            color: white;
        }
        
        .sidebar-menu a i {
            width: 20px;
            margin-right: 10px;
            font-size: 16px;
        }
        
        .sidebar-menu .badge {
            margin-left: auto;
            background-color: #e74c3c;
            color: white;
            padding: 2px 6px;
            border-radius: 10px;
            font-size: 12px;
        }
        
        .submenu {
            list-style: none;
            padding: 0;
            margin: 0;
            max-height: 0;
            overflow: hidden;
            transition: max-height 0.3s ease;
            background-color: #1a252f;
        }
        
        .submenu.active {
            max-height: 500px;
        }
        
        .submenu a {
            padding-left: 50px;
            font-size: 14px;
        }
        
        .menu-toggle {
            position: absolute;
            right: 20px;
            transition: transform 0.3s;
        }
        
        .menu-toggle.active {
            transform: rotate(90deg);
        }
        
        .sidebar-section {
            margin-top: 30px;
            padding-top: 30px;
            border-top: 1px solid #34495e;
        }
        
        .sidebar-title {
            color: #95a5a6;
            font-size: 12px;
            text-transform: uppercase;
            padding: 0 20px;
            margin-bottom: 10px;
            letter-spacing: 1px;
        }
        
        .quick-stats {
            padding: 0 20px;
            margin-top: 20px;
        }
        
        .stat-item {
            background-color: #34495e;
            border-radius: 5px;
            padding: 10px;
            margin-bottom: 10px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            color: white;
        }
        
        .stat-item i {
            color: #4CAF50;
            font-size: 20px;
        }
        
        .stat-value {
            font-size: 18px;
            font-weight: bold;
        }
        
        .stat-label {
            font-size: 12px;
            color: #95a5a6;
        }
        
        .main-content {
            margin-left: 250px;
            transition: margin-left 0.3s ease;
            min-height: calc(100vh - 80px);
            background-color: #f8f9fa;
        }
        
        .main-content.expanded {
            margin-left: 0;
        }
        
        @media (max-width: 768px) {
            .sidebar {
                transform: translateX(-250px);
            }
            
            .sidebar.active {
                transform: translateX(0);
            }
            
            .sidebar-toggle {
                left: 0;
            }
            
            .sidebar.active + .sidebar-toggle {
                left: 250px;
            }
            
            .main-content {
                margin-left: 0;
            }
        }
    </style>
</head>
<body>
    <aside class="sidebar" id="sidebar">
        <c:if test="${not empty sessionScope.user}">
            <div class="sidebar-header">
                <div class="user-profile">
                    <div class="user-avatar">
                        <i class="fas fa-user"></i>
                    </div>
                    <div class="user-info">
                        <h4>${sessionScope.user.name}</h4>
                        <p>${sessionScope.user.role == 'ADMIN' ? '관리자' : '일반회원'}</p>
                    </div>
                </div>
            </div>
        </c:if>
        
        <ul class="sidebar-menu">
            <li>
                <a href="${pageContext.request.contextPath}/" class="${pageContext.request.servletPath == '/index.jsp' ? 'active' : ''}">
                    <i class="fas fa-home"></i>
                    대시보드
                </a>
            </li>
            
            <li>
                <a href="javascript:void(0)" onclick="toggleSubmenu('cropMenu')">
                    <i class="fas fa-leaf"></i>
                    작물관리
                    <i class="fas fa-chevron-right menu-toggle" id="cropMenuToggle"></i>
                </a>
                <ul class="submenu" id="cropMenu">
                    <li><a href="${pageContext.request.contextPath}/crop/list">작물 목록</a></li>
                    <li><a href="${pageContext.request.contextPath}/crop/form">작물 등록</a></li>
                    <li><a href="${pageContext.request.contextPath}/crop/search">작물 검색</a></li>
                </ul>
            </li>
            
            <li>
                <a href="javascript:void(0)" onclick="toggleSubmenu('diaryMenu')">
                    <i class="fas fa-book"></i>
                    농업일지
                    <i class="fas fa-chevron-right menu-toggle" id="diaryMenuToggle"></i>
                </a>
                <ul class="submenu" id="diaryMenu">
                    <li><a href="${pageContext.request.contextPath}/diary/list">일지 목록</a></li>
                    <li><a href="${pageContext.request.contextPath}/diary/form">일지 작성</a></li>
                    <li><a href="${pageContext.request.contextPath}/diary/calendar">캘린더 보기</a></li>
                </ul>
            </li>
            
            <li>
                <a href="javascript:void(0)" onclick="toggleSubmenu('reviewMenu')">
                    <i class="fas fa-star"></i>
                    리뷰관리
                    <i class="fas fa-chevron-right menu-toggle" id="reviewMenuToggle"></i>
                </a>
                <ul class="submenu" id="reviewMenu">
                    <li><a href="${pageContext.request.contextPath}/review/list">전체 리뷰</a></li>
                    <li><a href="${pageContext.request.contextPath}/review/form">리뷰 작성</a></li>
                    <li><a href="${pageContext.request.contextPath}/review/my">내 리뷰</a></li>
                </ul>
            </li>
            
            <li>
                <a href="${pageContext.request.contextPath}/payment/list">
                    <i class="fas fa-credit-card"></i>
                    결제수단
                </a>
            </li>
            
            <li>
                <a href="${pageContext.request.contextPath}/comment/list">
                    <i class="fas fa-comments"></i>
                    댓글관리
                    <c:if test="${newCommentCount > 0}">
                        <span class="badge">${newCommentCount}</span>
                    </c:if>
                </a>
            </li>
        </ul>
        
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <div class="sidebar-section">
                <h3 class="sidebar-title">관리자 메뉴</h3>
                <ul class="sidebar-menu">
                    <li>
                        <a href="${pageContext.request.contextPath}/user/list">
                            <i class="fas fa-users"></i>
                            사용자 관리
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/report/list">
                            <i class="fas fa-flag"></i>
                            신고 관리
                            <c:if test="${pendingReportCount > 0}">
                                <span class="badge">${pendingReportCount}</span>
                            </c:if>
                        </a>
                    </li>
                    <li>
                        <a href="${pageContext.request.contextPath}/admin/statistics">
                            <i class="fas fa-chart-bar"></i>
                            통계 현황
                        </a>
                    </li>
                </ul>
            </div>
        </c:if>
        
        <div class="sidebar-section">
            <h3 class="sidebar-title">빠른 통계</h3>
            <div class="quick-stats">
                <div class="stat-item">
                    <div>
                        <div class="stat-value">${totalCrops != null ? totalCrops : 0}</div>
                        <div class="stat-label">등록 작물</div>
                    </div>
                    <i class="fas fa-seedling"></i>
                </div>
                <div class="stat-item">
                    <div>
                        <div class="stat-value">${totalDiaries != null ? totalDiaries : 0}</div>
                        <div class="stat-label">작성 일지</div>
                    </div>
                    <i class="fas fa-file-alt"></i>
                </div>
                <div class="stat-item">
                    <div>
                        <div class="stat-value">${totalReviews != null ? totalReviews : 0}</div>
                        <div class="stat-label">등록 리뷰</div>
                    </div>
                    <i class="fas fa-star"></i>
                </div>
            </div>
        </div>
    </aside>
    
    <button class="sidebar-toggle" onclick="toggleSidebar()">
        <i class="fas fa-bars"></i>
    </button>
    
    <script>
        function toggleSidebar() {
            const sidebar = document.getElementById('sidebar');
            const mainContent = document.querySelector('.main-content');
            
            sidebar.classList.toggle('collapsed');
            if (mainContent) {
                mainContent.classList.toggle('expanded');
            }
            
            // 모바일에서는 다른 동작
            if (window.innerWidth <= 768) {
                sidebar.classList.toggle('active');
                sidebar.classList.remove('collapsed');
            }
        }
        
        function toggleSubmenu(menuId) {
            const submenu = document.getElementById(menuId);
            const toggle = document.getElementById(menuId + 'Toggle');
            
            submenu.classList.toggle('active');
            toggle.classList.toggle('active');
            
            // 다른 서브메뉴 닫기
            const allSubmenus = document.querySelectorAll('.submenu');
            const allToggles = document.querySelectorAll('.menu-toggle');
            
            allSubmenus.forEach((menu) => {
                if (menu.id !== menuId) {
                    menu.classList.remove('active');
                }
            });
            
            allToggles.forEach((toggleIcon) => {
                if (toggleIcon.id !== menuId + 'Toggle') {
                    toggleIcon.classList.remove('active');
                }
            });
        }
        
        // 현재 페이지에 해당하는 메뉴 활성화
        window.addEventListener('DOMContentLoaded', function() {
            const currentPath = window.location.pathname;
            const menuItems = document.querySelectorAll('.sidebar-menu a');
            
            menuItems.forEach(item => {
                if (item.getAttribute('href') === currentPath) {
                    item.classList.add('active');
                    
                    // 부모 서브메뉴가 있다면 열기
                    const parentSubmenu = item.closest('.submenu');
                    if (parentSubmenu) {
                        parentSubmenu.classList.add('active');
                        const parentToggle = parentSubmenu.previousElementSibling.querySelector('.menu-toggle');
                        if (parentToggle) {
                            parentToggle.classList.add('active');
                        }
                    }
                }
            });
        });
    </script>
</body>
</html>