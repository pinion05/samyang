<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? '결제수단 수정' : '결제수단 등록'} - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .payment-form-container {
            max-width: 600px;
            margin: 0 auto;
            padding: 20px;
        }
        
        .form-header {
            margin-bottom: 30px;
            padding-bottom: 20px;
            border-bottom: 2px solid #4CAF50;
        }
        
        .form-header h1 {
            color: #2c3e50;
            margin: 0;
            font-size: 28px;
        }
        
        .form-description {
            color: #666;
            margin-top: 10px;
        }
        
        .payment-form {
            background-color: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
        }
        
        .form-group {
            margin-bottom: 25px;
        }
        
        .form-label {
            display: block;
            font-weight: 600;
            color: #333;
            margin-bottom: 8px;
        }
        
        .required {
            color: #dc3545;
        }
        
        .form-control {
            width: 100%;
            padding: 12px;
            border: 1px solid #ddd;
            border-radius: 5px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        .form-control:focus {
            outline: none;
            border-color: #4CAF50;
            box-shadow: 0 0 0 0.2rem rgba(76, 175, 80, 0.25);
        }
        
        select.form-control {
            height: 48px;
        }
        
        .card-preview {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            border-radius: 10px;
            margin-bottom: 30px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.2);
        }
        
        .card-preview-type {
            font-size: 14px;
            opacity: 0.9;
            margin-bottom: 5px;
        }
        
        .card-preview-number {
            font-size: 24px;
            font-weight: 600;
            letter-spacing: 3px;
            margin-bottom: 20px;
            font-family: monospace;
        }
        
        .card-preview-info {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .card-preview-holder {
            font-size: 16px;
            text-transform: uppercase;
        }
        
        .card-preview-expiry {
            font-size: 14px;
        }
        
        .form-row {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 20px;
        }
        
        .form-actions {
            margin-top: 40px;
            display: flex;
            gap: 10px;
            justify-content: flex-end;
        }
        
        .btn {
            padding: 12px 30px;
            border: none;
            border-radius: 5px;
            font-size: 16px;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s;
            text-decoration: none;
            display: inline-block;
            text-align: center;
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
        
        .alert {
            padding: 15px;
            margin-bottom: 20px;
            border-radius: 5px;
            font-weight: 500;
        }
        
        .alert-error {
            background-color: #f8d7da;
            color: #721c24;
            border: 1px solid #f5c6cb;
        }
        
        .help-text {
            font-size: 14px;
            color: #6c757d;
            margin-top: 5px;
        }
        
        .security-notice {
            background-color: #e8f5e9;
            border-left: 4px solid #4CAF50;
            padding: 15px;
            margin-bottom: 30px;
            font-size: 14px;
        }
        
        .security-notice i {
            color: #4CAF50;
            margin-right: 8px;
        }
        
        @media (max-width: 768px) {
            .payment-form-container {
                padding: 15px;
            }
            
            .payment-form {
                padding: 20px;
            }
            
            .form-row {
                grid-template-columns: 1fr;
            }
            
            .form-actions {
                flex-direction: column-reverse;
            }
            
            .btn {
                width: 100%;
            }
        }
    </style>
</head>
<body>
    <jsp:include page="../common/header.jsp" />
    
    <div class="payment-form-container">
        <div class="form-header">
            <h1>${isEdit ? '결제수단 수정' : '결제수단 등록'}</h1>
            <p class="form-description">
                ${isEdit ? '등록된 결제수단 정보를 수정합니다.' : '새로운 결제수단을 안전하게 등록합니다.'}
            </p>
        </div>
        
        <div class="security-notice">
            <i class="fas fa-shield-alt"></i>
            결제 정보는 안전하게 암호화되어 저장됩니다.
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <div class="card-preview" id="cardPreview">
            <div class="card-preview-type" id="previewType">CARD TYPE</div>
            <div class="card-preview-number" id="previewNumber">**** **** **** ****</div>
            <div class="card-preview-info">
                <div class="card-preview-holder" id="previewHolder">CARD HOLDER NAME</div>
                <div class="card-preview-expiry" id="previewExpiry">MM/YY</div>
            </div>
        </div>
        
        <form class="payment-form" action="${pageContext.request.contextPath}/payment/save" method="post">
            <c:if test="${isEdit}">
                <input type="hidden" name="paymentMethodId" value="${payment.paymentMethodId}">
            </c:if>
            
            <div class="form-group">
                <label class="form-label" for="paymentType">
                    카드 종류 <span class="required">*</span>
                </label>
                <select class="form-control" id="paymentType" name="paymentType" required onchange="updateCardPreview()">
                    <option value="">카드 종류를 선택하세요</option>
                    <option value="VISA" ${payment.paymentType == 'VISA' ? 'selected' : ''}>VISA</option>
                    <option value="MASTERCARD" ${payment.paymentType == 'MASTERCARD' ? 'selected' : ''}>MasterCard</option>
                    <option value="AMEX" ${payment.paymentType == 'AMEX' ? 'selected' : ''}>American Express</option>
                    <option value="DISCOVER" ${payment.paymentType == 'DISCOVER' ? 'selected' : ''}>Discover</option>
                </select>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="cardNumber">
                    카드 번호 <span class="required">*</span>
                </label>
                <input type="text" 
                       class="form-control" 
                       id="cardNumber" 
                       name="cardNumber" 
                       value="${payment.cardNumber}" 
                       placeholder="1234 5678 9012 3456"
                       maxlength="19"
                       onkeyup="formatCardNumber(this); updateCardPreview();"
                       required>
                <p class="help-text">카드 번호는 숫자만 입력해주세요.</p>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="cardHolderName">
                    카드 소유자명 <span class="required">*</span>
                </label>
                <input type="text" 
                       class="form-control" 
                       id="cardHolderName" 
                       name="cardHolderName" 
                       value="${payment.cardHolderName}" 
                       placeholder="홍길동"
                       onkeyup="updateCardPreview()"
                       required>
                <p class="help-text">카드에 표시된 이름을 입력해주세요.</p>
            </div>
            
            <div class="form-row">
                <div class="form-group">
                    <label class="form-label" for="expiryDate">
                        유효기간 <span class="required">*</span>
                    </label>
                    <input type="text" 
                           class="form-control" 
                           id="expiryDate" 
                           name="expiryDate" 
                           value="${payment.expiryDate}" 
                           placeholder="MM/YY"
                           maxlength="5"
                           onkeyup="formatExpiryDate(this); updateCardPreview();"
                           required>
                    <p class="help-text">MM/YY 형식으로 입력</p>
                </div>
                
                <div class="form-group">
                    <label class="form-label" for="cvv">
                        CVV <span class="required">*</span>
                    </label>
                    <input type="text" 
                           class="form-control" 
                           id="cvv" 
                           name="cvv" 
                           value="${payment.cvv}" 
                           placeholder="123"
                           maxlength="4"
                           onkeypress="return isNumber(event)"
                           required>
                    <p class="help-text">카드 뒷면의 3-4자리 숫자</p>
                </div>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="billingAddress">
                    청구지 주소 <span class="required">*</span>
                </label>
                <input type="text" 
                       class="form-control" 
                       id="billingAddress" 
                       name="billingAddress" 
                       value="${payment.billingAddress}" 
                       placeholder="서울시 강남구 테헤란로 123"
                       required>
            </div>
            
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/payment/list" class="btn btn-secondary">
                    취소
                </a>
                <button type="submit" class="btn btn-primary">
                    ${isEdit ? '수정하기' : '등록하기'}
                </button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // 카드 번호 포맷팅
        function formatCardNumber(input) {
            let value = input.value.replace(/\s/g, '');
            let formattedValue = '';
            
            for (let i = 0; i < value.length; i++) {
                if (i > 0 && i % 4 === 0) {
                    formattedValue += ' ';
                }
                formattedValue += value[i];
            }
            
            input.value = formattedValue;
        }
        
        // 유효기간 포맷팅
        function formatExpiryDate(input) {
            let value = input.value.replace(/\D/g, '');
            
            if (value.length >= 2) {
                value = value.substring(0, 2) + '/' + value.substring(2, 4);
            }
            
            input.value = value;
        }
        
        // 숫자만 입력 가능
        function isNumber(evt) {
            evt = (evt) ? evt : window.event;
            let charCode = (evt.which) ? evt.which : evt.keyCode;
            if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                return false;
            }
            return true;
        }
        
        // 카드 미리보기 업데이트
        function updateCardPreview() {
            const type = document.getElementById('paymentType').value || 'CARD TYPE';
            const number = document.getElementById('cardNumber').value || '**** **** **** ****';
            const holder = document.getElementById('cardHolderName').value.toUpperCase() || 'CARD HOLDER NAME';
            const expiry = document.getElementById('expiryDate').value || 'MM/YY';
            
            document.getElementById('previewType').textContent = type;
            document.getElementById('previewNumber').textContent = number || '**** **** **** ****';
            document.getElementById('previewHolder').textContent = holder;
            document.getElementById('previewExpiry').textContent = expiry;
            
            // 카드 종류에 따른 배경색 변경
            const cardPreview = document.getElementById('cardPreview');
            cardPreview.className = 'card-preview';
            
            switch(type) {
                case 'VISA':
                    cardPreview.style.background = 'linear-gradient(135deg, #1e3c72 0%, #2a5298 100%)';
                    break;
                case 'MASTERCARD':
                    cardPreview.style.background = 'linear-gradient(135deg, #eb3349 0%, #f45c43 100%)';
                    break;
                case 'AMEX':
                    cardPreview.style.background = 'linear-gradient(135deg, #007991 0%, #78ffd6 100%)';
                    break;
                case 'DISCOVER':
                    cardPreview.style.background = 'linear-gradient(135deg, #f2994a 0%, #f2c94c 100%)';
                    break;
                default:
                    cardPreview.style.background = 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)';
            }
        }
        
        // 페이지 로드 시 초기화
        window.onload = function() {
            updateCardPreview();
        };
    </script>
</body>
</html>