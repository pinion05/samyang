package com.farm404.samyang.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * JSP 뷰를 렌더링하는 컨트롤러
 * 
 * 이 컨트롤러는 JSP 페이지를 반환하는 엔드포인트들을 제공합니다.
 * @RestController가 아닌 @Controller를 사용하여 뷰 이름을 반환합니다.
 */
@Controller
@RequestMapping("/jsp")
public class JspController {

    /**
     * JSP 홈페이지를 렌더링합니다.
     * 
     * @param model 뷰에 전달할 데이터를 담는 모델 객체
     * @return JSP 뷰 이름 (home.jsp)
     */
    @GetMapping("/home")
    public String home(Model model) {
        model.addAttribute("title", "Samyang JSP Home");
        model.addAttribute("message", "JSP가 정상적으로 설정되었습니다!");
        model.addAttribute("currentTime", new java.util.Date());
        return "home"; // /WEB-INF/jsp/home.jsp를 렌더링
    }

    /**
     * 사용자 정보를 보여주는 JSP 페이지를 렌더링합니다.
     * 
     * @param model 뷰에 전달할 데이터를 담는 모델 객체
     * @return JSP 뷰 이름 (user.jsp)
     */
    @GetMapping("/user")
    public String user(Model model) {
        model.addAttribute("userName", "홍길동");
        model.addAttribute("userEmail", "hong@example.com");
        model.addAttribute("userAge", 25);
        return "user"; // /WEB-INF/jsp/user.jsp를 렌더링
    }

    /**
     * 상점 정보를 보여주는 JSP 페이지를 렌더링합니다.
     * 
     * @param model 뷰에 전달할 데이터를 담는 모델 객체
     * @return JSP 뷰 이름 (shop.jsp)
     */
    @GetMapping("/shop")
    public String shop(Model model) {
        // 상점 기본 정보
        model.addAttribute("shopName", "프레시팜");
        model.addAttribute("shopLocation", "서울특별시 강남구");
        model.addAttribute("shopDescription", "신선한 농산물과 물풀을 제공하는 온라인 쇼핑몰");
        
        // 카테고리 더미데이터
        java.util.List<String> categories = java.util.Arrays.asList(
            "채소류", "과일류", "곡물류", "육류", "수산물", "유제품", "가공식품"
        );
        model.addAttribute("categories", categories);
        
        // 상품 더미데이터
        java.util.List<java.util.Map<String, Object>> products = new java.util.ArrayList<>();
        
        java.util.Map<String, Object> product1 = new java.util.HashMap<>();
        product1.put("id", 1);
        product1.put("name", "유기농 상추");
        product1.put("price", 3500);
        product1.put("originalPrice", 4000);
        product1.put("discount", 12);
        product1.put("image", "/images/lettuce.jpg");
        product1.put("category", "채소류");
        product1.put("rating", 4.8);
        product1.put("reviewCount", 156);
        products.add(product1);
        
        java.util.Map<String, Object> product2 = new java.util.HashMap<>();
        product2.put("id", 2);
        product2.put("name", "국산 임꺽쩡");
        product2.put("price", 8900);
        product2.put("originalPrice", 9900);
        product2.put("discount", 10);
        product2.put("image", "/images/apple.jpg");
        product2.put("category", "과일류");
        product2.put("rating", 4.6);
        product2.put("reviewCount", 89);
        products.add(product2);
        
        java.util.Map<String, Object> product3 = new java.util.HashMap<>();
        product3.put("id", 3);
        product3.put("name", "친환경 당근");
        product3.put("price", 2800);
        product3.put("originalPrice", 3200);
        product3.put("discount", 15);
        product3.put("image", "/images/carrot.jpg");
        product3.put("category", "채소류");
        product3.put("rating", 4.7);
        product3.put("reviewCount", 203);
        products.add(product3);
        
        java.util.Map<String, Object> product4 = new java.util.HashMap<>();
        product4.put("id", 4);
        product4.put("name", "제주 감귤");
        product4.put("price", 12000);
        product4.put("originalPrice", 15000);
        product4.put("discount", 20);
        product4.put("image", "/images/orange.jpg");
        product4.put("category", "과일류");
        product4.put("rating", 4.9);
        product4.put("reviewCount", 312);
        products.add(product4);
        
        model.addAttribute("products", products);
        
        // 배너 정보
        java.util.Map<String, String> banner = new java.util.HashMap<>();
        banner.put("title", "신선한 농산물 특가 세일");
        banner.put("subtitle", "지금 주문하면 무료배송!");
        banner.put("image", "/images/banner.jpg");
        model.addAttribute("banner", banner);
        
        // 사용자 정보 (장바구니 등)
        model.addAttribute("cartCount", 3);
        model.addAttribute("userName", "홍길동");
        model.addAttribute("isLoggedIn", true);
        
        return "shop"; // /WEB-INF/jsp/shop.jsp를 렌더링
    }
}
