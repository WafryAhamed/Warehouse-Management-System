package com.wafry.warehouse.controller;

import com.wafry.warehouse.entity.*;
import com.wafry.warehouse.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class ProductController {
    private final ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable Integer id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        return ResponseEntity.ok(productService.createProduct(product));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> updateProduct(@PathVariable Integer id, @RequestBody Product product) {
        return ResponseEntity.ok(productService.updateProduct(id, product));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteProduct(@PathVariable Integer id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String name) {
        return ResponseEntity.ok(productService.searchProducts(name));
    }
}

@Slf4j
@RestController
@RequestMapping("/api/warehouses")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class WarehouseController {
    private final WarehouseService warehouseService;

    @GetMapping
    public ResponseEntity<List<Warehouse>> getAllWarehouses() {
        return ResponseEntity.ok(warehouseService.getAllWarehouses());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Warehouse> getWarehouse(@PathVariable Integer id) {
        return ResponseEntity.ok(warehouseService.getWarehouseById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Warehouse> createWarehouse(@RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.createWarehouse(warehouse));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Warehouse> updateWarehouse(@PathVariable Integer id, @RequestBody Warehouse warehouse) {
        return ResponseEntity.ok(warehouseService.updateWarehouse(id, warehouse));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteWarehouse(@PathVariable Integer id) {
        warehouseService.deleteWarehouse(id);
        return ResponseEntity.ok("Warehouse deleted");
    }

    @GetMapping("/{id}/utilization")
    public ResponseEntity<Double> getUtilization(@PathVariable Integer id) {
        return ResponseEntity.ok(warehouseService.getUtilizationPercentage(id));
    }
}

@Slf4j
@RestController
@RequestMapping("/api/stock")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class StockController {
    private final StockService stockService;

    @GetMapping("/{productId}/{warehouseId}")
    public ResponseEntity<Integer> getStock(@PathVariable Integer productId, @PathVariable Integer warehouseId) {
        return ResponseEntity.ok(stockService.getStockByProductAndWarehouse(productId, warehouseId));
    }

    @PostMapping("/add")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> addStock(@RequestParam Integer productId, @RequestParam Integer warehouseId,
                                      @RequestParam Integer quantity, @RequestParam String reference) {
        stockService.addStock(productId, warehouseId, quantity, reference);
        return ResponseEntity.ok("Stock added");
    }

    @PostMapping("/remove")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> removeStock(@RequestParam Integer productId, @RequestParam Integer warehouseId,
                                        @RequestParam Integer quantity, @RequestParam String reference) {
        stockService.removeStock(productId, warehouseId, quantity, reference);
        return ResponseEntity.ok("Stock removed");
    }

    @PostMapping("/transfer")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<?> transferStock(@RequestParam Integer productId, @RequestParam Integer fromWarehouse,
                                          @RequestParam Integer toWarehouse, @RequestParam Integer quantity,
                                          @RequestParam String reference) {
        stockService.transferStock(productId, fromWarehouse, toWarehouse, quantity, reference);
        return ResponseEntity.ok("Stock transferred");
    }
}

@Slf4j
@RestController
@RequestMapping("/api/suppliers")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SupplierController {
    private final SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<Supplier>> getAllSuppliers() {
        return ResponseEntity.ok(supplierService.getAllSuppliers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Supplier> getSupplier(@PathVariable Integer id) {
        return ResponseEntity.ok(supplierService.getSupplierById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Supplier> createSupplier(@RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.createSupplier(supplier));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Supplier> updateSupplier(@PathVariable Integer id, @RequestBody Supplier supplier) {
        return ResponseEntity.ok(supplierService.updateSupplier(id, supplier));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteSupplier(@PathVariable Integer id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.ok("Supplier deleted");
    }

    @GetMapping("/search")
    public ResponseEntity<List<Supplier>> searchSuppliers(@RequestParam String name) {
        return ResponseEntity.ok(supplierService.searchSuppliers(name));
    }
}

@Slf4j
@RestController
@RequestMapping("/api/purchase-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class PurchaseOrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<PurchaseOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllPurchaseOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PurchaseOrder> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getPurchaseOrderById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<PurchaseOrder> createOrder(@RequestBody PurchaseOrder po) {
        return ResponseEntity.ok(orderService.createPurchaseOrder(po));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<PurchaseOrder> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updatePurchaseOrderStatus(id, status));
    }
}

@Slf4j
@RestController
@RequestMapping("/api/sales-orders")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class SalesOrderController {
    private final OrderService orderService;

    @GetMapping
    public ResponseEntity<List<SalesOrder>> getAllOrders() {
        return ResponseEntity.ok(orderService.getAllSalesOrders());
    }

    @GetMapping("/{id}")
    public ResponseEntity<SalesOrder> getOrder(@PathVariable Integer id) {
        return ResponseEntity.ok(orderService.getSalesOrderById(id));
    }

    @PostMapping
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<SalesOrder> createOrder(@RequestBody SalesOrder so) {
        return ResponseEntity.ok(orderService.createSalesOrder(so));
    }

    @PutMapping("/{id}/status")
    @PreAuthorize("hasRole('STAFF')")
    public ResponseEntity<SalesOrder> updateStatus(@PathVariable Integer id, @RequestParam String status) {
        return ResponseEntity.ok(orderService.updateSalesOrderStatus(id, status));
    }
}

@Slf4j
@RestController
@RequestMapping("/api/locations")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class LocationController {
    private final LocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations() {
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocation(@PathVariable Integer id) {
        return ResponseEntity.ok(locationService.getLocationById(id));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<Location>> getWarehouseLocations(@PathVariable Integer warehouseId) {
        return ResponseEntity.ok(locationService.getWarehouseLocations(warehouseId));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> createLocation(@RequestBody Location location) {
        return ResponseEntity.ok(locationService.createLocation(location));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Location> updateLocation(@PathVariable Integer id, @RequestBody Location location) {
        return ResponseEntity.ok(locationService.updateLocation(id, location));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> deleteLocation(@PathVariable Integer id) {
        locationService.deleteLocation(id);
        return ResponseEntity.ok("Location deleted");
    }
}

@Slf4j
@RestController
@RequestMapping("/api/movements")
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MovementController {
    private final StockMovementService movementService;

    @GetMapping
    public ResponseEntity<List<StockMovement>> getAllMovements() {
        return ResponseEntity.ok(movementService.getAllMovements());
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockMovement> getMovement(@PathVariable Integer id) {
        return ResponseEntity.ok(movementService.getMovementById(id));
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<StockMovement>> getProductMovements(@PathVariable Integer productId) {
        return ResponseEntity.ok(movementService.getProductMovements(productId));
    }

    @GetMapping("/warehouse/{warehouseId}")
    public ResponseEntity<List<StockMovement>> getWarehouseMovements(@PathVariable Integer warehouseId) {
        return ResponseEntity.ok(movementService.getWarehouseMovements(warehouseId));
    }
}

