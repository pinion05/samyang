<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신고 상세 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .report-detail-container {
            max-width: 1000px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .report-detail-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .report-detail-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .btn-group {
            display: flex;
            gap: 10px;
        }
        
        .btn {
            padding: 10px 20px;
            text-decoration: none;
            border-radius: 5px;
            font-weight: bold;
            transition: all 0.3s;
            border: none;
            cursor: pointer;
            display: inline-flex;
            align-items: center;
            gap: 5px;
        }
        
        .btn-secondary {
            background-color: #6c757d;
            color: white;
        }
        
        .btn-secondary:hover {
            background-color: #5a6268;
        }
        
        .btn-danger {
            background-color: #dc3545;
            color: white;
        }
        
        .btn-danger:hover {
            background-color: #c82333;
        }
        
        .report-status-card {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .status-info h2 {
            color: #333;
            margin: 0 0 10px 0;
            font-size: 20px;
        }
        
        .status-badge-large {
            display: inline-block;
            padding: 12px 24px;
            border-radius: 25px;
            font-size: 16px;
            font-weight: 600;
        }
        
        .status-warning { 
            background-color: #fff3cd; 
            color: #856404; 
        }
        
        .status-info { 
            background-color: #d1ecf1; 
            color: #0c5460; 
        }
        
        .status-success { 
            background-color: #d4edda; 
            color: #155724; 
        }
        
        .status-danger { 
            background-color: #f8d7da; 
            color: #721c24; 
        }
        
        .report-detail-content {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
        }
        
        .report-section {
            padding: 30px;
            border-bottom: 1px solid #eee;
        }
        
        .report-section:last-child {
            border-bottom: none;
        }
        
        .section-title {
            font-size: 18px;
            color: #333;
            margin-bottom: 20px;
            font-weight: 600;
        }
        
        .info-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 20px;
        }
        
        .info-item {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }
        
        .info-label {
            font-size: 14px;
            color: #666;
            font-weight: 500;
        }
        
        .info-value {
            font-size: 16px;
            color: #333;
        }
        
        .type-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 15px;
            font-size: 14px;
            font-weight: 500;
        }
        
        .type-user { 
            background-color: #e3f2fd; 
            color: #1565c0; 
        }
        
        .type-crop { 
            background-color: #d4edda; 
            color: #155724; 
        }
        
        .type-diary { 
            background-color: #cce5ff; 
            color: #004085; 
        }
        
        .type-review { 
            background-color: #fff3cd; 
            color: #856404; 
        }
        
        .type-comment { 
            background-color: #f8d7da; 
            color: #721c24; 
        }
        
        .category-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 15px;
            font-size: 14px;
            background-color: #e9ecef;
            color: #495057;
        }
        
        .reason-box {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-top: 15px;
        }
        
        .admin-section {
            background-color: #fff3cd;
            padding: 30px;
        }
        
        .admin-form {
            margin-top: 20px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        .form-label {
            display: block;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 0.2rem rgba(76, 175, 80, 0.25);
        }
        
        select.form-control {
            height: 48px;
        }
        
        textarea.form-control {
            resize: vertical;
            min-height: 120px;
        }
        
        .form-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
            margin-top: 20px;
        }
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
        }
        
        .btn-warning {
            background-color: #ffc107;
            color: #212529;
        }
        
        .btn-warning:hover {
            background-color: #e0a800;
        }
        
        .btn-info {
            background-color: #17a2b8;
            color: white;
        }
        
        .btn-info:hover {
            background-color: #138496;
        }
        
        .btn-success {
            background-color: #28a745;
            color: white;
        }
        
        .btn-success:hover {
            background-color: #218838;
        }
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
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
        
        .delete-form {
            display: inline;
        }
        
        @media (max-width: 768px) {
            .report-detail-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
            
            .report-status-card {
                flex-direction: column;
                text-align: center;
                gap: 15px;
            }
            
            .info-grid {
                grid-template-columns: 1fr;
            }
            
            .form-actions {
                flex-direction: column;
            }
            
            .btn {
                width: 100%;
                justify-content: center;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="report-detail-container">
        <div class="report-detail-header">
            <h1>신고 상세 정보</h1>
            <div class="btn-group">
                <a href="${pageContext.request.contextPath}/report/list" class="btn btn-secondary">
                    <i class="fas fa-arrow-left"></i> 목록으로
                </a>
                <c:if test="${sessionScope.user.role == 'ADMIN' && report.isPending()}">
                    <form action="${pageContext.request.contextPath}/report/delete/${report.reportId}" 
                          method="post" class="delete-form" 
                          onsubmit="return confirm('정말로 이 신고를 삭제하시겠습니까?');">
                        <button type="submit" class="btn btn-danger">
                            <i class="fas fa-trash"></i> 삭제
                        </button>
                    </form>
                </c:if>
            </div>
        </div>
        
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
        
        <div class="report-status-card">
            <div class="status-info">
                <h2>신고 번호 #${report.reportId}</h2>
                <p>접수일시: <fmt:formatDate value="${report.createdAt}" pattern="yyyy년 MM월 dd일 HH:mm"/></p>
            </div>
            <span class="status-badge-large status-${report.statusColor}">
                ${report.statusText}
            </span>
        </div>
        
        <div class="report-detail-content">
            <div class="report-section">
                <h3 class="section-title">신고 정보</h3>
                <div class="info-grid">
                    <div class="info-item">
                        <div class="info-label">신고자</div>
                        <div class="info-value">${report.reporterName}</div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">신고 대상</div>
                        <div class="info-value">
                            <span class="type-badge type-${report.reportedType.toLowerCase()}">
                                ${report.reportedTypeText}
                            </span>
                            ${report.reportedTitle}
                            <c:if test="${not empty report.reportedUserName}">
                                (${report.reportedUserName})
                            </c:if>
                        </div>
                    </div>
                    <div class="info-item">
                        <div class="info-label">신고 카테고리</div>
                        <div class="info-value">
                            <span class="category-badge">
                                ${report.categoryText}
                            </span>
                        </div>
                    </div>
                    <c:if test="${report.isResolved()}">
                        <div class="info-item">
                            <div class="info-label">처리일시</div>
                            <div class="info-value">
                                <fmt:formatDate value="${report.resolvedAt}" pattern="yyyy년 MM월 dd일 HH:mm"/>
                            </div>
                        </div>
                    </c:if>
                </div>
                
                <div class="reason-box">
                    <h4 style="margin: 0 0 10px 0; color: #333;">신고 사유</h4>
                    <p style="margin: 0; color: #555; line-height: 1.6;">
                        ${report.reason}
                    </p>
                </div>
            </div>
            
            <c:if test="${not empty report.adminNote}">
                <div class="report-section">
                    <h3 class="section-title">관리자 처리 내용</h3>
                    <div class="reason-box">
                        <p style="margin: 0; color: #555; line-height: 1.6;">
                            ${report.adminNote}
                        </p>
                    </div>
                </div>
            </c:if>
            
            <c:if test="${sessionScope.user.role == 'ADMIN' && !report.isResolved()}">
                <div class="report-section admin-section">
                    <h3 class="section-title">신고 처리</h3>
                    <form class="admin-form" action="${pageContext.request.contextPath}/report/process/${report.reportId}" method="post">
                        <div class="form-group">
                            <label class="form-label" for="status">처리 상태</label>
                            <select class="form-control" id="status" name="status" required>
                                <option value="">상태를 선택하세요</option>
                                <option value="PROCESSING" ${report.status == 'PROCESSING' ? 'selected' : ''}>처리중</option>
                                <option value="RESOLVED">처리완료</option>
                                <option value="REJECTED">반려</option>
                            </select>
                        </div>
                        
                        <div class="form-group">
                            <label class="form-label" for="adminNote">처리 내용</label>
                            <textarea class="form-control" 
                                      id="adminNote" 
                                      name="adminNote" 
                                      placeholder="처리 내용을 입력하세요.">${report.adminNote}</textarea>
                        </div>
                        
                        <div class="form-actions">
                            <c:choose>
                                <c:when test="${report.isPending()}">
                                    <button type="submit" name="status" value="PROCESSING" class="btn btn-info">
                                        <i class="fas fa-clock"></i> 처리중으로 변경
                                    </button>
                                </c:when>
                                <c:otherwise>
                                    <button type="submit" class="btn btn-primary">
                                        <i class="fas fa-check"></i> 처리 완료
                                    </button>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </form>
                </div>
            </c:if>
        </div>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // 알림 메시지 자동 숨기기
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                alert.style.display = 'none';
            });
        }, 5000);
    </script>
</body>
</html>