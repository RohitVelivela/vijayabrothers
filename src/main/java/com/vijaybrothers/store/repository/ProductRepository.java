package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Integer> {
    Optional<Product> findBySlug(String slug);
    List<Product> findByNameContainingIgnoreCase(String name);
    List<Product> findByCategory_CategoryId(Integer categoryId);
    List<Product> findByStockQuantityLessThanEqual(Integer stockQuantity);
    List<Product> findByStockQuantityLessThan(Integer quantity);
    List<Product> findByInStock(boolean inStock);
    boolean existsByCategory_CategoryId(Integer categoryId);
    Optional<Product> findByProductCode(String productCode);

    @Query("SELECT p FROM Product p JOIN FETCH p.category WHERE p.productId = :productId")
    Optional<Product> findByIdWithCategoryAndImages(@Param("productId") Integer productId);

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.categoryId = :categoryId) AND (LOWER(p.name) LIKE LOWER(CONCAT('%', :query, '%')) OR p.productCode LIKE CONCAT('%', :query, '%'))")
    Page<Product> search(@Param("categoryId") Integer categoryId, @Param("query") String query, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE (:categoryId IS NULL OR p.category.categoryId = :categoryId) " +
           "AND (:color IS NULL OR p.color = :color) " +
           "AND (:fabric IS NULL OR p.fabric = :fabric) " +
           "AND (:inStock IS NULL OR p.inStock = :inStock)")
    List<Product> filterProducts(
        @Param("categoryId") Integer categoryId,
        @Param("color") String color,
        @Param("fabric") String fabric,
        @Param("inStock") Boolean inStock
    );
}
