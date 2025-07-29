<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:set var="pageTitle" value="삼양 농업 플랫폼 - 대시보드" />
<jsp:include page="common/header.jsp" />

<!-- 대시보드 요약 카드 -->
<div class="row mb-4">
    <div class="col-md-3">
        <div class="card bg-primary text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4><i class="fas fa-users"></i></h4>
                        <h5>전체 사용자</h5>
                        <h2>${totalUsers}명</h2>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="/user/list" class="text-white text-decoration-none">
                    <small>자세히 보기 <i class="fas fa-arrow-right"></i></small>
                </a>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card bg-success text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4><i class="fas fa-leaf"></i></h4>
                        <h5>전체 작물</h5>
                        <h2>${totalCrops}개</h2>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="/crop/list" class="text-white text-decoration-none">
                    <small>자세히 보기 <i class="fas fa-arrow-right"></i></small>
                </a>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card bg-info text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4><i class="fas fa-book"></i></h4>
                        <h5>농사일지</h5>
                        <h2>${totalDiaries}개</h2>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="/diary/list" class="text-white text-decoration-none">
                    <small>자세히 보기 <i class="fas fa-arrow-right"></i></small>
                </a>
            </div>
        </div>
    </div>
    
    <div class="col-md-3">
        <div class="card bg-warning text-white">
            <div class="card-body">
                <div class="d-flex justify-content-between">
                    <div>
                        <h4><i class="fas fa-apple-alt"></i></h4>
                        <h5>수확 가능</h5>
                        <h2>${harvestableCrops.size()}개</h2>
                    </div>
                </div>
            </div>
            <div class="card-footer">
                <a href="/crop/harvestable" class="text-white text-decoration-none">
                    <small>자세히 보기 <i class="fas fa-arrow-right"></i></small>
                </a>
            </div>
        </div>
    </div>
</div>

<!-- 최근 활동 -->
<div class="row">
    <!-- 최근 등록된 사용자 -->
    <div class="col-md-6 mb-4">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">
                    <i class="fas fa-user-plus"></i> 최근 등록된 사용자
                </h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty recentUsers}">
                        <div class="table-responsive">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>이름</th>
                                        <th>이메일</th>
                                        <th>가입일</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="user" items="${recentUsers}">
                                        <tr>
                                            <td>
                                                <a href="/user/detail/${user.사용자ID}" class="text-decoration-none">
                                                    ${user.이름}
                                                </a>
                                            </td>
                                            <td>${user.이메일}</td>
                                            <td>
                                                ${user.가입일시.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm"))}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted text-center">등록된 사용자가 없습니다.</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-footer">
                <a href="/user/list" class="btn btn-sm btn-outline-primary">전체 보기</a>
                <a href="/user/register" class="btn btn-sm btn-success">사용자 등록</a>
            </div>
        </div>
    </div>
    
    <!-- 최근 등록된 작물 -->
    <div class="col-md-6 mb-4">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">
                    <i class="fas fa-seedling"></i> 최근 등록된 작물
                </h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty recentCrops}">
                        <div class="table-responsive">
                            <table class="table table-sm">
                                <thead>
                                    <tr>
                                        <th>작물명</th>
                                        <th>사용자</th>
                                        <th>상태</th>
                                        <th>심은날짜</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="crop" items="${recentCrops}">
                                        <tr>
                                            <td>
                                                <a href="/crop/detail/${crop.작물ID}" class="text-decoration-none">
                                                    ${crop.작물명}
                                                </a>
                                            </td>
                                            <td>${crop.사용자이름}</td>
                                            <td>
                                                <span class="badge bg-secondary">${crop.상태}</span>
                                            </td>
                                            <td>
                                                ${crop.심은날짜.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd"))}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted text-center">등록된 작물이 없습니다.</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-footer">
                <a href="/crop/list" class="btn btn-sm btn-outline-success">전체 보기</a>
                <a href="/crop/register" class="btn btn-sm btn-success">작물 등록</a>
            </div>
        </div>
    </div>
</div>

<!-- 최근 농사일지 -->
<div class="row">
    <div class="col-12">
        <div class="card">
            <div class="card-header">
                <h5 class="card-title mb-0">
                    <i class="fas fa-book-open"></i> 최근 농사일지
                </h5>
            </div>
            <div class="card-body">
                <c:choose>
                    <c:when test="${not empty recentDiaries}">
                        <div class="table-responsive">
                            <table class="table">
                                <thead>
                                    <tr>
                                        <th>날짜</th>
                                        <th>사용자</th>
                                        <th>작물</th>
                                        <th>활동유형</th>
                                        <th>내용</th>
                                        <th>작성일시</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <c:forEach var="diary" items="${recentDiaries}">
                                        <tr>
                                            <td>
                                                ${diary.날짜.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))}
                                            </td>
                                            <td>${diary.사용자이름}</td>
                                            <td>
                                                <c:choose>
                                                    <c:when test="${not empty diary.작물명}">
                                                        <a href="/crop/detail/${diary.작물ID}" class="text-decoration-none">
                                                            ${diary.작물명}
                                                        </a>
                                                    </c:when>
                                                    <c:otherwise>
                                                        <span class="text-muted">-</span>
                                                    </c:otherwise>
                                                </c:choose>
                                            </td>
                                            <td>
                                                <span class="badge bg-info">${diary.활동_유형}</span>
                                            </td>
                                            <td>
                                                <a href="/diary/detail/${diary.농사일지ID}" class="text-decoration-none">
                                                    <c:choose>
                                                        <c:when test="${diary.내용.length() > 30}">
                                                            ${diary.내용.substring(0, 30)}...
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${diary.내용}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </a>
                                            </td>
                                            <td>
                                                ${diary.작성일시.format(java.time.format.DateTimeFormatter.ofPattern("MM-dd HH:mm"))}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <p class="text-muted text-center">작성된 농사일지가 없습니다.</p>
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="card-footer">
                <a href="/diary/list" class="btn btn-sm btn-outline-info">전체 보기</a>
                <a href="/diary/register" class="btn btn-sm btn-success">일지 작성</a>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />