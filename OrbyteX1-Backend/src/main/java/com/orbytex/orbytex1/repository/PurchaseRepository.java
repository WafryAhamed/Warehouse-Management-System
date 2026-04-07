package com.orbytex.orbytex1.repository;

import com.orbytex.orbytex1.entity.Purchase;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByPurchaseNumber(String purchaseNumber);
    Page<Purchase> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);
    Page<Purchase> findBySupplier_IdOrderByCreatedAtDesc(Long supplierId, Pageable pageable);
}

