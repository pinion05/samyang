<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? '댓글 수정' : '댓글 작성'} - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .comment-form-container {
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
        
        .comment-form {
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
            min-height: 120px;
        }
        
        .related-info {
            background-color: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .related-info h3 {
            margin: 0 0 10px 0;
            font-size: 16px;
            color: #333;
        }
        
        .related-type-badge {
            display: inline-block;
            padding: 4px 10px;
            border-radius: 15px;
            font-size: 12px;
            font-weight: 600;
            margin-right: 10px;
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
        
        @media (max-width: 768px) {
            .comment-form-container {
                padding: 15px;
            }
            
            .comment-form {
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
    
    <div class="comment-form-container">
        <div class="form-header">
            <h1>${isEdit ? '댓글 수정' : '댓글 작성'}</h1>
            <p class="form-description">
                ${isEdit ? '작성하신 댓글을 수정합니다.' : '의견을 남겨주세요.'}
            </p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <form class="comment-form" action="${pageContext.request.contextPath}/comment/save" method="post">
            <c:if test="${isEdit}">
                <input type="hidden" name="commentId" value="${comment.commentId}">
                <input type="hidden" name="relatedId" value="${comment.relatedId}">
                <input type="hidden" name="relatedType" value="${comment.relatedType}">
            </c:if>
            
            <c:choose>
                <c:when test="${not empty comment.relatedType && not empty comment.relatedId}">
                    <div class="related-info">
                        <h3>
                            <span class="related-type-badge type-${comment.relatedType.toLowerCase()}">
                                <c:choose>
                                    <c:when test="${comment.relatedType == 'CROP'}">작물</c:when>
                                    <c:when test="${comment.relatedType == 'DIARY'}">농업일지</c:when>
                                    <c:when test="${comment.relatedType == 'REVIEW'}">리뷰</c:when>
                                </c:choose>
                            </span>
                            댓글 작성
                        </h3>
                    </div>
                    <c:if test="${not isEdit}">
                        <input type="hidden" name="relatedId" value="${comment.relatedId}">
                        <input type="hidden" name="relatedType" value="${comment.relatedType}">
                    </c:if>
                </c:when>
                <c:when test="${not isEdit}">
                    <div class="form-group">
                        <label class="form-label" for="relatedType">
                            댓글 대상 <span class="required">*</span>
                        </label>
                        <select class="form-control" id="relatedType" name="relatedType" required>
                            <option value="">선택하세요</option>
                            <option value="CROP">작물</option>
                            <option value="DIARY">농업일지</option>
                            <option value="REVIEW">리뷰</option>
                        </select>
                        <p class="help-text">댓글을 작성할 대상을 선택해주세요.</p>
                    </div>
                    
                    <div class="form-group">
                        <label class="form-label" for="relatedId">
                            대상 ID <span class="required">*</span>
                        </label>
                        <input type="number" 
                               class="form-control" 
                               id="relatedId" 
                               name="relatedId" 
                               placeholder="대상 ID를 입력하세요" 
                               required>
                        <p class="help-text">댓글을 작성할 대상의 ID를 입력해주세요.</p>
                    </div>
                </c:when>
            </c:choose>
            
            <div class="form-group">
                <label class="form-label" for="content">
                    댓글 내용 <span class="required">*</span>
                </label>
                <textarea class="form-control" 
                          id="content" 
                          name="content" 
                          placeholder="댓글을 입력하세요." 
                          maxlength="500"
                          onkeyup="updateCharCount(this)"
                          required>${comment.content}</textarea>
                <div class="char-counter">
                    <span id="charCount">0</span> / 500
                </div>
                <p class="help-text">
                    건전한 댓글 문화를 위해 욕설이나 비방은 삼가해주세요.
                </p>
            </div>
            
            <div class="form-actions">
                <a href="javascript:history.back();" class="btn btn-secondary">
                    취소
                </a>
                <button type="submit" class="btn btn-primary">
                    ${isEdit ? '수정하기' : '작성하기'}
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
            const contentTextarea = document.getElementById('content');
            if (contentTextarea) {
                updateCharCount(contentTextarea);
            }
        };
    </script>
</body>
</html>