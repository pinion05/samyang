<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="오류 - 삼양 농업 플랫폼" />
<jsp:include page="common/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-8">
        <div class="card">
            <div class="card-header bg-danger text-white text-center">
                <h4><i class="fas fa-exclamation-triangle"></i> 오류가 발생했습니다</h4>
            </div>
            <div class="card-body text-center">
                <div class="mb-4">
                    <i class="fas fa-exclamation-circle text-danger" style="font-size: 4rem;"></i>
                </div>
                
                <c:choose>
                    <c:when test="${not empty errorMessage}">
                        <h5 class="text-danger mb-3">${errorMessage}</h5>
                    </c:when>
                    <c:otherwise>
                        <h5 class="text-danger mb-3">예상치 못한 오류가 발생했습니다.</h5>
                    </c:otherwise>
                </c:choose>
                
                <p class="text-muted">
                    불편을 드려 죄송합니다. 문제가 지속되면 관리자에게 문의해주세요.
                </p>
                
                <div class="mt-4">
                    <a href="/" class="btn btn-success me-2">
                        <i class="fas fa-home"></i> 홈으로 이동
                    </a>
                    <button onclick="history.back()" class="btn btn-outline-secondary">
                        <i class="fas fa-arrow-left"></i> 이전 페이지
                    </button>
                </div>
            </div>
        </div>
    </div>
</div>

<jsp:include page="common/footer.jsp" />