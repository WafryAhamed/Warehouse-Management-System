package com.wafry.warehouse.repository;

import com.wafry.warehouse.entity.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderNumber(String orderNumber);
    Page<Order> findByCustomer_IdOrderByCreatedAtDesc(Long customerId, Pageable pageable);
    Page<Order> findByStatusOrderByCreatedAtDesc(String status, Pageable pageable);

    @Query("SELECT COUNT(o) FROM Order o WHERE o.status = 'COMPLETED'")
    Integer countCompletedOrders();

    @Query("SELECT SUM(o.finalAmount) FROM Order o WHERE o.status = 'COMPLETED' AND o.createdAt >= ?1")
    java.math.BigDecimal getTotalRevenueSince(LocalDateTime date);

    @Query("SELECT o FROM Order o WHERE o.createdAt >= ?1 AND o.createdAt < ?2")
    List<Order> findByDateRange(LocalDateTime start, LocalDateTime end);
}

