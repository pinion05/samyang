<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>삼양 농업 플랫폼 - 로그인</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 400px;
            margin: 100px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .login-container {
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        .form-group {
            margin-bottom: 15px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #333;
        }
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        .btn-login {
            width: 100%;
            padding: 12px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
        }
        .btn-login:hover {
            background-color: #218838;
        }
        .error-message {
            color: #dc3545;
            margin-top: 10px;
            text-align: center;
        }
        .success-message {
            color: #28a745;
            margin-top: 10px;  
            text-align: center;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
    </style>
</head>
<body>
    <div class="login-container">
        <h2>🌾 삼양 농업 플랫폼</h2>
        
        <!-- 에러/성공 메시지 표시 -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        
        <form id="frm" name="frm">
            <div class="form-group">
                <label for="username">사용자명:</label>
                <input type="text" id="username" name="username" placeholder="사용자명을 입력하세요" required>
            </div>
            
            <div class="form-group">
                <label for="password">비밀번호:</label>
                <input type="password" id="password" name="password" placeholder="비밀번호를 입력하세요" required>
            </div>
            
            <button type="button" class="btn-login" id="loginBtn">로그인</button>
        </form>
        
        <div style="text-align: center; margin-top: 20px;">
            <a href="/user/register" style="color: #28a745;">회원가입</a> | 
            <a href="/" style="color: #28a745;">메인으로</a>
        </div>
    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            // 로그인 버튼 클릭 이벤트
            $("#loginBtn").click(function() {
                // 입력값 검증
                var username = $("#username").val().trim();
                var password = $("#password").val().trim();
                
                if (username === "") {
                    alert("사용자명을 입력해주세요.");
                    $("#username").focus();
                    return false;
                }
                
                if (password === "") {
                    alert("비밀번호를 입력해주세요.");
                    $("#password").focus();
                    return false;
                }
                
                // 폼 제출
                $("#frm").attr("method", "post");
                $("#frm").attr("action", "/user/login");
                $("#frm").submit();
            });
            
            // Enter 키 처리
            $("#frm input").keypress(function(e) {
                if (e.which === 13) { // Enter key
                    $("#loginBtn").click();
                }
            });
        });
    </script>
</body>
</html>