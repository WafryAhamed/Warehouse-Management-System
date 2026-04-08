package com.wafry.warehouse.repository;

import com.wafry.warehouse.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;

@Repository
public interface ExpenseRepository extends JpaRepository<Expense, Long> {
    @Query("SELECT SUM(e.amount) FROM Expense e WHERE e.createdAt >= ?1")
    java.math.BigDecimal getTotalExpensesSince(LocalDateTime date);
}

