<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>결제수단 관리 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .payment-container {
            max-width: 1200px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .payment-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .payment-header h1 {
            color: #2c3e50;
            margin: 0;
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
        
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #45a049;
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
        
        .filter-section {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .filter-form {
            display: flex;
            gap: 15px;
            align-items: center;
            flex-wrap: wrap;
        }
        
        .filter-form select,
        .filter-form button {
            padding: 8px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .filter-form button {
            background-color: #6c757d;
            color: white;
            cursor: pointer;
        }
        
        .filter-form button:hover {
            background-color: #5a6268;
        }
        
        .payment-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            margin-top: 20px;
        }
        
        .payment-card {
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: transform 0.3s, box-shadow 0.3s;
        }
        
        .payment-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 5px 20px rgba(0,0,0,0.15);
        }
        
        .card-header {
            padding: 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
        }
        
        .card-type {
            font-size: 14px;
            opacity: 0.9;
            margin-bottom: 5px;
        }
        
        .card-number {
            font-size: 20px;
            font-weight: 600;
            letter-spacing: 2px;
            margin-bottom: 10px;
        }
        
        .card-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .card-holder {
            font-size: 16px;
        }
        
        .card-expiry {
            font-size: 14px;
            opacity: 0.9;
        }
        
        .card-body {
            padding: 20px;
        }
        
        .card-details {
            margin-bottom: 20px;
        }
        
        .detail-item {
            display: flex;
            justify-content: space-between;
            margin-bottom: 10px;
            padding-bottom: 10px;
            border-bottom: 1px solid #eee;
        }
        
        .detail-label {
            color: #666;
            font-size: 14px;
        }
        
        .detail-value {
            color: #333;
            font-weight: 500;
        }
        
        .card-actions {
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .no-payment {
            text-align: center;
            padding: 60px 20px;
            color: #666;
        }
        
        .no-payment i {
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
        
        .type-visa {
            background: linear-gradient(135deg, #1e3c72 0%, #2a5298 100%);
        }
        
        .type-mastercard {
            background: linear-gradient(135deg, #eb3349 0%, #f45c43 100%);
        }
        
        .type-amex {
            background: linear-gradient(135deg, #007991 0%, #78ffd6 100%);
        }
        
        .type-discover {
            background: linear-gradient(135deg, #f2994a 0%, #f2c94c 100%);
        }
        
        @media (max-width: 768px) {
            .payment-header {
                flex-direction: column;
                align-items: flex-start;
                gap: 15px;
            }
            
            .payment-grid {
                grid-template-columns: 1fr;
            }
            
            .card-actions {
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
    
    <div class="payment-container">
        <div class="payment-header">
            <h1>결제수단 관리</h1>
            <a href="${pageContext.request.contextPath}/payment/form" class="btn btn-primary">
                <i class="fas fa-plus"></i> 새 결제수단 등록
            </a>
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
        
        <div class="filter-section">
            <form class="filter-form" method="get" action="${pageContext.request.contextPath}/payment/list">
                <label for="paymentType">카드 종류:</label>
                <select name="paymentType" id="paymentType">
                    <option value="">전체</option>
                    <option value="VISA" ${selectedType == 'VISA' ? 'selected' : ''}>VISA</option>
                    <option value="MASTERCARD" ${selectedType == 'MASTERCARD' ? 'selected' : ''}>MasterCard</option>
                    <option value="AMEX" ${selectedType == 'AMEX' ? 'selected' : ''}>American Express</option>
                    <option value="DISCOVER" ${selectedType == 'DISCOVER' ? 'selected' : ''}>Discover</option>
                </select>
                <button type="submit">필터 적용</button>
                <c:if test="${not empty selectedType}">
                    <a href="${pageContext.request.contextPath}/payment/list" class="btn btn-secondary">필터 초기화</a>
                </c:if>
            </form>
        </div>
        
        <c:choose>
            <c:when test="${empty paymentList}">
                <div class="no-payment">
                    <i class="fas fa-credit-card"></i>
                    <h3>등록된 결제수단이 없습니다</h3>
                    <p>새로운 결제수단을 등록해주세요.</p>
                    <a href="${pageContext.request.contextPath}/payment/form" class="btn btn-primary">
                        결제수단 등록하기
                    </a>
                </div>
            </c:when>
            <c:otherwise>
                <div class="payment-grid">
                    <c:forEach var="payment" items="${paymentList}">
                        <div class="payment-card">
                            <div class="card-header type-${payment.paymentType.toLowerCase()}">
                                <div class="card-type">${payment.paymentType}</div>
                                <div class="card-number">${payment.maskedCardNumber}</div>
                                <div class="card-info">
                                    <div class="card-holder">${payment.cardHolderName}</div>
                                    <div class="card-expiry">${payment.expiryDate}</div>
                                </div>
                            </div>
                            
                            <div class="card-body">
                                <div class="card-details">
                                    <div class="detail-item">
                                        <span class="detail-label">청구지 주소</span>
                                        <span class="detail-value">${payment.billingAddress}</span>
                                    </div>
                                    <c:if test="${sessionScope.user.role == 'ADMIN'}">
                                        <div class="detail-item">
                                            <span class="detail-label">사용자</span>
                                            <span class="detail-value">
                                                <c:choose>
                                                    <c:when test="${not empty payment.userName}">
                                                        ${payment.userName}
                                                    </c:when>
                                                    <c:otherwise>
                                                        사용자 ${payment.userId}
                                                    </c:otherwise>
                                                </c:choose>
                                            </span>
                                        </div>
                                    </c:if>
                                    <div class="detail-item">
                                        <span class="detail-label">등록일</span>
                                        <span class="detail-value">${payment.createdAt}</span>
                                    </div>
                                </div>
                                
                                <div class="card-actions">
                                    <a href="${pageContext.request.contextPath}/payment/form?paymentId=${payment.paymentMethodId}" 
                                       class="btn btn-secondary">
                                        <i class="fas fa-edit"></i> 수정
                                    </a>
                                    <form action="${pageContext.request.contextPath}/payment/delete/${payment.paymentMethodId}" 
                                          method="post" style="display: inline;"
                                          onsubmit="return confirm('정말로 이 결제수단을 삭제하시겠습니까?');">
                                        <button type="submit" class="btn btn-danger">
                                            <i class="fas fa-trash"></i> 삭제
                                        </button>
                                    </form>
                                </div>
                            </div>
                        </div>
                    </c:forEach>
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