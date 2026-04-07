package com.orbytex.orbytex1.repository;

import com.orbytex.orbytex1.entity.AuditLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {
    Page<AuditLog> findByUser_IdOrderByCreatedAtDesc(Long userId, Pageable pageable);
    List<AuditLog> findByCreatedAtBetween(LocalDateTime start, LocalDateTime end);
    List<AuditLog> findByEntityTypeAndEntityIdOrderByCreatedAtDesc(String entityType, Long entityId);
}

