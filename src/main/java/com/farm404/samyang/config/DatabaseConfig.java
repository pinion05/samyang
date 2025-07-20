package com.farm404.samyang.config;

import com.farm404.samyang.entity.Product;
import com.farm404.samyang.entity.User;
import com.farm404.samyang.service.ProductService;
import com.farm404.samyang.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.math.BigDecimal;

/**
 * 데이터베이스 설정 및 초기 데이터 구성 클래스
 * MySQL 데이터베이스 연결 테스트 및 샘플 데이터를 생성합니다.
 */
@Configuration
@RequiredArgsConstructor
@Slf4j
public class DatabaseConfig {
    
    private final UserService userService;
    private final ProductService productService;
    
    /**
     * 애플리케이션 시작 시 데이터베이스 연결 테스트 및 초기 데이터 생성
     * 개발 환경에서만 실행됩니다.
     */
    @Bean
    @Profile("!prod") // 프로덕션 환경이 아닐 때만 실행
    public CommandLineRunner initDatabase() {
        return args -> {
            log.info("=== 데이터베이스 초기화 시작 ===");
            
            try {
                // 데이터베이스 연결 테스트
                testDatabaseConnection();
                
                // 초기 데이터가 없는 경우에만 생성
                if (userService.getAllUsers().isEmpty()) {
                    createInitialUsers();
                }
                
                if (productService.getAllProducts().isEmpty()) {
                    createInitialProducts();
                }
                
                log.info("=== 데이터베이스 초기화 완료 ===");
                
            } catch (Exception e) {
                log.error("데이터베이스 초기화 중 오류 발생: {}", e.getMessage(), e);
            }
        };
    }
    
    /**
     * 데이터베이스 연결 테스트
     */
    private void testDatabaseConnection() {
        log.info("데이터베이스 연결 테스트 중...");
        
        long userCount = userService.getAllUsers().size();
        long productCount = productService.getAllProducts().size();
        
        log.info("데이터베이스 연결 성공! 현재 사용자 수: {}, 상품 수: {}", userCount, productCount);
    }
    
    /**
     * 초기 사용자 데이터 생성
     */
    private void createInitialUsers() {
        log.info("초기 사용자 데이터 생성 중...");
        
        // 관리자 사용자
        User admin = User.builder()
                .username("admin")
                .email("admin@samyang.com")
                .password("admin123") // 실제 환경에서는 암호화 필요
                .isAdmin(true)
                .deliveryAddress("서울시 강남구 테헤란로 123")
                .build();
        
        // 일반 사용자
        User user1 = User.builder()
                .username("user1")
                .email("user1@example.com")
                .password("user123") // 실제 환경에서는 암호화 필요
                .isAdmin(false)
                .deliveryAddress("서울시 서초구 서초대로 456")
                .build();
        
        User user2 = User.builder()
                .username("user2")
                .email("user2@example.com")
                .password("user123") // 실제 환경에서는 암호화 필요
                .isAdmin(false)
                .deliveryAddress("부산시 해운대구 해운대로 789")
                .build();
        
        userService.createUser(admin);
        userService.createUser(user1);
        userService.createUser(user2);
        
        log.info("초기 사용자 데이터 생성 완료");
    }
    
    /**
     * 초기 상품 데이터 생성
     */
    private void createInitialProducts() {
        log.info("초기 상품 데이터 생성 중...");
        
        // 라면 카테고리 상품들
        Product product1 = Product.builder()
                .name("삼양 불닭볶음면")
                .description("매콤한 불닭 소스로 볶아낸 인기 라면")
                .price(new BigDecimal("1200"))
                .originalPrice(new BigDecimal("1500"))
                .discount(20)
                .image("/images/buldak-ramen.jpg")
                .category("라면")
                .rating(4.5)
                .reviewCount(1250)
                .stockQuantity(100)
                .status(Product.ProductStatus.ACTIVE)
                .build();
        
        Product product2 = Product.builder()
                .name("삼양 까르보 불닭볶음면")
                .description("크리미한 까르보나라와 불닭의 만남")
                .price(new BigDecimal("1300"))
                .originalPrice(new BigDecimal("1300"))
                .discount(0)
                .image("/images/carbo-buldak.jpg")
                .category("라면")
                .rating(4.7)
                .reviewCount(980)
                .stockQuantity(80)
                .status(Product.ProductStatus.ACTIVE)
                .build();
        
        Product product3 = Product.builder()
                .name("삼양 치즈 불닭볶음면")
                .description("진한 치즈와 불닭의 환상적인 조합")
                .price(new BigDecimal("1400"))
                .originalPrice(new BigDecimal("1600"))
                .discount(12)
                .image("/images/cheese-buldak.jpg")
                .category("라면")
                .rating(4.6)
                .reviewCount(756)
                .stockQuantity(120)
                .status(Product.ProductStatus.ACTIVE)
                .build();
        
        // 스낵 카테고리 상품들
        Product product4 = Product.builder()
                .name("삼양 불닭 떡볶이")
                .description("매콤달콤한 불닭 소스 떡볶이")
                .price(new BigDecimal("2500"))
                .originalPrice(new BigDecimal("2500"))
                .discount(0)
                .image("/images/buldak-tteokbokki.jpg")
                .category("스낵")
                .rating(4.3)
                .reviewCount(432)
                .stockQuantity(60)
                .status(Product.ProductStatus.ACTIVE)
                .build();
        
        Product product5 = Product.builder()
                .name("삼양 불닭 볶음밥")
                .description("간편하게 즐기는 불닭 볶음밥")
                .price(new BigDecimal("3200"))
                .originalPrice(new BigDecimal("3500"))
                .discount(8)
                .image("/images/buldak-rice.jpg")
                .category("즉석식품")
                .rating(4.4)
                .reviewCount(298)
                .stockQuantity(40)
                .status(Product.ProductStatus.ACTIVE)
                .build();
        
        productService.createProduct(product1);
        productService.createProduct(product2);
        productService.createProduct(product3);
        productService.createProduct(product4);
        productService.createProduct(product5);
        
        log.info("초기 상품 데이터 생성 완료");
    }
}