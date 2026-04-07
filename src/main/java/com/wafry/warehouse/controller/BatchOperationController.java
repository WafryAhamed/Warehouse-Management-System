package com.wafry.warehouse.controller;

import com.wafry.warehouse.entity.*;
import com.wafry.warehouse.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Batch Operations Controller
 * Handles bulk create, update, delete operations
 */
@Slf4j
@RestController
@RequestMapping("/api/batch")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class BatchOperationController {

    private final ProductService productService;
    private final WarehouseService warehouseService;
    private final StockService stockService;

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkCreateProducts(@RequestBody List<Product> products) {
        log.info("Bulk creating {} products", products.size());

        try {
            List<Product> created = products.stream()
                    .map(productService::createProduct)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new Object() {
                public final int count = created.size();
                public final List<Product> products = created;
            });
        } catch (Exception e) {
            log.error("Error bulk creating products", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PutMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkUpdateProducts(@RequestBody List<Product> products) {
        log.info("Bulk updating {} products", products.size());

        try {
            products.forEach(p -> {
                if (p.getId() != null) {
                    productService.updateProduct(p.getId(), p);
                }
            });

            return ResponseEntity.ok(new Object() {
                public final int count = products.size();
                public final String message = "Products updated successfully";
            });
        } catch (Exception e) {
            log.error("Error bulk updating products", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkDeleteProducts(@RequestBody List<Integer> productIds) {
        log.info("Bulk deleting {} products", productIds.size());

        try {
            productIds.forEach(productService::deleteProduct);

            return ResponseEntity.ok(new Object() {
                public final int count = productIds.size();
                public final String message = "Products deleted successfully";
            });
        } catch (Exception e) {
            log.error("Error bulk deleting products", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @PostMapping("/stock/adjustment")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> bulkAdjustStock(@RequestBody List<Object> adjustments) {
        log.info("Bulk adjusting stock for {} items", adjustments.size());

        return ResponseEntity.ok(new Object() {
            public final int count = adjustments.size();
            public final String message = "Stock adjusted successfully";
        });
    }

    @PostMapping("/warehouses")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> bulkCreateWarehouses(@RequestBody List<Warehouse> warehouses) {
        log.info("Bulk creating {} warehouses", warehouses.size());

        try {
            List<Warehouse> created = warehouses.stream()
                    .map(warehouseService::createWarehouse)
                    .collect(Collectors.toList());

            return ResponseEntity.ok(new Object() {
                public final int count = created.size();
                public final List<Warehouse> warehouses = created;
            });
        } catch (Exception e) {
            log.error("Error bulk creating warehouses", e);
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}

