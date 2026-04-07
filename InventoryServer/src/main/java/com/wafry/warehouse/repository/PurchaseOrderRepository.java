package com.wafry.warehouse.repository;

import com.wafry.warehouse.entity.PurchaseOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Integer> {
    Optional<PurchaseOrder> findByPoNumber(String poNumber);
}

