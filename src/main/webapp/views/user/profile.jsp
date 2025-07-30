<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>내 정보 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        body {
            margin: 0;
            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', Roboto, 'Helvetica Neue', Arial, sans-serif;
            background-color: #f8f9fa;
        }
        
        .profile-container {
            max-width: 800px;
            margin: 0 auto;
            padding: 30px 20px;
        }
        
        .profile-header {
            background: white;
            border-radius: 10px;
            padding: 30px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            margin-bottom: 30px;
            text-align: center;
        }
        
        .profile-avatar {
            width: 120px;
            height: 120px;
            background-color: #4CAF50;
            border-radius: 50%;
            display: inline-flex;
            align-items: center;
            justify-content: center;
            font-size: 48px;
            color: white;
            margin-bottom: 20px;
        }
        
        .profile-info h1 {
            color: #2c3e50;
            margin: 0 0 10px 0;
            font-size: 28px;
        }
        
        .profile-info p {
            color: #666;
            margin: 5px 0;
            font-size: 16px;
        }
        
        .profile-stats {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(150px, 1fr));
            gap: 20px;
            margin-top: 30px;
        }
        
        .stat-item {
            text-align: center;
            padding: 20px;
            background-color: #f8f9fa;
            border-radius: 10px;
        }
        
        .stat-value {
            font-size: 32px;
            font-weight: bold;
            color: #4CAF50;
            margin: 0;
        }
        
        .stat-label {
            font-size: 14px;
            color: #666;
            margin-top: 5px;
        }
        
        .profile-content {
            background: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.08);
            overflow: hidden;
        }
        
        .profile-tabs {
            display: flex;
            background-color: #f8f9fa;
            border-bottom: 2px solid #e9ecef;
        }
        
        .tab-btn {
            flex: 1;
            padding: 15px 20px;
            background: none;
            border: none;
            cursor: pointer;
            font-size: 16px;
            font-weight: 600;
            color: #666;
            transition: all 0.3s;
            position: relative;
        }
        
        .tab-btn:hover {
            color: #4CAF50;
        }
        
        .tab-btn.active {
            color: #4CAF50;
        }
        
        .tab-btn.active::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            right: 0;
            height: 2px;
            background-color: #4CAF50;
        }
        
        .tab-content {
            padding: 30px;
            display: none;
        }
        
        .tab-content.active {
            display: block;
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-label {
            display: block;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        
        .form-control {
            width: 100%;
            padding: 12px 15px;
            border: 1px solid #ddd;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 0.2rem rgba(76, 175, 80, 0.25);
        }
        
        .form-control[readonly] {
            background-color: #f8f9fa;
            cursor: not-allowed;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 8px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
            transform: translateY(-2px);
            box-shadow: 0 4px 12px rgba(76, 175, 80, 0.3);
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
            margin-left: 10px;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .alert {
            padding: 15px 20px;
            margin-bottom: 20px;
            border-radius: 8px;
            font-weight: 500;
        }
        
        .alert-success {
            background-color: #d4edda;
            color: #155724;
            border: 1px solid #c3e6cb;
        }
        
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .activity-list {
            list-style: none;
            padding: 0;
            margin: 0;
        }
        
        .activity-item {
            padding: 20px;
            border-bottom: 1px solid #e9ecef;
            display: flex;
            align-items: center;
            gap: 15px;
        }
        
        .activity-item:last-child {
            border-bottom: none;
        }
        
        .activity-icon {
            width: 40px;
            height: 40px;
            border-radius: 50%;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 18px;
            flex-shrink: 0;
        }
        
        .activity-icon.crop {
            background-color: #d4edda;
            color: #155724;
        }
        
        .activity-icon.diary {
            background-color: #cce5ff;
            color: #004085;
        }
        
        .activity-icon.review {
            background-color: #fff3cd;
            color: #856404;
        }
        
        .activity-content {
            flex: 1;
        }
        
        .activity-title {
            font-weight: 600;
            color: #333;
            margin: 0 0 5px 0;
        }
        
        .activity-time {
            font-size: 14px;
            color: #999;
        }
        
        .empty-state {
            text-align: center;
            padding: 60px 20px;
            color: #999;
        }
        
        .empty-state i {
            font-size: 64px;
            color: #ddd;
            margin-bottom: 20px;
        }
        
        @media (max-width: 768px) {
            .profile-container {
                padding: 20px 15px;
            }
            
            .profile-stats {
                grid-template-columns: 1fr 1fr;
            }
            
            .profile-tabs {
                overflow-x: auto;
                -webkit-overflow-scrolling: touch;
            }
            
            .tab-btn {
                white-space: nowrap;
                min-width: 120px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="profile-container">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success">
                ${successMessage}
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <div class="profile-header">
            <div class="profile-avatar">
                <i class="fas fa-user"></i>
            </div>
            <div class="profile-info">
                <h1>${user.name}</h1>
                <p><i class="fas fa-envelope"></i> ${user.email}</p>
                <p><i class="fas fa-phone"></i> ${user.phone}</p>
                <p><i class="fas fa-calendar"></i> 가입일: <fmt:formatDate value="${user.createdAt}" pattern="yyyy년 MM월 dd일"/></p>
            </div>
            <div class="profile-stats">
                <div class="stat-item">
                    <p class="stat-value">${cropCount != null ? cropCount : 0}</p>
                    <p class="stat-label">등록 작물</p>
                </div>
                <div class="stat-item">
                    <p class="stat-value">${diaryCount != null ? diaryCount : 0}</p>
                    <p class="stat-label">작성 일지</p>
                </div>
                <div class="stat-item">
                    <p class="stat-value">${reviewCount != null ? reviewCount : 0}</p>
                    <p class="stat-label">리뷰</p>
                </div>
                <div class="stat-item">
                    <p class="stat-value">${commentCount != null ? commentCount : 0}</p>
                    <p class="stat-label">댓글</p>
                </div>
            </div>
        </div>
        
        <div class="profile-content">
            <div class="profile-tabs">
                <button class="tab-btn active" onclick="showTab('info')">
                    <i class="fas fa-user"></i> 기본 정보
                </button>
                <button class="tab-btn" onclick="showTab('password')">
                    <i class="fas fa-lock"></i> 비밀번호 변경
                </button>
                <button class="tab-btn" onclick="showTab('activity')">
                    <i class="fas fa-history"></i> 최근 활동
                </button>
            </div>
            
            <div class="tab-content active" id="info-tab">
                <form action="${pageContext.request.contextPath}/user/profile/update" method="post">
                    <div class="form-group">
                        <label class="form-label" for="loginId">아이디</label>
                        <input type="text" 
                               class="form-control" 
                               id="loginId" 
                               value="${user.loginId}" 
                               readonly>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="name">이름</label>
                        <input type="text" 
                               class="form-control" 
                               id="name" 
                               name="name" 
                               value="${user.name}" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="email">이메일</label>
                        <input type="email" 
                               class="form-control" 
                               id="email" 
                               name="email" 
                               value="${user.email}" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="phone">전화번호</label>
                        <input type="tel" 
                               class="form-control" 
                               id="phone" 
                               name="phone" 
                               value="${user.phone}" 
                               pattern="[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}"
                               placeholder="010-1234-5678" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label">회원 등급</label>
                        <input type="text" 
                               class="form-control" 
                               value="${user.role == 'ADMIN' ? '관리자' : '일반회원'}" 
                               readonly>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save"></i> 정보 수정
                    </button>
                    <a href="${pageContext.request.contextPath}/" class="btn btn-secondary">
                        취소
                    </a>
                </form>
            </div>
            
            <div class="tab-content" id="password-tab">
                <form action="${pageContext.request.contextPath}/user/password/change" method="post">
                    <div class="form-group">
                        <label class="form-label" for="currentPassword">현재 비밀번호</label>
                        <input type="password" 
                               class="form-control" 
                               id="currentPassword" 
                               name="currentPassword" 
                               required>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="newPassword">새 비밀번호</label>
                        <input type="password" 
                               class="form-control" 
                               id="newPassword" 
                               name="newPassword" 
                               pattern="^(?=.*[a-zA-Z])(?=.*[0-9]).{8,20}$"
                               title="영문과 숫자를 포함한 8~20자" 
                               required>
                        <small class="text-muted">영문과 숫자를 포함한 8~20자로 입력해주세요.</small>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="confirmPassword">새 비밀번호 확인</label>
                        <input type="password" 
                               class="form-control" 
                               id="confirmPassword" 
                               name="confirmPassword" 
                               required>
                    </div>
                    
                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-key"></i> 비밀번호 변경
                    </button>
                    <button type="button" class="btn btn-secondary" onclick="showTab('info')">
                        취소
                    </button>
                </form>
            </div>
            
            <div class="tab-content" id="activity-tab">
                <c:choose>
                    <c:when test="${not empty recentActivities}">
                        <ul class="activity-list">
                            <c:forEach var="activity" items="${recentActivities}">
                                <li class="activity-item">
                                    <div class="activity-icon ${activity.type}">
                                        <c:choose>
                                            <c:when test="${activity.type == 'crop'}">
                                                <i class="fas fa-seedling"></i>
                                            </c:when>
                                            <c:when test="${activity.type == 'diary'}">
                                                <i class="fas fa-book"></i>
                                            </c:when>
                                            <c:when test="${activity.type == 'review'}">
                                                <i class="fas fa-star"></i>
                                            </c:when>
                                            <c:otherwise>
                                                <i class="fas fa-comment"></i>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                    <div class="activity-content">
                                        <p class="activity-title">${activity.title}</p>
                                        <p class="activity-time">
                                            <fmt:formatDate value="${activity.createdAt}" pattern="yyyy년 MM월 dd일 HH:mm"/>
                                        </p>
                                    </div>
                                </li>
                            </c:forEach>
                        </ul>
                    </c:when>
                    <c:otherwise>
                        <div class="empty-state">
                            <i class="fas fa-history"></i>
                            <h3>최근 활동이 없습니다</h3>
                            <p>작물을 등록하거나 일지를 작성해보세요!</p>
                        </div>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        function showTab(tabName) {
            // 모든 탭 버튼과 컨텐츠 비활성화
            const tabs = document.querySelectorAll('.tab-btn');
            const contents = document.querySelectorAll('.tab-content');
            
            tabs.forEach(tab => tab.classList.remove('active'));
            contents.forEach(content => content.classList.remove('active'));
            
            // 선택된 탭 활성화
            event.target.closest('.tab-btn').classList.add('active');
            document.getElementById(tabName + '-tab').classList.add('active');
        }
        
        // 비밀번호 확인 검증
        document.getElementById('confirmPassword').addEventListener('input', function() {
            const newPassword = document.getElementById('newPassword').value;
            const confirmPassword = this.value;
            
            if (newPassword !== confirmPassword) {
                this.setCustomValidity('비밀번호가 일치하지 않습니다.');
            } else {
                this.setCustomValidity('');
            }
        });
        
        // 알림 메시지 자동 숨기기
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                alert.style.display = 'none';
            });
        }, 5000);
    </script>
</body>
</html>