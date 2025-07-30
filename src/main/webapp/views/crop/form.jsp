<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>${isEdit ? '작물 수정' : '작물 등록'} - Samyang 농업 플랫폼</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
            background-color: #f5f5f5;
        }
        .container {
            max-width: 600px;
            margin: 50px auto;
            background: white;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0,0,0,0.1);
        }
        h1 {
            color: #333;
            margin-bottom: 30px;
        }
        .form-group {
            margin-bottom: 20px;
        }
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: bold;
            color: #666;
        }
        input[type="text"],
        input[type="date"],
        select {
            width: 100%;
            padding: 10px;
            border: 1px solid #ddd;
            border-radius: 5px;
            box-sizing: border-box;
        }
        select {
            height: 42px;
        }
        .required {
            color: red;
        }
        .btn-group {
            margin-top: 30px;
        }
        .btn {
            display: inline-block;
            padding: 10px 20px;
            margin-right: 10px;
            text-decoration: none;
            border-radius: 5px;
            border: none;
            cursor: pointer;
            font-size: 16px;
        }
        .btn-primary {
            background-color: #4CAF50;
            color: white;
        }
        .btn-primary:hover {
            background-color: #45a049;
        }
        .btn-secondary {
            background-color: #666;
            color: white;
        }
        .btn-secondary:hover {
            background-color: #555;
        }
        .help-text {
            font-size: 12px;
            color: #999;
            margin-top: 5px;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>🌱 ${isEdit ? '작물 수정' : '작물 등록'}</h1>
        
        <form action="/crop/save" method="post">
            <c:if test="${isEdit}">
                <input type="hidden" name="cropId" value="${crop.cropId}">
                <input type="hidden" name="userId" value="${crop.userId}">
            </c:if>
            
            <div class="form-group">
                <label for="cropName">작물명 <span class="required">*</span></label>
                <input type="text" id="cropName" name="cropName" value="${crop.cropName}" required>
            </div>
            
            <div class="form-group">
                <label for="variety">품종</label>
                <input type="text" id="variety" name="variety" value="${crop.variety}">
                <div class="help-text">예: 청양고추, 백도라지, 샤인머스켓 등</div>
            </div>
            
            <div class="form-group">
                <label for="plantedDate">식재일</label>
                <input type="date" id="plantedDate" name="plantedDate" 
                       value="<fmt:formatDate value='${crop.plantedDate}' pattern='yyyy-MM-dd'/>">
            </div>
            
            <div class="form-group">
                <label for="expectedHarvestDate">예상 수확일</label>
                <input type="date" id="expectedHarvestDate" name="expectedHarvestDate" 
                       value="<fmt:formatDate value='${crop.expectedHarvestDate}' pattern='yyyy-MM-dd'/>">
            </div>
            
            <div class="form-group">
                <label for="status">상태</label>
                <select id="status" name="status">
                    <option value="">선택하세요</option>
                    <option value="planning" ${crop.status == 'planning' ? 'selected' : ''}>계획중</option>
                    <option value="growing" ${crop.status == 'growing' ? 'selected' : ''}>재배중</option>
                    <option value="harvested" ${crop.status == 'harvested' ? 'selected' : ''}>수확완료</option>
                </select>
            </div>
            
            <div class="btn-group">
                <button type="submit" class="btn btn-primary">${isEdit ? '수정' : '등록'}</button>
                <a href="/crop/list" class="btn btn-secondary">취소</a>
            </div>
        </form>
    </div>
    
    <script>
        // 날짜 입력 제한 (미래 날짜만 허용하는 경우 등)
        document.addEventListener('DOMContentLoaded', function() {
            var plantedDate = document.getElementById('plantedDate');
            var expectedHarvestDate = document.getElementById('expectedHarvestDate');
            
            // 식재일이 변경되면 예상 수확일의 최소값을 식재일로 설정
            plantedDate.addEventListener('change', function() {
                expectedHarvestDate.min = this.value;
                if (expectedHarvestDate.value && expectedHarvestDate.value < this.value) {
                    expectedHarvestDate.value = '';
                }
            });
        });
    </script>
</body>
</html>