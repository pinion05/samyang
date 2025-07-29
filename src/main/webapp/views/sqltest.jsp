<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="pageTitle" value="SQL 테스트 및 디버깅 페이지" />
<jsp:include page="common/header.jsp" />

<style>
    .test-result {
        margin-bottom: 20px;
        border: 1px solid #ddd;
        border-radius: 8px;
    }
    
    .test-result.success {
        border-color: #28a745;
        background-color: #f8fff9;
    }
    
    .test-result.failed {
        border-color: #dc3545;
        background-color: #fff8f8;
    }
    
    .test-header {
        padding: 15px;
        border-bottom: 1px solid #ddd;
        font-weight: bold;
    }
    
    .test-header.success {
        background-color: #d4edda;
        color: #155724;
    }
    
    .test-header.failed {
        background-color: #f8d7da;
        color: #721c24;
    }
    
    .test-body {
        padding: 15px;
    }
    
    .error-log {
        background-color: #f8f9fa;
        border: 1px solid #dee2e6;
        border-radius: 4px;
        padding: 10px;
        margin: 10px 0;
        font-family: 'Courier New', monospace;
        font-size: 12px;
        max-height: 300px;
        overflow-y: auto;
    }
    
    .stack-trace {
        background-color: #2d3748;
        color: #e2e8f0;
        border-radius: 4px;
        padding: 15px;
        margin: 10px 0;
        font-family: 'Courier New', monospace;
        font-size: 11px;
        max-height: 400px;
        overflow-y: auto;
        white-space: pre-wrap;
    }
    
    .operation-list {
        list-style: none;
        padding: 0;
    }
    
    .operation-list li {
        padding: 5px 0;
        border-bottom: 1px dotted #ddd;
    }
    
    .operation-list li:last-child {
        border-bottom: none;
    }
    
    .test-controls {
        background-color: #f8f9fa;
        padding: 20px;
        border-radius: 8px;
        margin-bottom: 20px;
    }
    
    .btn-test {
        margin: 5px;
    }
    
    .timestamp {
        font-size: 0.9em;
        color: #6c757d;
    }
    
    .status-badge {
        display: inline-block;
        padding: 4px 8px;
        border-radius: 4px;
        font-size: 12px;
        font-weight: bold;
        text-transform: uppercase;
    }
    
    .status-badge.success {
        background-color: #28a745;
        color: white;
    }
    
    .status-badge.failed {
        background-color: #dc3545;
        color: white;
    }
    
    .error-summary {
        background-color: #fff3cd;
        border: 1px solid #ffeaa7;
        border-radius: 4px;
        padding: 15px;
        margin-bottom: 20px;
    }
</style>

<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header bg-info text-white">
                <h4 class="mb-0">
                    <i class="fas fa-database"></i> SQL 테스트 및 디버깅 페이지
                </h4>
                <small>데이터베이스 연결, CRUD 작업 테스트 및 상세 에러 분석</small>
            </div>
            
            <div class="card-body">
                <!-- 테스트 컨트롤 섹션 -->
                <div class="test-controls">
                    <h5><i class="fas fa-play-circle"></i> 테스트 실행</h5>
                    <div class="row">
                        <div class="col-md-6">
                            <a href="/sqltest" class="btn btn-primary btn-test">
                                <i class="fas fa-sync"></i> 전체 테스트 다시 실행
                            </a>
                        </div>
                        <div class="col-md-6">
                            <div class="btn-group" role="group">
                                <form method="post" action="/sqltest/run" style="display: inline;">
                                    <input type="hidden" name="testType" value="connection">
                                    <button type="submit" class="btn btn-outline-secondary btn-test">
                                        <i class="fas fa-link"></i> 연결 테스트
                                    </button>
                                </form>
                                <form method="post" action="/sqltest/run" style="display: inline;">
                                    <input type="hidden" name="testType" value="user">
                                    <button type="submit" class="btn btn-outline-secondary btn-test">
                                        <i class="fas fa-user"></i> 사용자 테스트
                                    </button>
                                </form>
                                <form method="post" action="/sqltest/run" style="display: inline;">
                                    <input type="hidden" name="testType" value="crop">
                                    <button type="submit" class="btn btn-outline-secondary btn-test">
                                        <i class="fas fa-leaf"></i> 작물 테스트
                                    </button>
                                </form>
                                <form method="post" action="/sqltest/run" style="display: inline;">
                                    <input type="hidden" name="testType" value="diary">
                                    <button type="submit" class="btn btn-outline-secondary btn-test">
                                        <i class="fas fa-book"></i> 일지 테스트
                                    </button>
                                </form>
                            </div>
                        </div>
                    </div>
                    
                    <div class="row mt-3">
                        <div class="col-12">
                            <p class="timestamp">
                                <i class="fas fa-clock"></i> 
                                <strong>테스트 실행 시간:</strong> 
                                <fmt:formatDate value="${testTime}" pattern="yyyy-MM-dd HH:mm:ss" />
                            </p>
                            <c:if test="${not empty singleTest}">
                                <p class="text-info">
                                    <i class="fas fa-info-circle"></i> 
                                    <strong>실행된 테스트:</strong> ${singleTest}
                                </p>
                            </c:if>
                        </div>
                    </div>
                </div>
                
                <!-- 에러 로그 요약 -->
                <c:if test="${not empty errorLogs}">
                    <div class="error-summary">
                        <h5><i class="fas fa-exclamation-triangle text-warning"></i> 에러 로그 요약</h5>
                        <div class="error-log">
                            <c:forEach var="errorLog" items="${errorLogs}">
                                <div class="text-danger">
                                    <i class="fas fa-times-circle"></i> ${errorLog}
                                </div>
                            </c:forEach>
                        </div>
                    </div>
                </c:if>
                
                <!-- 테스트 결과 표시 -->
                <c:if test="${not empty testResults}">
                    <h5><i class="fas fa-list-check"></i> 테스트 결과</h5>
                    <c:forEach var="testResult" items="${testResults}">
                        <div class="test-result ${testResult.status == 'SUCCESS' ? 'success' : 'failed'}">
                            <!-- 테스트 헤더 -->
                            <div class="test-header ${testResult.status == 'SUCCESS' ? 'success' : 'failed'}">
                                <div class="row">
                                    <div class="col-md-8">
                                        <h6 class="mb-0">
                                            <c:choose>
                                                <c:when test="${testResult.status == 'SUCCESS'}">
                                                    <i class="fas fa-check-circle"></i>
                                                </c:when>
                                                <c:otherwise>
                                                    <i class="fas fa-times-circle"></i>
                                                </c:otherwise>
                                            </c:choose>
                                            ${testResult.testName}
                                        </h6>
                                    </div>
                                    <div class="col-md-4 text-end">
                                        <span class="status-badge ${testResult.status == 'SUCCESS' ? 'success' : 'failed'}">
                                            ${testResult.status}
                                        </span>
                                    </div>
                                </div>
                            </div>
                            
                            <!-- 테스트 내용 -->
                            <div class="test-body">
                                <div class="row">
                                    <div class="col-md-6">
                                        <p><strong>메시지:</strong> ${testResult.message}</p>
                                        <p class="timestamp">
                                            <strong>시작 시간:</strong> 
                                            <fmt:formatDate value="${testResult.startTime}" pattern="HH:mm:ss.SSS" />
                                        </p>
                                        <p class="timestamp">
                                            <strong>종료 시간:</strong> 
                                            <fmt:formatDate value="${testResult.endTime}" pattern="HH:mm:ss.SSS" />
                                        </p>
                                    </div>
                                    <div class="col-md-6">
                                        <!-- 성공 시 상세 정보 -->
                                        <c:if test="${not empty testResult.details}">
                                            <p><strong>상세 정보:</strong> ${testResult.details}</p>
                                        </c:if>
                                        
                                        <!-- 실패 시 에러 정보 -->
                                        <c:if test="${not empty testResult.error}">
                                            <div class="alert alert-danger">
                                                <strong>에러:</strong> ${testResult.error}
                                            </div>
                                        </c:if>
                                    </div>
                                </div>
                                
                                <!-- 작업 내역 -->
                                <c:if test="${not empty testResult.operations}">
                                    <h6><i class="fas fa-list"></i> 수행된 작업</h6>
                                    <ul class="operation-list">
                                        <c:forEach var="operation" items="${testResult.operations}">
                                            <li>${operation}</li>
                                        </c:forEach>
                                    </ul>
                                </c:if>
                                
                                <!-- 스택 트레이스 -->
                                <c:if test="${not empty testResult.stackTrace}">
                                    <h6><i class="fas fa-bug"></i> 상세 스택 트레이스</h6>
                                    <div class="stack-trace">
${testResult.stackTrace}
                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </c:forEach>
                </c:if>
                
                <!-- 도움말 섹션 -->
                <div class="mt-4">
                    <div class="card">
                        <div class="card-header bg-light">
                            <h6><i class="fas fa-question-circle"></i> 도움말</h6>
                        </div>
                        <div class="card-body">
                            <div class="row">
                                <div class="col-md-6">
                                    <h6>테스트 항목</h6>
                                    <ul>
                                        <li><strong>연결 테스트:</strong> 데이터베이스 연결 상태 확인</li>
                                        <li><strong>사용자 테스트:</strong> 사용자 테이블 CRUD 작업</li>
                                        <li><strong>작물 테스트:</strong> 작물 테이블 CRUD 작업</li>
                                        <li><strong>일지 테스트:</strong> 농사일지 테이블 조회 작업</li>
                                    </ul>
                                </div>
                                <div class="col-md-6">
                                    <h6>상태 코드</h6>
                                    <ul>
                                        <li><span class="status-badge success">SUCCESS</span> 테스트 성공</li>
                                        <li><span class="status-badge failed">FAILED</span> 테스트 실패</li>
                                    </ul>
                                    
                                    <h6 class="mt-3">주의사항</h6>
                                    <p><small class="text-muted">
                                        이 페이지는 개발/테스트 목적으로만 사용하세요. 
                                        프로덕션 환경에서는 접근을 제한해야 합니다.
                                    </small></p>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />