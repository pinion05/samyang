<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${pageTitle != null ? pageTitle : '삼양 농업 플랫폼'}</title>
    
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
    
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    
    <!-- jQuery -->
    <script src="/scripts/jquery-3.7.1.js"></script>
    
    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
    
    <style>
        .navbar-brand {
            font-weight: bold;
            color: #28a745 !important;
        }
        
        .main-content {
            margin-top: 20px;
            margin-bottom: 40px;
        }
        
        .card-header {
            background-color: #f8f9fa;
            border-bottom: 2px solid #dee2e6;
        }
        
        .btn-success {
            background-color: #28a745;
            border-color: #28a745;
        }
        
        .search-form {
            background-color: #f8f9fa;
            padding: 20px;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        
        .table-responsive {
            border-radius: 5px;
        }
        
        .alert {
            margin-top: 10px;
        }
        
        footer {
            background-color: #343a40;
            color: white;
            padding: 20px 0;
            margin-top: 50px;
        }
    </style>
</head>
<body>
    <!-- Navigation -->
    <nav class="navbar navbar-expand-lg navbar-light bg-light border-bottom">
        <div class="container">
            <a class="navbar-brand" href="/">
                <i class="fas fa-seedling"></i> 삼양 농업 플랫폼
            </a>
            
            <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
                <span class="navbar-toggler-icon"></span>
            </button>
            
            <div class="collapse navbar-collapse" id="navbarNav">
                <ul class="navbar-nav me-auto">
                    <li class="nav-item">
                        <a class="nav-link" href="/"><i class="fas fa-home"></i> 홈</a>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-users"></i> 사용자 관리
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/user/list">사용자 목록</a></li>
                            <li><a class="dropdown-item" href="/user/register">사용자 등록</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-leaf"></i> 작물 관리
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/crop/list">작물 목록</a></li>
                            <li><a class="dropdown-item" href="/crop/register">작물 등록</a></li>
                            <li><a class="dropdown-item" href="/crop/harvestable">수확 가능 작물</a></li>
                        </ul>
                    </li>
                    <li class="nav-item dropdown">
                        <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown">
                            <i class="fas fa-book"></i> 농사일지
                        </a>
                        <ul class="dropdown-menu">
                            <li><a class="dropdown-item" href="/diary/list">일지 목록</a></li>
                            <li><a class="dropdown-item" href="/diary/register">일지 작성</a></li>
                            <li><a class="dropdown-item" href="/diary/recent">최근 일지</a></li>
                        </ul>
                    </li>
                </ul>
                
                <ul class="navbar-nav">
                    <li class="nav-item">
                        <a class="nav-link" href="/user/login">
                            <i class="fas fa-sign-in-alt"></i> 로그인
                        </a>
                    </li>
                </ul>
            </div>
        </div>
    </nav>

    <!-- Alert Messages -->
    <div class="container">
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show" role="alert">
                <i class="fas fa-check-circle"></i> ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                <i class="fas fa-exclamation-circle"></i> ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
    </div>

    <!-- Main Content -->
    <div class="container main-content">