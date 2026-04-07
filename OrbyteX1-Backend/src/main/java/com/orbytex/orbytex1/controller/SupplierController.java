package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.entity.Supplier;
import com.orbytex.orbytex1.service.SupplierService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/suppliers")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class SupplierController {
    private SupplierService supplierService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Supplier> createSupplier(@Valid @RequestBody Supplier supplier) {
        log.info("Creating new supplier: {}", supplier.getSupplierName());
        Supplier created = supplierService.createSupplier(supplier);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Long id, @Valid @RequestBody Supplier supplier) {
        log.info("Updating supplier: {}", id);
        Supplier updated = supplierService.updateSupplier(id, supplier);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Supplier> getSupplierById(@PathVariable Long id) {
        Supplier supplier = supplierService.getSupplierById(id);
        return ResponseEntity.ok(supplier);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Page<Supplier>> getAllSuppliers(Pageable pageable) {
        Page<Supplier> suppliers = supplierService.getAllSuppliers(pageable);
        return ResponseEntity.ok(suppliers);
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'INVENTORY_MANAGER')")
    public ResponseEntity<Page<Supplier>> searchSuppliers(
            @RequestParam String name,
            Pageable pageable) {
        log.info("Searching suppliers with name: {}", name);
        Page<Supplier> results = supplierService.searchSuppliers(name, pageable);
        return ResponseEntity.ok(results);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<String> deleteSupplier(@PathVariable Long id) {
        log.info("Deleting supplier: {}", id);
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted successfully");
    }
}

