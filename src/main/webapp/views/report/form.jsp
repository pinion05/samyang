<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>신고하기 - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .report-form-container {
            max-width: 700px;
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
        
        .report-info-box {
            background-color: #e3f2fd;
            padding: 20px;
            border-radius: 10px;
            margin-bottom: 30px;
            border-left: 4px solid #2196F3;
        }
        
        .report-info-box h3 {
            margin: 0 0 10px 0;
            color: #1565c0;
            font-size: 18px;
        }
        
        .report-info-box p {
            margin: 0;
            color: #333;
            line-height: 1.6;
        }
        
        .report-form {
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
        
        textarea.form-control {
            resize: vertical;
            min-height: 150px;
        }
        
        .type-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .type-badge {
            display: inline-block;
            padding: 6px 12px;
            border-radius: 15px;
            font-size: 14px;
            font-weight: 500;
            margin-right: 10px;
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
        
        .category-options {
            display: grid;
            grid-template-columns: 1fr;
            gap: 10px;
        }
        
        .category-option {
            position: relative;
        }
        
        .category-option input[type="radio"] {
            position: absolute;
            opacity: 0;
        }
        
        .category-label {
            display: block;
            padding: 15px 20px;
            border: 2px solid #ddd;
            border-radius: 8px;
            cursor: pointer;
            transition: all 0.3s;
            background-color: white;
        }
        
        .category-label:hover {
            border-color: #4CAF50;
            background-color: #f8f9fa;
        }
        
        .category-option input[type="radio"]:checked + .category-label {
            border-color: #4CAF50;
            background-color: #e8f5e9;
        }
        
        .category-title {
            font-weight: 600;
            color: #333;
            margin-bottom: 5px;
        }
        
        .category-desc {
            font-size: 14px;
            color: #666;
        }
        
        .help-text {
            font-size: 14px;
            color: #6c757d;
            margin-top: 5px;
        }
        
        .char-counter {
            text-align: right;
            font-size: 14px;
            color: #6c757d;
            margin-top: 5px;
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
            background-color: #dc3545;
            color: white;
        }
        
        .btn-primary:hover {
            background-color: #c82333;
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
        
        @media (max-width: 768px) {
            .report-form-container {
                padding: 15px;
            }
            
            .report-form {
                padding: 20px;
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
    
    <div class="report-form-container">
        <div class="form-header">
            <h1>신고하기</h1>
            <p class="form-description">
                부적절한 콘텐츠나 사용자를 신고해주세요.
            </p>
        </div>
        
        <div class="report-info-box">
            <h3><i class="fas fa-info-circle"></i> 신고 안내</h3>
            <p>
                허위 신고나 악의적인 신고는 제재의 대상이 될 수 있습니다.<br>
                신고 내용은 관리자가 검토 후 적절한 조치를 취할 예정입니다.
            </p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <form class="report-form" action="${pageContext.request.contextPath}/report/save" method="post">
            <c:choose>
                <c:when test="${not empty report.reportedType && not empty report.reportedId}">
                    <div class="type-info">
                        <span class="type-badge type-${report.reportedType.toLowerCase()}">
                            <c:choose>
                                <c:when test="${report.reportedType == 'USER'}">사용자</c:when>
                                <c:when test="${report.reportedType == 'CROP'}">작물</c:when>
                                <c:when test="${report.reportedType == 'DIARY'}">농업일지</c:when>
                                <c:when test="${report.reportedType == 'REVIEW'}">리뷰</c:when>
                                <c:when test="${report.reportedType == 'COMMENT'}">댓글</c:when>
                            </c:choose>
                        </span>
                        신고
                    </div>
                    <input type="hidden" name="reportedId" value="${report.reportedId}">
                    <input type="hidden" name="reportedType" value="${report.reportedType}">
                </c:when>
                <c:otherwise>
                    <div class="form-group">
                        <label class="form-label" for="reportedType">
                            신고 대상 유형 <span class="required">*</span>
                        </label>
                        <select class="form-control" id="reportedType" name="reportedType" required>
                            <option value="">선택하세요</option>
                            <option value="USER">사용자</option>
                            <option value="CROP">작물</option>
                            <option value="DIARY">농업일지</option>
                            <option value="REVIEW">리뷰</option>
                            <option value="COMMENT">댓글</option>
                        </select>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="reportedId">
                            대상 ID <span class="required">*</span>
                        </label>
                        <input type="number" 
                               class="form-control" 
                               id="reportedId" 
                               name="reportedId" 
                               placeholder="신고할 대상의 ID를 입력하세요" 
                               required>
                    </div>
                </c:otherwise>
            </c:choose>
            
            <div class="form-group">
                <label class="form-label">
                    신고 카테고리 <span class="required">*</span>
                </label>
                <div class="category-options">
                    <div class="category-option">
                        <input type="radio" id="category-spam" name="category" value="SPAM" required>
                        <label for="category-spam" class="category-label">
                            <div class="category-title">스팸/광고</div>
                            <div class="category-desc">상업적 광고, 도배, 스팸 콘텐츠</div>
                        </label>
                    </div>
                    
                    <div class="category-option">
                        <input type="radio" id="category-abuse" name="category" value="ABUSE" required>
                        <label for="category-abuse" class="category-label">
                            <div class="category-title">욕설/비방</div>
                            <div class="category-desc">욕설, 비방, 명예훼손 등 부적절한 언어 사용</div>
                        </label>
                    </div>
                    
                    <div class="category-option">
                        <input type="radio" id="category-fraud" name="category" value="FRAUD" required>
                        <label for="category-fraud" class="category-label">
                            <div class="category-title">사기/허위정보</div>
                            <div class="category-desc">거짓 정보, 사기성 콘텐츠, 허위 거래</div>
                        </label>
                    </div>
                    
                    <div class="category-option">
                        <input type="radio" id="category-inappropriate" name="category" value="INAPPROPRIATE" required>
                        <label for="category-inappropriate" class="category-label">
                            <div class="category-title">부적절한 콘텐츠</div>
                            <div class="category-desc">음란물, 폭력적 콘텐츠, 불법 정보</div>
                        </label>
                    </div>
                    
                    <div class="category-option">
                        <input type="radio" id="category-other" name="category" value="OTHER" required>
                        <label for="category-other" class="category-label">
                            <div class="category-title">기타</div>
                            <div class="category-desc">위 카테고리에 해당하지 않는 기타 신고</div>
                        </label>
                    </div>
                </div>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="reason">
                    신고 사유 <span class="required">*</span>
                </label>
                <textarea class="form-control" 
                          id="reason" 
                          name="reason" 
                          placeholder="신고 사유를 구체적으로 작성해주세요." 
                          maxlength="1000"
                          onkeyup="updateCharCount(this)"
                          required></textarea>
                <div class="char-counter">
                    <span id="charCount">0</span> / 1000
                </div>
                <p class="help-text">
                    신고 사유를 구체적으로 작성해주시면 더 빠른 처리가 가능합니다.
                </p>
            </div>
            
            <div class="form-actions">
                <a href="javascript:history.back();" class="btn btn-secondary">
                    취소
                </a>
                <button type="submit" class="btn btn-primary">
                    신고하기
                </button>
            </div>
        </form>
    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
    <script>
        // 문자수 카운터 업데이트
        function updateCharCount(textarea) {
            const charCount = textarea.value.length;
            document.getElementById('charCount').textContent = charCount;
        }
        
        // 페이지 로드 시 초기 문자수 표시
        window.onload = function() {
            const reasonTextarea = document.getElementById('reason');
            if (reasonTextarea) {
                updateCharCount(reasonTextarea);
            }
        };
    </script>
</body>
</html>