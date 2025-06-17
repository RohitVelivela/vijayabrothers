package com.vijaybrothers.store.repository;

import com.vijaybrothers.store.model.Product;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query("""
        select p from Product p
        where (:catId is null or p.category.id = :catId)
          and (:q is null or lower(p.name) like lower(concat('%', :q, '%')))
        """)    
    Page<Product> search(@Param("catId") Integer categoryId,
                        @Param("q")     String  q,
                        Pageable page);

    boolean existsByProductCode(String productCode);

    @Query("""
        select p 
        from Product p
        left join fetch p.category
        left join fetch p.images
        where p.productId = :id
        """)
    Optional<Product> findByIdWithCategoryAndImages(@Param("id") Integer id);

    /** Count how many products are under the low-stock threshold */
    long countByStockQuantityLessThan(int threshold);

    /** Find all products under the low-stock threshold */
    List<Product> findByStockQuantityLessThan(int threshold);

    /**
     * Find all products in a specific category
     * @param categoryId The ID of the category to find products for
     * @return List of products in the category
     */
    List<Product> findAllByCategoryCategoryId(Integer categoryId);

    /** Find by SKU/product_code */
    Optional<Product> findByProductCode(String productCode);

    /** Search by name OR code, case-insensitive */
    Page<Product> findByNameContainingIgnoreCaseOrProductCodeContainingIgnoreCase(
        String name, String code, Pageable pageable);

    /** All flagged in stock */
    List<Product> findByInStockTrue();
}
