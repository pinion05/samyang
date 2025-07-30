<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${isEdit ? '영농일지 수정' : '영농일지 작성'} - 삼양농업플랫폼</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .diary-form-container {
            max-width: 800px;
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
        
        .diary-form {
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
        
        .file-upload-group {
            position: relative;
        }
        
        .file-input-wrapper {
            position: relative;
            overflow: hidden;
            display: inline-block;
            cursor: pointer;
            width: 100%;
        }
        
        .file-input-button {
            display: block;
            padding: 12px;
            background-color: #f8f9fa;
            border: 2px dashed #dee2e6;
            border-radius: 5px;
            text-align: center;
            color: #6c757d;
            font-weight: 500;
            transition: all 0.3s;
        }
        
        .file-input-button:hover {
            background-color: #e9ecef;
            border-color: #adb5bd;
        }
        
        .file-input-wrapper input[type="file"] {
            position: absolute;
            left: -9999px;
        }
        
        .file-preview {
            margin-top: 15px;
            text-align: center;
        }
        
        .file-preview img {
            max-width: 100%;
            max-height: 300px;
            border-radius: 5px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.15);
        }
        
        .current-image {
            margin-top: 15px;
            padding: 15px;
            background-color: #f8f9fa;
            border-radius: 5px;
        }
        
        .current-image-label {
            font-weight: 600;
            color: #495057;
            margin-bottom: 10px;
            display: block;
        }
        
        .current-image img {
            max-width: 100%;
            max-height: 200px;
            border-radius: 5px;
        }
        
        .file-info {
            margin-top: 10px;
            font-size: 14px;
            color: #6c757d;
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
        
        @media (max-width: 768px) {
            .diary-form-container {
                padding: 15px;
            }
            
            .diary-form {
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
    
    <div class="diary-form-container">
        <div class="form-header">
            <h1>${isEdit ? '영농일지 수정' : '영농일지 작성'}</h1>
            <p class="form-description">
                ${isEdit ? '영농일지 내용을 수정합니다.' : '오늘의 농업 활동을 기록해보세요.'}
            </p>
        </div>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-error">
                ${errorMessage}
            </div>
        </c:if>
        
        <form class="diary-form" action="${pageContext.request.contextPath}/diary/save" method="post" enctype="multipart/form-data">
            <c:if test="${isEdit}">
                <input type="hidden" name="farmingDiaryId" value="${diary.farmingDiaryId}">
            </c:if>
            
            <div class="form-group">
                <label class="form-label" for="date">
                    작업 날짜 <span class="required">*</span>
                </label>
                <input type="date" 
                       class="form-control" 
                       id="date" 
                       name="date" 
                       value="${diary.date}" 
                       required>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="activityType">
                    활동 유형 <span class="required">*</span>
                </label>
                <select class="form-control" id="activityType" name="activityType" required>
                    <option value="">활동 유형을 선택하세요</option>
                    <option value="파종" ${diary.activityType == '파종' ? 'selected' : ''}>파종</option>
                    <option value="관수" ${diary.activityType == '관수' ? 'selected' : ''}>관수</option>
                    <option value="시비" ${diary.activityType == '시비' ? 'selected' : ''}>시비</option>
                    <option value="방제" ${diary.activityType == '방제' ? 'selected' : ''}>방제</option>
                    <option value="수확" ${diary.activityType == '수확' ? 'selected' : ''}>수확</option>
                    <option value="기타" ${diary.activityType == '기타' ? 'selected' : ''}>기타</option>
                </select>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="content">
                    작업 내용 <span class="required">*</span>
                </label>
                <textarea class="form-control" 
                          id="content" 
                          name="content" 
                          placeholder="오늘 수행한 농업 활동을 자세히 기록해주세요." 
                          required>${diary.content}</textarea>
                <p class="help-text">날씨, 작물 상태, 사용한 자재, 작업 시간 등을 포함하면 더 좋습니다.</p>
            </div>
            
            <div class="form-group">
                <label class="form-label" for="photoFile">
                    사진 첨부
                </label>
                
                <c:if test="${isEdit && not empty diary.photoUrl}">
                    <div class="current-image">
                        <span class="current-image-label">현재 사진</span>
                        <img src="${pageContext.request.contextPath}${diary.photoUrl}" alt="현재 영농일지 사진">
                        <p class="help-text">새로운 사진을 업로드하면 기존 사진이 대체됩니다.</p>
                    </div>
                </c:if>
                
                <div class="file-upload-group">
                    <div class="file-input-wrapper">
                        <label for="photoFile" class="file-input-button">
                            <i class="fas fa-camera"></i> 사진 선택
                        </label>
                        <input type="file" 
                               id="photoFile" 
                               name="photoFile" 
                               accept="image/*"
                               onchange="previewImage(this)">
                    </div>
                    <div class="file-preview" id="preview" style="display: none;">
                        <img id="preview-image" src="" alt="미리보기">
                    </div>
                    <p class="file-info">
                        * 이미지 파일만 업로드 가능합니다 (JPG, PNG, GIF)
                        <br>* 최대 파일 크기: 5MB
                    </p>
                </div>
            </div>
            
            <div class="form-actions">
                <a href="${pageContext.request.contextPath}/diary/list" class="btn btn-secondary">
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
        // 오늘 날짜를 기본값으로 설정
        window.onload = function() {
            const dateInput = document.getElementById('date');
            if (!dateInput.value) {
                const today = new Date();
                const yyyy = today.getFullYear();
                const mm = String(today.getMonth() + 1).padStart(2, '0');
                const dd = String(today.getDate()).padStart(2, '0');
                dateInput.value = yyyy + '-' + mm + '-' + dd;
            }
        };
        
        // 이미지 미리보기 기능
        function previewImage(input) {
            const preview = document.getElementById('preview');
            const previewImage = document.getElementById('preview-image');
            
            if (input.files && input.files[0]) {
                const reader = new FileReader();
                
                reader.onload = function(e) {
                    previewImage.src = e.target.result;
                    preview.style.display = 'block';
                };
                
                reader.readAsDataURL(input.files[0]);
                
                // 파일 크기 검증
                const fileSize = input.files[0].size;
                const maxSize = 5 * 1024 * 1024; // 5MB
                
                if (fileSize > maxSize) {
                    alert('파일 크기는 5MB 이하여야 합니다.');
                    input.value = '';
                    preview.style.display = 'none';
                    return;
                }
                
                // 파일 타입 검증
                const fileType = input.files[0].type;
                if (!fileType.startsWith('image/')) {
                    alert('이미지 파일만 업로드 가능합니다.');
                    input.value = '';
                    preview.style.display = 'none';
                    return;
                }
            } else {
                preview.style.display = 'none';
            }
        }
    </script>
</body>
</html>