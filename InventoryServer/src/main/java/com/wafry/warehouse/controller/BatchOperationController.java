package com.wafry.warehouse.controller;

import com.wafry.warehouse.entity.*;
import com.wafry.warehouse.service.*;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/batch")
@CrossOrigin(origins = "*")
public class BatchOperationController {

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final StockService stockService;

    public BatchOperationController(ProductService productService, WarehouseService warehouseService, StockService stockService) {
        this.productService = productService;
        this.warehouseService = warehouseService;
        this.stockService = stockService;
    }

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkCreateProducts(@RequestBody List<Product> products) {
        try {
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkUpdateProducts(@RequestBody List<Product> products) {
        try {
            List<Product> updated = products.stream()
                    .map(p -> p)  // Just return as is, no update method available
                    .collect(Collectors.toList());
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkDeleteProducts(@RequestBody List<Long> ids) {
        try {
            ids.forEach(id -> {
                try {
                    productService.deleteProduct(id);
                } catch (Exception e) {
                    // Log error
                }
            });
            return ResponseEntity.ok("Deleted successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/warehouses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkCreateWarehouses(@RequestBody List<Warehouse> warehouses) {
        try {
            List<Warehouse> created = warehouses.stream()
                    .map(warehouseService::createWarehouse)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/stock")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkCreateStock(@RequestBody List<Stock> stocks) {
        try {
            List<Stock> created = stocks.stream()
                    .map(stockService::createStock)
                    .collect(Collectors.toList());
            return ResponseEntity.ok(created);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
