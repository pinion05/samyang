package com.farm404.samyang.repository;

import com.farm404.samyang.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 상품 데이터 접근을 위한 리포지토리 인터페이스
 * Spring Data JPA를 사용하여 MySQL 데이터베이스에 접근합니다.
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    
    /**
     * 카테고리별 상품 조회
     * @param category 카테고리명
     * @return 상품 목록
     */
    List<Product> findByCategory(String category);
    
    /**
     * 상품 상태별 조회
     * @param status 상품 상태
     * @return 상품 목록
     */
    List<Product> findByStatus(Product.ProductStatus status);
    
    /**
     * 활성 상품 조회
     * @return 활성 상품 목록
     */
    List<Product> findByStatusOrderByCreatedAtDesc(Product.ProductStatus status);
    
    /**
     * 상품명으로 검색 (부분 일치)
     * @param name 검색할 상품명
     * @return 상품 목록
     */
    List<Product> findByNameContainingIgnoreCase(String name);
    
    /**
     * 가격 범위로 상품 조회
     * @param minPrice 최소 가격
     * @param maxPrice 최대 가격
     * @return 상품 목록
     */
    List<Product> findByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    
    /**
     * 할인 상품 조회
     * @return 할인 상품 목록
     */
    List<Product> findByDiscountGreaterThan(Integer discount);
    
    /**
     * 카테고리별 활성 상품 조회
     * @param category 카테고리명
     * @param status 상품 상태
     * @return 상품 목록
     */
    List<Product> findByCategoryAndStatus(String category, Product.ProductStatus status);
    
    /**
     * 평점 높은 순으로 상품 조회
     * @param limit 조회할 개수
     * @return 상품 목록
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.rating DESC LIMIT :limit")
    List<Product> findTopRatedProducts(@Param("limit") int limit);
    
    /**
     * 최신 상품 조회
     * @param limit 조회할 개수
     * @return 상품 목록
     */
    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.createdAt DESC LIMIT :limit")
    List<Product> findLatestProducts(@Param("limit") int limit);
    
    /**
     * 재고 부족 상품 조회
     * @param threshold 재고 임계값
     * @return 상품 목록
     */
    List<Product> findByStockQuantityLessThan(Integer threshold);
    
    /**
     * 카테고리별 상품 수 조회
     * @return 카테고리별 상품 수
     */
    @Query("SELECT p.category, COUNT(p) FROM Product p WHERE p.status = 'ACTIVE' GROUP BY p.category")
    List<Object[]> countProductsByCategory();
}