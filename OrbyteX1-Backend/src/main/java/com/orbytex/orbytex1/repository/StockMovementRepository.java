package com.orbytex.orbytex1.repository;

import com.orbytex.orbytex1.entity.StockMovement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
    List<StockMovement> findByProduct_IdOrderByCreatedAtDesc(Long productId);
    Page<StockMovement> findByMovementTypeOrderByCreatedAtDesc(String movementType, Pageable pageable);
    List<StockMovement> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
}

