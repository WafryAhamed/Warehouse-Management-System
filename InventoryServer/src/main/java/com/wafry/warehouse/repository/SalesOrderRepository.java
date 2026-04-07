package com.wafry.warehouse.repository;

import com.wafry.warehouse.entity.SalesOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SalesOrderRepository extends JpaRepository<SalesOrder, Integer> {
    Optional<SalesOrder> findBySoNumber(String soNumber);
}

