package com.wafry.warehouse.controller;

import com.wafry.warehouse.dto.DashboardStatsDTO;
import com.wafry.warehouse.service.*;
import com.wafry.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;

@Slf4j
@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;
    private final StockMovementRepository movementRepository;

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        log.info("Fetching dashboard statistics");

        // Count products
        int totalProducts = (int) productRepository.count();

        // Count warehouses
        int totalWarehouses = (int) warehouseRepository.count();

        // Total stock quantity
        int totalStock = stockRepository.findAll().stream()
                .mapToInt(s -> s.getQuantity() != null ? s.getQuantity() : 0)
                .sum();

        // Low stock products (< 100 units)
        int lowStockCount = (int) productRepository.findAll().stream()
                .filter(p -> {
                    Integer quantity = stockRepository.findByProductId(p.getId()).stream()
                            .mapToInt(s -> s.getQuantity() != null ? s.getQuantity() : 0)
                            .sum();
                    return quantity < 100;
                })
                .count();

        // Total inventory value
        double totalValue = productRepository.findAll().stream()
                .mapToDouble(p -> {
                    Integer quantity = stockRepository.findByProductId(p.getId()).stream()
                            .mapToInt(s -> s.getQuantity() != null ? s.getQuantity() : 0)
                            .sum();
                    return quantity * p.getPrice().doubleValue();
                })
                .sum();

        // Warehouse utilization
        double utilizationPercent = warehouseRepository.findAll().stream()
                .mapToDouble(w -> {
                    if (w.getCapacity() == null || w.getCapacity() == 0) return 0;
                    return ((double) (w.getCurrentStock() != null ? w.getCurrentStock() : 0) / w.getCapacity()) * 100;
                })
                .average()
                .orElse(0);

        DashboardStatsDTO stats = DashboardStatsDTO.builder()
                .totalProducts(totalProducts)
                .totalWarehouses(totalWarehouses)
                .totalStock(totalStock)
                .lowStockCount(lowStockCount)
                .totalInventoryValue(totalValue)
                .utilizationPercent(utilizationPercent)
                .build();

        return ResponseEntity.ok(stats);
    }

    @GetMapping("/overview")
    public ResponseEntity<?> getDashboardOverview() {
        log.info("Fetching dashboard overview");

        return ResponseEntity.ok(new Object() {
            public final int recentMovements = (int) movementRepository.count();
            public final int activeWarehouses = (int) warehouseRepository.count();
            public final int activeProducts = (int) productRepository.count();
            public final long totalTransactions = movementRepository.count();
        });
    }
}

