<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:set var="pageTitle" value="로그인 - 삼양 농업 플랫폼" />
<jsp:include page="../common/header.jsp" />

<div class="row justify-content-center">
    <div class="col-md-6 col-lg-4">
        <div class="card">
            <div class="card-header text-center">
                <h4><i class="fas fa-sign-in-alt"></i> 로그인</h4>
            </div>
            <div class="card-body">
                <form id="loginForm" action="/user/login" method="post">
                    <div class="mb-3">
                        <label for="이메일" class="form-label">이메일</label>
                        <input type="email" class="form-control" id="이메일" name="이메일" required>
                    </div>
                    
                    <div class="mb-3">
                        <label for="비밀번호" class="form-label">비밀번호</label>
                        <input type="password" class="form-control" id="비밀번호" name="비밀번호" required>
                    </div>
                    
                    <div class="d-grid gap-2">
                        <button type="submit" class="btn btn-success btn-lg">
                            <i class="fas fa-sign-in-alt"></i> 로그인
                        </button>
                    </div>
                </form>
            </div>
            <div class="card-footer text-center">
                <p class="mb-0">
                    계정이 없으신가요? 
                    <a href="/user/register" class="text-decoration-none">회원가입</a>
                </p>
            </div>
        </div>
    </div>
</div>

<script>
$(document).ready(function() {
    $('#loginForm').on('submit', function(e) {
        const 이메일 = $('#이메일').val().trim();
        const 비밀번호 = $('#비밀번호').val().trim();
        
        if (!이메일) {
            alert('이메일을 입력해주세요.');
            $('#이메일').focus();
            e.preventDefault();
            return false;
        }
        
        if (!비밀번호) {
            alert('비밀번호를 입력해주세요.');
            $('#비밀번호').focus();
            e.preventDefault();
            return false;
        }
        
        return true;
    });
});
</script>

<jsp:include page="../common/footer.jsp" />