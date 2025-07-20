package com.farm404.samyang.service;

import com.farm404.samyang.entity.Product;
import com.farm404.samyang.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

/**
 * 상품 관련 비즈니스 로직을 처리하는 서비스 클래스
 * MySQL 데이터베이스와의 상호작용을 담당합니다.
 */
@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductService {
    
    private final ProductRepository productRepository;
    
    /**
     * 모든 상품 조회
     * @return 상품 목록
     */
    public List<Product> getAllProducts() {
        log.info("모든 상품 조회 요청");
        return productRepository.findAll();
    }
    
    /**
     * ID로 상품 조회
     * @param id 상품 ID
     * @return 상품 정보 (Optional)
     */
    public Optional<Product> getProductById(Long id) {
        log.info("상품 조회 요청 - ID: {}", id);
        return productRepository.findById(id);
    }
    
    /**
     * 활성 상품 목록 조회
     * @return 활성 상품 목록
     */
    public List<Product> getActiveProducts() {
        log.info("활성 상품 목록 조회 요청");
        return productRepository.findByStatusOrderByCreatedAtDesc(Product.ProductStatus.ACTIVE);
    }
    
    /**
     * 카테고리별 활성 상품 조회
     * @param category 카테고리명
     * @return 상품 목록
     */
    public List<Product> getProductsByCategory(String category) {
        log.info("카테고리별 상품 조회 요청 - 카테고리: {}", category);
        return productRepository.findByCategoryAndStatus(category, Product.ProductStatus.ACTIVE);
    }
    
    /**
     * 상품명으로 검색
     * @param name 검색할 상품명
     * @return 검색된 상품 목록
     */
    public List<Product> searchProductsByName(String name) {
        log.info("상품명 검색 요청 - 검색어: {}", name);
        return productRepository.findByNameContainingIgnoreCase(name);
    }
    
    /**
     * 가격 범위로 상품 조회
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 상품 목록
     */
    public List<Product> getProductsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) {
        log.info("가격 범위 상품 조회 요청 - 최소: {}, 최대: {}", minPrice, maxPrice);
        return productRepository.findByPriceBetween(minPrice, maxPrice);
    }
    
    /**
     * 할인 상품 조회
     * @param minDiscount 최소 할인율
     * @return 할인 상품 목록
     */
    public List<Product> getDiscountedProducts(Integer minDiscount) {
        log.info("할인 상품 조회 요청 - 최소 할인율: {}%", minDiscount);
        return productRepository.findByDiscountGreaterThan(minDiscount);
    }
    
    /**
     * 평점 높은 상품 조회
     * @param limit 조회할 개수
     * @return 상품 목록
     */
    public List<Product> getTopRatedProducts(int limit) {
        log.info("평점 높은 상품 조회 요청 - 개수: {}", limit);
        return productRepository.findTopRatedProducts(limit);
    }
    
    /**
     * 최신 상품 조회
     * @param limit 조회할 개수
     * @return 상품 목록
     */
    public List<Product> getLatestProducts(int limit) {
        log.info("최신 상품 조회 요청 - 개수: {}", limit);
        return productRepository.findLatestProducts(limit);
    }
    
    /**
     * 새 상품 생성
     * @param product 상품 정보
     * @return 생성된 상품 정보
     */
    @Transactional
    public Product createProduct(Product product) {
        log.info("새 상품 생성 요청 - 상품명: {}, 카테고리: {}", product.getName(), product.getCategory());
        
        Product savedProduct = productRepository.save(product);
        log.info("상품 생성 완료 - ID: {}", savedProduct.getId());
        return savedProduct;
    }
    
    /**
     * 상품 정보 업데이트
     * @param id 상품 ID
     * @param productDetails 업데이트할 상품 정보
     * @return 업데이트된 상품 정보
     */
    @Transactional
    public Product updateProduct(Long id, Product productDetails) {
        log.info("상품 정보 업데이트 요청 - ID: {}", id);
        
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("상품을 찾을 수 없습니다: " + id));
        
        // 업데이트 가능한 필드들
        if (productDetails.getName() != null) {
            product.setName(productDetails.getName());
        }
        if (productDetails.getDescription() != null) {
            product.setDescription(productDetails.getDescription());
        }
        if (productDetails.getPrice() != null) {
            product.setPrice(productDetails.getPrice());
        }
        if (productDetails.getOriginalPrice() != null) {
            product.setOriginalPrice(productDetails.getOriginalPrice());
        }
        if (productDetails.getDiscount() != null) {
            product.setDiscount(productDetails.getDiscount());
        }
        if (productDetails.getImage() != null) {
            product.setImage(productDetails.getImage());
        }
        if (productDetails.getCategory() != null) {
            product.setCategory(productDetails.getCategory());
        }
        if (productDetails.getStockQuantity() != null) {
            product.setStockQuantity(productDetails.getStockQuantity());
        }
        if (productDetails.getStatus() != null) {
            product.setStatus(productDetails.getStatus());
        }
        
        Product updatedProduct = productRepository.save(product);
        log.info("상품 정보 업데이트 완료 - ID: {}", updatedProduct.getId());
        return updatedProduct;
    }
    
    /**
     * 상품 삭제
     * @param id 상품 ID
     */
    @Transactional
    public void deleteProduct(Long id) {
        log.info("상품 삭제 요청 - ID: {}", id);
        
        if (!productRepository.existsById(id)) {
            throw new IllegalArgumentException("상품을 찾을 수 없습니다: " + id);
        }
        
        productRepository.deleteById(id);
        log.info("상품 삭제 완료 - ID: {}", id);
    }
    
    /**
     * 재고 부족 상품 조회
     * @param threshold 재고 임계값
     * @return 재고 부족 상품 목록
     */
    public List<Product> getLowStockProducts(Integer threshold) {
        log.info("재고 부족 상품 조회 요청 - 임계값: {}", threshold);
        return productRepository.findByStockQuantityLessThan(threshold);
    }
    
    /**
     * 카테고리별 상품 수 조회
     * @return 카테고리별 상품 수
     */
    public List<Object[]> getProductCountByCategory() {
        log.info("카테고리별 상품 수 조회 요청");
        return productRepository.countProductsByCategory();
    }
}