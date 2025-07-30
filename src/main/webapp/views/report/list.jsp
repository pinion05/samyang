<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신고 관리 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .report-container {
            max-width: 1400px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .report-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .report-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .statistics-cards {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 20px;
            margin-bottom: 30px;
        }
        
        .stat-card {
            background-color: white;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            text-align: center;
        }
        
        .stat-card h3 {
            color: #666;
            font-size: 14px;
            margin: 0 0 10px 0;
            text-transform: uppercase;
        }
        
        .stat-number {
            font-size: 36px;
            font-weight: bold;
            margin: 0;
        }
        
        .stat-pending { color: #ffc107; }
        .stat-processing { color: #17a2b8; }
        .stat-resolved { color: #28a745; }
        .stat-rejected { color: #dc3545; }
        
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
        }
        
        .filter-tabs {
            display: flex;
            gap: 10px;
            flex-wrap: wrap;
            margin-bottom: 15px;
        }
        
        .filter-tab {
            padding: 8px 16px;
            border: 1px solid #ddd;
            background-color: white;
            border-radius: 20px;
            text-decoration: none;
            color: #333;
            font-size: 14px;
            transition: all 0.3s;
        }
        
        .filter-tab:hover {
            background-color: #e9ecef;
        }
        
        .filter-tab.active {
            background-color: #4CAF50;
            color: white;
            border-color: #4CAF50;
        }
        
        .table-responsive {
            overflow-x: auto;
        }
        
        .report-table {
            width: 100%;
            background-color: white;
            border-radius: 10px;
            overflow: hidden;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .report-table th,
        .report-table td {
            padding: 15px;
            text-align: left;
            border-bottom: 1px solid #eee;
        }
        
        .report-table th {
            background-color: #f8f9fa;
            font-weight: 600;
            color: #333;
            position: sticky;
            top: 0;
        }
        
        .report-table tr:hover {
            background-color: #f8f9fa;
        }
        
        .status-badge {
            display: inline-block;
            padding: 5px 12px;
            border-radius: 15px;
            font-size: 12px;
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
        
        .category-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
            background-color: #e9ecef;
            color: #495057;
        }
        
        .type-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 12px;
            font-size: 12px;
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
        
        .action-btn {
            padding: 6px 12px;
            border-radius: 4px;
            text-decoration: none;
            font-size: 14px;
            transition: all 0.3s;
        }
        
        .btn-view {
            background-color: #007bff;
            color: white;
        }
        
        .btn-view:hover {
            background-color: #0056b3;
        }
        
        .no-reports {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .no-reports i {
            font-size: 48px;
            color: #ddd;
            margin-bottom: 20px;
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
        
        @media (max-width: 768px) {
            .statistics-cards {
                grid-template-columns: 1fr 1fr;
            }
            
            .filter-tabs {
                justify-content: center;
            }
            
            .report-table {
                font-size: 14px;
            }
            
            .report-table th,
            .report-table td {
                padding: 10px;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="report-container">
        <div class="report-header">
            <h1>
                <c:choose>
                    <c:when test="${isMyReports}">내 신고 내역</c:when>
                    <c:otherwise>신고 관리</c:otherwise>
                </c:choose>
            </h1>
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
        
        <c:if test="${not isMyReports && sessionScope.user.role == 'ADMIN'}">
            <div class="statistics-cards">
                <div class="stat-card">
                    <h3>대기중</h3>
                    <p class="stat-number stat-pending">${statistics.statusStats.pending}</p>
                </div>
                <div class="stat-card">
                    <h3>처리중</h3>
                    <p class="stat-number stat-processing">${statistics.statusStats.processing}</p>
                </div>
                <div class="stat-card">
                    <h3>처리완료</h3>
                    <p class="stat-number stat-resolved">${statistics.statusStats.resolved}</p>
                </div>
                <div class="stat-card">
                    <h3>반려</h3>
                    <p class="stat-number stat-rejected">${statistics.statusStats.rejected}</p>
                </div>
            </div>
            
            <div class="filter-section">
                <div class="filter-tabs">
                    <a href="${pageContext.request.contextPath}/report/list" 
                       class="filter-tab ${empty selectedStatus && empty selectedCategory && empty selectedType ? 'active' : ''}">
                        전체 (${statistics.totalCount})
                    </a>
                    
                    <span style="margin: 0 10px; color: #999;">|</span>
                    
                    <a href="${pageContext.request.contextPath}/report/list?status=PENDING" 
                       class="filter-tab ${selectedStatus == 'PENDING' ? 'active' : ''}">
                        대기중
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?status=PROCESSING" 
                       class="filter-tab ${selectedStatus == 'PROCESSING' ? 'active' : ''}">
                        처리중
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?status=RESOLVED" 
                       class="filter-tab ${selectedStatus == 'RESOLVED' ? 'active' : ''}">
                        처리완료
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?status=REJECTED" 
                       class="filter-tab ${selectedStatus == 'REJECTED' ? 'active' : ''}">
                        반려
                    </a>
                    
                    <span style="margin: 0 10px; color: #999;">|</span>
                    
                    <a href="${pageContext.request.contextPath}/report/list?type=USER" 
                       class="filter-tab ${selectedType == 'USER' ? 'active' : ''}">
                        사용자
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?type=CROP" 
                       class="filter-tab ${selectedType == 'CROP' ? 'active' : ''}">
                        작물
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?type=DIARY" 
                       class="filter-tab ${selectedType == 'DIARY' ? 'active' : ''}">
                        농업일지
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?type=REVIEW" 
                       class="filter-tab ${selectedType == 'REVIEW' ? 'active' : ''}">
                        리뷰
                    </a>
                    <a href="${pageContext.request.contextPath}/report/list?type=COMMENT" 
                       class="filter-tab ${selectedType == 'COMMENT' ? 'active' : ''}">
                        댓글
                    </a>
                </div>
            </div>
        </c:if>
        
        <c:choose>
            <c:when test="${empty reportList}">
                <div class="no-reports">
                    <i class="fas fa-flag"></i>
                    <h3>신고 내역이 없습니다</h3>
                    <p>현재 처리할 신고가 없습니다.</p>
                </div>
            </c:when>
            <c:otherwise>
                <div class="table-responsive">
                    <table class="report-table">
                        <thead>
                            <tr>
                                <th>번호</th>
                                <th>신고일</th>
                                <th>신고자</th>
                                <th>대상</th>
                                <th>유형</th>
                                <th>카테고리</th>
                                <th>상태</th>
                                <th>작업</th>
                            </tr>
                        </thead>
                        <tbody>
                            <c:forEach var="report" items="${reportList}">
                                <tr>
                                    <td>${report.reportId}</td>
                                    <td>
                                        <fmt:formatDate value="${report.createdAt}" pattern="yyyy-MM-dd HH:mm"/>
                                    </td>
                                    <td>${report.reporterName}</td>
                                    <td>
                                        ${report.reportedTitle}
                                        <c:if test="${not empty report.reportedUserName}">
                                            <br><small>(${report.reportedUserName})</small>
                                        </c:if>
                                    </td>
                                    <td>
                                        <span class="type-badge type-${report.reportedType.toLowerCase()}">
                                            ${report.reportedTypeText}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="category-badge">
                                            ${report.categoryText}
                                        </span>
                                    </td>
                                    <td>
                                        <span class="status-badge status-${report.statusColor}">
                                            ${report.statusText}
                                        </span>
                                    </td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/report/detail/${report.reportId}" 
                                           class="action-btn btn-view">
                                            상세보기
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>
                </div>
            </c:otherwise>
        </c:choose>
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