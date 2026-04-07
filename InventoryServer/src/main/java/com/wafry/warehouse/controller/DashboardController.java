package com.wafry.warehouse.controller;

import com.wafry.warehouse.dto.DashboardStatsDTO;
import com.wafry.warehouse.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@CrossOrigin(origins = "*")
public class DashboardController {

    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;
    private final StockRepository stockRepository;
    private final StockMovementRepository movementRepository;

    public DashboardController(ProductRepository productRepository, WarehouseRepository warehouseRepository,
                             StockRepository stockRepository, StockMovementRepository movementRepository) {
        this.productRepository = productRepository;
        this.warehouseRepository = warehouseRepository;
        this.stockRepository = stockRepository;
        this.movementRepository = movementRepository;
    }

    @GetMapping("/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        try {
            DashboardStatsDTO stats = new DashboardStatsDTO();
            stats.setTotalProducts((int) productRepository.count());
            stats.setTotalWarehouses((int) warehouseRepository.count());
            stats.setTotalStock(stockRepository.findAll().stream()
                    .mapToInt(s -> s.getQuantity() != null ? s.getQuantity() : 0)
                    .sum());
            stats.setTotalMovements(movementRepository.count());
            stats.setUtilizationPercent(50.0);
            stats.setLowStockCount(0);
            stats.setTotalValue(0);

            return ResponseEntity.ok(stats);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();
        }
    }

    @GetMapping("/overview")
    public ResponseEntity<?> getDashboardOverview() {
        return ResponseEntity.ok("Dashboard overview");
    }
}
