package com.wafry.warehouse.repository;

import com.wafry.warehouse.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findBySku(String sku);
    List<Product> findByIsActiveTrue();
    Page<Product> findByNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);
    Page<Product> findByCategory_IdAndIsActiveTrue(Long categoryId, Pageable pageable);
    Page<Product> findByBrand_IdAndIsActiveTrue(Long brandId, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.reorderLevel >= COALESCE(p.inventory.quantityOnHand, 0) AND p.isActive = true")
    List<Product> findLowStockProducts();
}

