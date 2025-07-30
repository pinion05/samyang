<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        .footer {
            background-color: #2c3e50;
            color: white;
            margin-top: 50px;
            position: relative;
            width: 100%;
        }
        
        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
            padding: 40px 20px 30px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 40px;
        }
        
        .footer-section h3 {
            color: #4CAF50;
            margin-bottom: 15px;
            font-size: 18px;
        }
        
        .footer-section p,
        .footer-section ul {
            margin: 0;
            line-height: 1.8;
            font-size: 14px;
        }
        
        .footer-section ul {
            list-style: none;
            padding: 0;
        }
        
        .footer-section ul li {
            margin-bottom: 8px;
        }
        
        .footer-section a {
            color: #ecf0f1;
            text-decoration: none;
            transition: color 0.3s;
        }
        
        .footer-section a:hover {
            color: #4CAF50;
        }
        
        .company-info {
            line-height: 1.8;
        }
        
        .company-info i {
            color: #4CAF50;
            margin-right: 8px;
            width: 16px;
        }
        
        .social-links {
            display: flex;
            gap: 15px;
            margin-top: 15px;
        }
        
        .social-links a {
            display: flex;
            align-items: center;
            justify-content: center;
            width: 40px;
            height: 40px;
            background-color: #34495e;
            border-radius: 50%;
            transition: all 0.3s;
        }
        
        .social-links a:hover {
            background-color: #4CAF50;
            transform: translateY(-3px);
        }
        
        .social-links i {
            font-size: 18px;
        }
        
        .footer-bottom {
            background-color: #1a252f;
            padding: 20px;
            text-align: center;
            border-top: 1px solid #34495e;
        }
        
        .footer-bottom p {
            margin: 0;
            font-size: 14px;
            color: #95a5a6;
        }
        
        .footer-bottom .links {
            display: flex;
            justify-content: center;
            gap: 20px;
            margin-top: 10px;
            flex-wrap: wrap;
        }
        
        .footer-bottom a {
            color: #95a5a6;
            text-decoration: none;
            font-size: 13px;
            transition: color 0.3s;
        }
        
        .footer-bottom a:hover {
            color: #4CAF50;
        }
        
        .footer-logo {
            display: flex;
            align-items: center;
            gap: 10px;
            margin-bottom: 15px;
        }
        
        .footer-logo i {
            font-size: 32px;
            color: #4CAF50;
        }
        
        .footer-logo span {
            font-size: 24px;
            font-weight: bold;
        }
        
        .newsletter-form {
            margin-top: 15px;
        }
        
        .newsletter-form p {
            margin-bottom: 10px;
        }
        
        .newsletter-input-group {
            display: flex;
            gap: 10px;
        }
        
        .newsletter-input {
            flex: 1;
            padding: 10px;
            border: none;
            border-radius: 5px;
            font-size: 14px;
        }
        
        .newsletter-btn {
            padding: 10px 20px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-weight: bold;
            transition: background-color 0.3s;
        }
        
        .newsletter-btn:hover {
            background-color: #45a049;
        }
        
        @media (max-width: 768px) {
            .footer-content {
                grid-template-columns: 1fr;
                gap: 30px;
                padding: 30px 20px 20px;
            }
            
            .social-links {
                justify-content: center;
            }
            
            .newsletter-input-group {
                flex-direction: column;
            }
            
            .newsletter-btn {
                width: 100%;
            }
            
            .footer-bottom .links {
                flex-direction: column;
                gap: 10px;
            }
        }
    </style>
</head>
<body>
    <footer class="footer">
        <div class="footer-content">
            <div class="footer-section">
                <div class="footer-logo">
                    <i class="fas fa-seedling"></i>
                    <span>삼양농업플랫폼</span>
                </div>
                <p>지속 가능한 농업의 미래를 만들어갑니다.</p>
                <p>스마트 농업 기술과 데이터 기반 솔루션으로 농업인의 성공을 지원합니다.</p>
                <div class="social-links">
                    <a href="#" title="Facebook">
                        <i class="fab fa-facebook-f"></i>
                    </a>
                    <a href="#" title="Instagram">
                        <i class="fab fa-instagram"></i>
                    </a>
                    <a href="#" title="YouTube">
                        <i class="fab fa-youtube"></i>
                    </a>
                    <a href="#" title="Blog">
                        <i class="fas fa-blog"></i>
                    </a>
                </div>
            </div>
            
            <div class="footer-section">
                <h3>빠른 메뉴</h3>
                <ul>
                    <li><a href="${pageContext.request.contextPath}/crop/list">작물관리</a></li>
                    <li><a href="${pageContext.request.contextPath}/diary/list">농업일지</a></li>
                    <li><a href="${pageContext.request.contextPath}/review/list">리뷰</a></li>
                    <li><a href="${pageContext.request.contextPath}/payment/list">결제수단</a></li>
                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                        <li><a href="${pageContext.request.contextPath}/report/list">신고관리</a></li>
                    </c:if>
                </ul>
            </div>
            
            <div class="footer-section">
                <h3>고객지원</h3>
                <ul>
                    <li><a href="#">이용가이드</a></li>
                    <li><a href="#">자주 묻는 질문</a></li>
                    <li><a href="#">1:1 문의</a></li>
                    <li><a href="#">공지사항</a></li>
                    <li><a href="#">서비스 약관</a></li>
                    <li><a href="#">개인정보처리방침</a></li>
                </ul>
            </div>
            
            <div class="footer-section">
                <h3>회사 정보</h3>
                <div class="company-info">
                    <p><i class="fas fa-building"></i> 주식회사 삼양농업플랫폼</p>
                    <p><i class="fas fa-map-marker-alt"></i> 서울특별시 강남구 농업로 123</p>
                    <p><i class="fas fa-phone"></i> 대표전화: 1588-0000</p>
                    <p><i class="fas fa-fax"></i> 팩스: 02-1234-5678</p>
                    <p><i class="fas fa-envelope"></i> 이메일: info@samyang.com</p>
                    <p><i class="fas fa-id-card"></i> 사업자번호: 123-45-67890</p>
                </div>
            </div>
            
            <div class="footer-section">
                <h3>뉴스레터</h3>
                <div class="newsletter-form">
                    <p>농업 소식과 유용한 정보를 받아보세요!</p>
                    <form action="#" method="post" onsubmit="subscribeNewsletter(event)">
                        <div class="newsletter-input-group">
                            <input type="email" 
                                   class="newsletter-input" 
                                   placeholder="이메일 주소 입력"
                                   required>
                            <button type="submit" class="newsletter-btn">
                                구독하기
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        
        <div class="footer-bottom">
            <p>&copy; 2024 삼양농업플랫폼. All rights reserved.</p>
            <div class="links">
                <a href="#">이용약관</a>
                <a href="#">개인정보처리방침</a>
                <a href="#">이메일무단수집거부</a>
                <a href="#">사이트맵</a>
            </div>
        </div>
    </footer>
    
    <script>
        function subscribeNewsletter(event) {
            event.preventDefault();
            alert('뉴스레터 구독이 완료되었습니다!');
            event.target.reset();
        }
    </script>
</body>
</html>