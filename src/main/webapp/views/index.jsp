<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>대시보드 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f8f9fa;
        }
        
        .dashboard-container {
            padding: 30px;
        }
        
        .dashboard-header {
            margin-bottom: 30px;
        }
        
        .dashboard-header h1 {
            color: #2c3e50;
            margin: 0 0 10px 0;
            font-size: 32px;
        }
        
        .dashboard-header p {
            color: #666;
            margin: 0;
            font-size: 16px;
        }
        
        .stats-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
            gap: 25px;
            margin-bottom: 40px;
        }
        
        .stat-card {
            background: white;
            padding: 25px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            display: flex;
            align-items: center;
            justify-content: space-between;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .stat-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.1);
        }
        
        .stat-content h3 {
            margin: 0;
            font-size: 14px;
            color: #666;
            text-transform: uppercase;
            letter-spacing: 0.5px;
        }
        
        .stat-content .value {
            font-size: 36px;
            font-weight: bold;
            color: #2c3e50;
            margin: 10px 0;
        }
        
        .stat-content .trend {
            font-size: 14px;
            display: flex;
            align-items: center;
            gap: 5px;
        }
        
        .trend.up {
            color: #4CAF50;
        }
        
        .trend.down {
            color: #dc3545;
        }
        
        .stat-icon {
            width: 60px;
            height: 60px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 28px;
        }
        
        .stat-icon.green {
            background-color: #d4edda;
            color: #155724;
        }
        
        .stat-icon.blue {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .stat-icon.yellow {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .stat-icon.red {
            background-color: #f8d7da;
            color: #721c24;
        }
        
        .content-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(500px, 1fr));
            gap: 30px;
            margin-bottom: 40px;
        }
        
        @media (max-width: 1024px) {
            .content-grid {
                grid-template-columns: 1fr;
            }
        }
        
        .content-card {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        .card-header {
            padding: 20px;
            border-bottom: 1px solid #eee;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .card-header h2 {
            margin: 0;
            font-size: 20px;
            color: #2c3e50;
        }
        
        .card-header a {
            color: #4CAF50;
            text-decoration: none;
            font-size: 14px;
            font-weight: 500;
        }
        
        .card-header a:hover {
            text-decoration: underline;
        }
        
        .card-body {
            padding: 20px;
        }
        
        .recent-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        
        .recent-list li {
            padding: 15px 0;
            border-bottom: 1px solid #f0f0f0;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .recent-list li:last-child {
            border-bottom: none;
        }
        
        .recent-info h4 {
            margin: 0 0 5px 0;
            font-size: 16px;
            color: #333;
        }
        
        .recent-info p {
            margin: 0;
            font-size: 14px;
            color: #666;
        }
        
        .recent-meta {
            text-align: right;
            font-size: 14px;
            color: #999;
        }
        
        .weather-widget {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            text-align: center;
            margin-bottom: 30px;
        }
        
        .weather-widget h3 {
            margin: 0 0 20px 0;
            font-size: 24px;
        }
        
        .weather-info {
            display: flex;
            justify-content: space-around;
            align-items: center;
        }
        
        .weather-temp {
            font-size: 48px;
            font-weight: bold;
        }
        
        .weather-desc {
            font-size: 18px;
            margin-top: 10px;
        }
        
        .quick-actions {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(200px, 1fr));
            gap: 20px;
            margin-bottom: 40px;
        }
        
        .action-button {
            background: white;
            border: 2px solid #e9ecef;
            border-radius: 10px;
            padding: 20px;
            text-align: center;
            text-decoration: none;
            color: #333;
            transition: all 0.3s;
            display: block;
        }
        
        .action-button:hover {
            border-color: #4CAF50;
            transform: translateY(-3px);
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .action-button i {
            font-size: 36px;
            color: #4CAF50;
            margin-bottom: 10px;
            display: block;
        }
        
        .action-button span {
            font-weight: 600;
            font-size: 16px;
        }
        
        .empty-state {
            text-align: center;
            padding: 40px;
            color: #999;
        }
        
        .empty-state i {
            font-size: 48px;
            color: #ddd;
            margin-bottom: 20px;
            display: block;
        }
        
        @media (max-width: 768px) {
            .dashboard-container {
                padding: 20px;
            }
            
            .stats-grid {
                grid-template-columns: 1fr;
            }
            
            .quick-actions {
                grid-template-columns: repeat(2, 1fr);
            }
        }
    </style>
</head>
<body>
    <jsp:include page="common/header.jsp" />
    
    <div style="display: flex;">
        <jsp:include page="common/sidebar.jsp" />
        
        <main class="main-content">
            <div class="dashboard-container">
                <div class="dashboard-header">
                    <h1>대시보드</h1>
                    <p>
                        <c:choose>
                            <c:when test="${not empty sessionScope.user}">
                                ${sessionScope.user.name}님, 환영합니다! 오늘도 풍성한 수확을 기원합니다.
                            </c:when>
                            <c:otherwise>
                                삼양농업플랫폼에 오신 것을 환영합니다.
                            </c:otherwise>
                        </c:choose>
                    </p>
                </div>
                
                <c:if test="${not empty sessionScope.user}">
                    <div class="stats-grid">
                        <div class="stat-card">
                            <div class="stat-content">
                                <h3>등록된 작물</h3>
                                <div class="value">${totalCrops != null ? totalCrops : 0}</div>
                                <div class="trend up">
                                    <i class="fas fa-arrow-up"></i>
                                    <span>지난달 대비 12% 증가</span>
                                </div>
                            </div>
                            <div class="stat-icon green">
                                <i class="fas fa-seedling"></i>
                            </div>
                        </div>
                        
                        <div class="stat-card">
                            <div class="stat-content">
                                <h3>작성된 일지</h3>
                                <div class="value">${totalDiaries != null ? totalDiaries : 0}</div>
                                <div class="trend up">
                                    <i class="fas fa-arrow-up"></i>
                                    <span>지난주 대비 8% 증가</span>
                                </div>
                            </div>
                            <div class="stat-icon blue">
                                <i class="fas fa-book"></i>
                            </div>
                        </div>
                        
                        <div class="stat-card">
                            <div class="stat-content">
                                <h3>등록된 리뷰</h3>
                                <div class="value">${totalReviews != null ? totalReviews : 0}</div>
                                <div class="trend up">
                                    <i class="fas fa-arrow-up"></i>
                                    <span>지난달 대비 15% 증가</span>
                                </div>
                            </div>
                            <div class="stat-icon yellow">
                                <i class="fas fa-star"></i>
                            </div>
                        </div>
                        
                        <div class="stat-card">
                            <div class="stat-content">
                                <h3>활성 사용자</h3>
                                <div class="value">${activeUsers != null ? activeUsers : 0}</div>
                                <div class="trend down">
                                    <i class="fas fa-arrow-down"></i>
                                    <span>지난주 대비 3% 감소</span>
                                </div>
                            </div>
                            <div class="stat-icon red">
                                <i class="fas fa-users"></i>
                            </div>
                        </div>
                    </div>
                    
                    <div class="weather-widget">
                        <h3>오늘의 농업 날씨</h3>
                        <div class="weather-info">
                            <div>
                                <div class="weather-temp">24°C</div>
                                <div class="weather-desc">맑음</div>
                            </div>
                            <div>
                                <p>습도: 65%</p>
                                <p>강수확률: 10%</p>
                                <p>풍속: 5m/s</p>
                            </div>
                        </div>
                    </div>
                    
                    <h2 style="margin-bottom: 20px; color: #2c3e50;">빠른 작업</h2>
                    <div class="quick-actions">
                        <a href="${pageContext.request.contextPath}/crop/form" class="action-button">
                            <i class="fas fa-plus-circle"></i>
                            <span>작물 등록</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/diary/form" class="action-button">
                            <i class="fas fa-pen"></i>
                            <span>일지 작성</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/review/form" class="action-button">
                            <i class="fas fa-star"></i>
                            <span>리뷰 작성</span>
                        </a>
                        <a href="${pageContext.request.contextPath}/payment/form" class="action-button">
                            <i class="fas fa-credit-card"></i>
                            <span>결제수단 추가</span>
                        </a>
                    </div>
                    
                    <div class="content-grid">
                        <div class="content-card">
                            <div class="card-header">
                                <h2>최근 농업일지</h2>
                                <a href="${pageContext.request.contextPath}/diary/list">전체보기 →</a>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${not empty recentDiaries}">
                                        <ul class="recent-list">
                                            <c:forEach var="diary" items="${recentDiaries}">
                                                <li>
                                                    <div class="recent-info">
                                                        <h4>${diary.title}</h4>
                                                        <p>${diary.cropName} - ${diary.activityType}</p>
                                                    </div>
                                                    <div class="recent-meta">
                                                        <fmt:formatDate value="${diary.diaryDate}" pattern="MM월 dd일"/>
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <i class="fas fa-book-open"></i>
                                            <p>아직 작성된 일지가 없습니다.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                        
                        <div class="content-card">
                            <div class="card-header">
                                <h2>최근 리뷰</h2>
                                <a href="${pageContext.request.contextPath}/review/list">전체보기 →</a>
                            </div>
                            <div class="card-body">
                                <c:choose>
                                    <c:when test="${not empty recentReviews}">
                                        <ul class="recent-list">
                                            <c:forEach var="review" items="${recentReviews}">
                                                <li>
                                                    <div class="recent-info">
                                                        <h4>${review.cropName}</h4>
                                                        <p>
                                                            <c:forEach begin="1" end="${review.rating}">
                                                                <i class="fas fa-star" style="color: #ffc107;"></i>
                                                            </c:forEach>
                                                            <c:forEach begin="${review.rating + 1}" end="5">
                                                                <i class="far fa-star" style="color: #ffc107;"></i>
                                                            </c:forEach>
                                                        </p>
                                                    </div>
                                                    <div class="recent-meta">
                                                        ${review.userName}
                                                    </div>
                                                </li>
                                            </c:forEach>
                                        </ul>
                                    </c:when>
                                    <c:otherwise>
                                        <div class="empty-state">
                                            <i class="fas fa-star"></i>
                                            <p>아직 작성된 리뷰가 없습니다.</p>
                                        </div>
                                    </c:otherwise>
                                </c:choose>
                            </div>
                        </div>
                    </div>
                </c:if>
                
                <c:if test="${empty sessionScope.user}">
                    <div style="text-align: center; padding: 60px 20px;">
                        <i class="fas fa-lock" style="font-size: 64px; color: #ddd; margin-bottom: 20px;"></i>
                        <h2 style="color: #666; margin-bottom: 20px;">로그인이 필요합니다</h2>
                        <p style="color: #999; margin-bottom: 30px;">
                            대시보드를 이용하시려면 로그인해 주세요.
                        </p>
                        <a href="${pageContext.request.contextPath}/user/login" 
                           style="display: inline-block; padding: 12px 30px; background-color: #4CAF50; color: white; text-decoration: none; border-radius: 5px; font-weight: bold;">
                            로그인하기
                        </a>
                    </div>
                </c:if>
            </div>
        </main>
    </div>
    
    <jsp:include page="common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
</body>
</html>