<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>삼양 농업 플랫폼 - 회원가입</title>
    <script src="https://code.jquery.com/jquery-3.7.1.min.js"></script>
    <style>
        body {
            font-family: Arial, sans-serif;
            max-width: 500px;
            margin: 50px auto;
            padding: 20px;
            background-color: #f5f5f5;
        }
        .register-container {
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
        input[type="text"], input[type="email"], input[type="password"], input[type="tel"], textarea {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
            font-size: 14px;
        }
        textarea {
            height: 80px;
            resize: vertical;
        }
        .btn-register {
            width: 100%;
            padding: 12px;
            background-color: #28a745;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn-register:hover {
            background-color: #218838;
        }
        .btn-cancel {
            width: 100%;
            padding: 12px;
            background-color: #6c757d;
            color: white;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            cursor: pointer;
            margin-top: 10px;
        }
        .btn-cancel:hover {
            background-color: #545b62;
        }
        .error-message {
            color: #dc3545;
            margin: 10px 0;
            text-align: center;
        }
        .success-message {
            color: #28a745;
            margin: 10px 0;  
            text-align: center;
        }
        h2 {
            text-align: center;
            color: #333;
            margin-bottom: 30px;
        }
        .required {
            color: #dc3545;
        }
        .checkbox-group {
            display: flex;
            align-items: center;
            margin-bottom: 15px;
        }
        .checkbox-group input[type="checkbox"] {
            width: auto;
            margin-right: 8px;
        }
    </style>
</head>
<body>
    <div class="register-container">
        <h2>🌾 회원가입</h2>
        
        <!-- 에러/성공 메시지 표시 -->
        <c:if test="${not empty errorMessage}">
            <div class="error-message">${errorMessage}</div>
        </c:if>
        <c:if test="${not empty successMessage}">
            <div class="success-message">${successMessage}</div>
        </c:if>
        
        <form id="registerForm" method="post" action="/user/register">
            <div class="form-group">
                <label for="name">이름 <span class="required">*</span>:</label>
                <input type="text" id="name" name="name" value="${user.name}" placeholder="이름을 입력하세요" required>
            </div>
            
            <div class="form-group">
                <label for="email">이메일 <span class="required">*</span>:</label>
                <input type="email" id="email" name="email" value="${user.email}" placeholder="example@email.com" required>
                <span id="emailCheck" style="font-size: 12px; margin-top: 5px; display: block;"></span>
            </div>
            
            <div class="form-group">
                <label for="password">비밀번호 <span class="required">*</span>:</label>
                <input type="password" id="password" name="password" placeholder="8자리 이상 입력" required>
            </div>
            
            <div class="form-group">
                <label for="passwordConfirm">비밀번호 확인 <span class="required">*</span>:</label>
                <input type="password" id="passwordConfirm" name="passwordConfirm" placeholder="비밀번호를 다시 입력하세요" required>
                <span id="passwordMatch" style="font-size: 12px; margin-top: 5px; display: block;"></span>
            </div>
            
            <div class="form-group">
                <label for="phoneNumber">전화번호:</label>
                <input type="tel" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" placeholder="010-0000-0000">
            </div>
            
            <div class="form-group">
                <label for="address">주소:</label>
                <textarea id="address" name="address" placeholder="주소를 입력하세요">${user.address}</textarea>
            </div>
            
            <div class="checkbox-group">
                <input type="checkbox" id="isAdmin" name="isAdmin" value="true" ${user.isAdmin ? 'checked' : ''}>
                <label for="isAdmin">관리자 권한 요청</label>
            </div>
            
            <button type="submit" class="btn-register" id="registerBtn">회원가입</button>
            <button type="button" class="btn-cancel" onclick="location.href='/user/login'">취소</button>
        </form>
    </div>

    <script type="text/javascript">
        $(document).ready(function() {
            // 이메일 중복 체크
            $("#email").blur(function() {
                var email = $(this).val().trim();
                if (email !== "") {
                    $.get("/user/check-email", {이메일: email}, function(data) {
                        if (data === "exists") {
                            $("#emailCheck").text("이미 사용 중인 이메일입니다.").css("color", "#dc3545");
                        } else if (data === "available") {
                            $("#emailCheck").text("사용 가능한 이메일입니다.").css("color", "#28a745");
                        } else {
                            $("#emailCheck").text("이메일 확인 중 오류가 발생했습니다.").css("color", "#dc3545");
                        }
                    });
                }
            });
            
            // 비밀번호 확인 체크
            $("#passwordConfirm").keyup(function() {
                var password = $("#password").val();
                var passwordConfirm = $(this).val();
                
                if (passwordConfirm !== "") {
                    if (password === passwordConfirm) {
                        $("#passwordMatch").text("비밀번호가 일치합니다.").css("color", "#28a745");
                    } else {
                        $("#passwordMatch").text("비밀번호가 일치하지 않습니다.").css("color", "#dc3545");
                    }
                } else {
                    $("#passwordMatch").text("");
                }
            });
            
            // 폼 제출 전 유효성 검사
            $("#registerForm").submit(function(e) {
                var name = $("#name").val().trim();
                var email = $("#email").val().trim();
                var password = $("#password").val();
                var passwordConfirm = $("#passwordConfirm").val();
                
                if (name === "") {
                    alert("이름을 입력해주세요.");
                    $("#name").focus();
                    e.preventDefault();
                    return false;
                }
                
                if (email === "") {
                    alert("이메일을 입력해주세요.");
                    $("#email").focus();
                    e.preventDefault();
                    return false;
                }
                
                var emailPattern = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
                if (!emailPattern.test(email)) {
                    alert("올바른 이메일 형식을 입력해주세요.");
                    $("#email").focus();
                    e.preventDefault();
                    return false;
                }
                
                if (password.length < 8) {
                    alert("비밀번호는 8자리 이상 입력해주세요.");
                    $("#password").focus();
                    e.preventDefault();
                    return false;
                }
                
                if (password !== passwordConfirm) {
                    alert("비밀번호가 일치하지 않습니다.");
                    $("#passwordConfirm").focus();
                    e.preventDefault();
                    return false;
                }
                
                return true;
            });
        });
    </script>
</body>
</html>