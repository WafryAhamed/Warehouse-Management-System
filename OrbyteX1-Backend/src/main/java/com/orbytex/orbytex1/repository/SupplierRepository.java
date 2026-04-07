package com.orbytex.orbytex1.repository;

import com.orbytex.orbytex1.entity.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    Optional<Supplier> findBySupplierName(String supplierName);
    Page<Supplier> findBySupplierNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);
    Page<Supplier> findByIsActiveTrue(Pageable pageable);
}

