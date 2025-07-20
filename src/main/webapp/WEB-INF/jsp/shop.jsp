<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>프레시팜 - 농산물·물풀 구매</title>
    <style>
        :root {
            --primary: #2e7d32;
            --secondary: #628e5d;
            --light: #f8f9fa;
            --dark: #212529;
            --accent: #ff9800;
        }
        
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: 'Noto Sans KR', sans-serif;
        }
        
        body {
            background-color: #fff;
            color: var(--dark);
        }
        
        /* 헤더 스타일 */
        header {
            background-color: white;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            padding: 15px 5%;
        }
        
        .header-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }
        
        .logo-and-nav {
            display: flex;
            align-items: center;
        }
        
        .logo {
            font-size: 1.5rem;
            font-weight: bold;
            color: var(--primary);
            margin-right: 30px;
        }
        
        .logo span {
            color: var(--secondary);
        }
        
        nav ul {
            display: flex;
            list-style: none;
        }
        
        nav li {
            margin-right: 20px;
        }
        
        nav a {
            text-decoration: none;
            color: var(--dark);
            font-weight: 500;
            transition: color 0.3s;
        }
        
        nav a:hover {
            color: var(--primary);
        }
        
        .user-section {
            display: flex;
            gap: 20px;
            align-items: center;
        }
        
        .search-box {
            display: flex;
            background-color: #f1f1f1;
            border-radius: 20px;
            overflow: hidden;
            width: 250px;
        }
        
        .search-box input {
            background: transparent;
            border: none;
            padding: 10px 15px;
            width: calc(100% - 40px);
            font-size: 0.9rem;
        }
        
        .search-box button {
            background: transparent;
            border: none;
            width: 40px;
            cursor: pointer;
            color: #666;
        }
        
        .user-actions {
            display: flex;
            gap: 15px;
        }
        
        .cart-icon {
            position: relative;
        }
        
        .cart-count {
            background-color: var(--accent);
            color: white;
            border-radius: 50%;
            width: 20px;
            height: 20px;
            font-size: 0.75rem;
            position: absolute;
            top: -5px;
            right: -5px;
            display: flex;
            justify-content: center;
            align-items: center;
        }
        
        /* 메인 컨텐츠 스타일 */
        .main-container {
            display: flex;
            max-width: 1400px;
            margin: 30px auto;
            padding: 0 20px;
        }
        
        /* 사이드바 스타일 */
        .sidebar {
            width: 250px;
            padding-right: 20px;
            border-right: 1px solid #eee;
        }
        
        .sidebar-section {
            margin-bottom: 30px;
        }
        
        .sidebar-title {
            font-size: 1.1rem;
            margin-bottom: 15px;
            padding-bottom: 8px;
            border-bottom: 2px solid var(--primary);
            display: inline-block;
        }
        
        .checkbox-group {
            display: block;
            margin: 10px 0;
        }
        
        .checkbox-group input {
            margin-right: 10px;
        }
        
        .price-filter {
            margin: 15px 0;
        }
        
        .price-inputs {
            display: flex;
            gap: 10px;
            margin: 10px 0;
        }
        
        .price-inputs input {
            width: 100px;
            padding: 8px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .filter-divider {
            height: 1px;
            background-color: #eee;
            margin: 20px 0;
        }
        
        .filter-btn {
            background-color: var(--primary);
            color: white;
            border: none;
            width: 100%;
            padding: 12px;
            border-radius: 4px;
            cursor: pointer;
        }
        
        /* 상품 그리드 스타일 */
        .product-grid {
            flex: 1;
            padding-left: 20px;
        }
        
        .category-banner {
            height: 160px;
            background-color: #f0f8f0;
            margin-bottom: 30px;
            border-radius: 8px;
            display: flex;
            align-items: center;
            padding: 0 40px;
        }
        
        .category-info h2 {
            font-size: 1.8rem;
            color: var(--primary);
            margin-bottom: 10px;
        }
        
        .toolbar {
            display: flex;
            justify-content: space-between;
            margin-bottom: 20px;
        }
        
        .product-count {
            color: #666;
        }
        
        .sort-select {
            padding: 8px 30px 8px 15px;
            border: 1px solid #ddd;
            border-radius: 4px;
        }
        
        .products-container {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(220px, 1fr));
            gap: 25px;
        }
        
        .product-item {
            background-color: white;
            border-radius: 8px;
            box-shadow: 0 2px 8px rgba(0,0,0,0.1);
            overflow: hidden;
            transition: all 0.3s ease;
        }
        
        .product-item:hover {
            box-shadow: 0 5px 15px rgba(0,0,0,0.15);
        }
        
        .product-image-container {
            height: 180px;
            position: relative;
            overflow: hidden;
        }
        
        .product-image {
            width: 100%;
            height: 100%;
            object-fit: cover;
            transition: transform 0.3s;
        }
        
        .product-item:hover .product-image {
            transform: scale(1.05);
        }
        
        .discount-badge {
            position: absolute;
            top: 10px;
            right: 10px;
            background-color: var(--accent);
            color: white;
            padding: 4px 8px;
            border-radius: 3px;
            font-size: 0.8rem;
        }
        
        .product-info {
            padding: 15px;
        }
        
        .product-name {
            font-size: 1rem;
            margin-bottom: 10px;
            height: 38px;
            overflow: hidden;
        }
        
        .product-price {
            margin-bottom: 10px;
        }
        
        .current-price {
            font-weight: bold;
            font-size: 1.2rem;
            color: var(--primary);
        }
        
        .original-price {
            font-size: 0.9rem;
            color: #999;
            text-decoration: line-through;
            margin-left: 8px;
        }
        
        .product-rating {
            margin: 8px 0;
            font-size: 14px;
        }
        
        .rating {
            color: #ffa500;
            font-weight: bold;
        }
        
        .review-count {
            color: #666;
            margin-left: 5px;
        }
        
        .add-to-cart {
            background-color: var(--accent);
            color: white;
            border: none;
            width: 100%;
            padding: 8px;
            font-size: 0.9rem;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
            margin-top: 10px;
        }
        
        .add-to-cart:hover {
            background-color: #e68a00;
        }
        
        .quick-btn {
            background-color: var(--primary);
            color: white;
            border: none;
            width: 100%;
            padding: 8px;
            font-size: 0.9rem;
            cursor: pointer;
            border-radius: 4px;
            transition: background-color 0.3s;
            margin-top: 5px;
        }
        
        .quick-btn:hover {
            background-color: #1b5e20;
        }
        
        .pagination {
            display: flex;
            justify-content: center;
            margin: 40px 0;
            gap: 8px;
        }
        
        .page-number {
            width: 40px;
            height: 40px;
            display: flex;
            align-items: center;
            justify-content: center;
            border-radius: 5px;
            background-color: #f1f1f1;
            cursor: pointer;
        }
        
        .page-number.active {
            background-color: var(--primary);
            color: white;
        }
        
        /* 쿠키 배너 */
        .cookie-banner {
            background-color: rgba(0,0,0,0.85);
            color: white;
            position: fixed;
            bottom: 0;
            left: 0;
            width: 100%;
            padding: 15px 5%;
            display: flex;
            justify-content: space-between;
            align-items: center;
            z-index: 1000;
        }
        
        .cookie-text {
            font-size: 0.9rem;
            max-width: 85%;
        }
        
        .cookie-btn {
            background-color: var(--primary);
            color: white;
            border: none;
            padding: 8px 15px;
            border-radius: 4px;
            cursor: pointer;
        }
        
        /* 반응형 디자인 */
        @media (max-width: 992px) {
            .sidebar {
                width: 200px;
            }
            
            .category-banner {
                padding: 0 20px;
                height: 140px;
            }
            
            .products-container {
                grid-template-columns: repeat(auto-fill, minmax(200px, 1fr));
                gap: 20px;
            }
        }
        
        @media (max-width: 768px) {
            .header-container {
                flex-direction: column;
                gap: 15px;
            }
            
            .logo-and-nav {
                margin-bottom: 15px;
            }
            
            .user-section {
                width: 100%;
            }
            
            .search-box {
                flex: 1;
            }
            
            .main-container {
                flex-direction: column;
            }
            
            .sidebar {
                width: 100%;
                padding-right: 0;
                border-right: none;
                border-bottom: 1px solid #eee;
                margin-bottom: 20px;
                padding-bottom: 20px;
            }
            
            .product-grid {
                padding-left: 0;
            }
            
            .products-container {
                grid-template-columns: repeat(auto-fill, minmax(165px, 1fr));
                gap: 15px;
            }
            
            .product-image-container {
                height: 160px;
            }
        }
        
        @media (max-width: 576px) {
            nav ul {
                flex-wrap: wrap;
            }
            
            nav li {
                margin-bottom: 5px;
            }
            
            .category-banner {
                height: 120px;
                padding: 0 15px;
            }
            
            .toolbar {
                flex-direction: column;
                gap: 10px;
            }
            
            .products-container {
                grid-template-columns: repeat(2, 1fr);
            }
        }
    </style>
</head>
<body>
    <!-- 헤더 -->
    <header>
        <div class="header-container">
            <div class="logo-and-nav">
                <h1 class="logo">${shopName}</h1>
                <nav>
                    <ul>
                        <c:forEach var="category" items="${categories}">
                            <li><a href="#">${category}</a></li>
                        </c:forEach>
                        <li><a href="#">특가할인</a></li>
                        <li><a href="#">이벤트</a></li>
                    </ul>
                </nav>
            </div>
            <div class="user-section">
                <div class="search-box">
                    <input type="text" placeholder="상품명을 검색하세요...">
                    <button>검색</button>
                </div>
                <div class="user-actions">
                    <c:if test="${isLoggedIn}">
                        <a href="#" class="cart-icon">
                            <i class="fas fa-user"></i>
                            <span>${userName}</span>
                        </a>
                    </c:if>
                    <a href="#" class="cart-icon">
                        <i class="fas fa-shopping-cart"></i>
                        <span class="cart-count">${cartCount}</span>
                    </a>
                </div>
            </div>
        </div>
    </header>

    <!-- 메인 컨텐츠 -->
    <div class="main-container">
        <!-- 사이드바 필터 -->
        <aside class="sidebar">
            <div class="sidebar-section">
                <h3 class="sidebar-title">카테고리</h3>
                <c:forEach var="category" items="${categories}" varStatus="status">
                    <div class="checkbox-group">
                        <input type="checkbox" id="category${status.index}" <c:if test="${status.index == 0}">checked</c:if>>
                        <label for="category${status.index}">${category}</label>
                    </div>
                </c:forEach>
            </div>
            
            <div class="filter-divider"></div>
            
            <div class="sidebar-section">
                <h3 class="sidebar-title">가격 범위</h3>
                <div class="price-inputs">
                    <input type="number" placeholder="최소" value="5000">
                    <span>-</span>
                    <input type="number" placeholder="최대" value="50000">
                </div>
                <button class="filter-btn">필터 적용</button>
            </div>
            
            <div class="filter-divider"></div>
            
            <div class="sidebar-section">
                <h3 class="sidebar-title">배송 옵션</h3>
                <div class="checkbox-group">
                    <input type="checkbox" id="express" checked>
                    <label for="express">당일 배송</label>
                </div>
                <div class="checkbox-group">
                    <input type="checkbox" id="cold">
                    <label for="cold">냉장 배송</label>
                </div>
                <div class="checkbox-group">
                    <input type="checkbox" id="free">
                    <label for="free">무료 배송</label>
                </div>
            </div>
        </aside>

        <!-- 상품 그리드 -->
        <main class="product-grid">
            <div class="category-banner">
                <div class="category-info">
                    <h2>${banner.title}</h2>
                    <p>${banner.subtitle}</p>
                </div>
            </div>
            
            <div class="toolbar">
                <div class="product-count">총 ${fn:length(products)}개의 상품</div>
                <div class="sort">
                    <select class="sort-select">
                        <option>신상품순</option>
                        <option>판매인기순</option>
                        <option>낮은 가격순</option>
                        <option>높은 가격순</option>
                    </select>
                </div>
            </div>
            
            <div class="products-container">
                <c:forEach var="product" items="${products}">
                    <div class="product-item">
                        <div class="product-image-container">
                            <img src="${product.image}" alt="${product.name}" class="product-image">
                            <c:if test="${product.discount > 0}">
                                <div class="discount-badge">-${product.discount}%</div>
                            </c:if>
                        </div>
                        <div class="product-info">
                            <h3 class="product-name">${product.name}</h3>
                            <div class="product-price">
                                <span class="current-price">￦<fmt:formatNumber value="${product.price}" pattern="#,###"/></span>
                                <c:if test="${product.originalPrice > product.price}">
                                    <span class="original-price">￦<fmt:formatNumber value="${product.originalPrice}" pattern="#,###"/></span>
                                </c:if>
                            </div>
                            <c:if test="${product.rating > 0}">
                                <div class="product-rating">
                                    <span class="rating">★ ${product.rating}</span>
                                    <span class="review-count">(${product.reviewCount})</span>
                                </div>
                            </c:if>
                            <button class="add-to-cart">장바구니</button>
                            <button class="quick-btn">바로구매</button>
                        </div>
                    </div>
                </c:forEach>
            </div>
            
            <!-- 페이징 -->
            <div class="pagination">
                <div class="page-number active">1</div>
                <div class="page-number">2</div>
                <div class="page-number">3</div>
                <div class="page-number">4</div>
                <div class="page-number">5</div>
            </div>
        </main>
    </div>

    <!-- 쿠키 배너 -->
    <div class="cookie-banner">
        <div class="cookie-text">
            당사는 사용자 경험 향상을 위해 쿠키를 사용합니다. 사이트를 계속 이용하시면 쿠키 정책에 동의하는 것으로 간주됩니다.
        </div>
        <button class="cookie-btn">동의하고 계속하기</button>
    </div>

    <script>
        // 장바구니 아이콘 카운트 업데이트
        document.querySelectorAll('.add-to-cart').forEach(button => {
            button.addEventListener('click', function() {
                this.textContent = "담는 중...";
                
                setTimeout(() => {
                    this.textContent = "담기 완료!";
                    
                    const cartCount = document.querySelector('.cart-count');
                    cartCount.textContent = parseInt(cartCount.textContent) + 1;
                    
                    setTimeout(() => {
                        this.textContent = "장바구니";
                    }, 1500);
                }, 500);
            });
        });
        
        // 바로구매 버튼 클릭 효과
        document.querySelectorAll('.quick-btn').forEach(button => {
            button.addEventListener('click', function() {
                const prevText = this.textContent;
                this.textContent = "상품 준비 중...";
                
                setTimeout(() => {
                    this.textContent = prevText;
                }, 1500);
            });
        });
        
        // 페이징 핸들러
        document.querySelectorAll('.page-number').forEach(page => {
            page.addEventListener('click', function() {
                document.querySelector('.page-number.active').classList.remove('active');
                this.classList.add('active');
            });
        });
        
        // 쿠키 배너 닫기
        document.querySelector('.cookie-btn').addEventListener('click', function() {
            document.querySelector('.cookie-banner').style.display = 'none';
        });
    </script>
</body>
</html>
