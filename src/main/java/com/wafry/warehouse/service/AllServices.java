package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.*;
import com.wafry.warehouse.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.time.LocalDateTime;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    private final LocationRepository locationRepository;
    private final StockRepository stockRepository;

    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }

    public Warehouse getWarehouseById(Integer id) {
        return warehouseRepository.findById(id).orElseThrow(() -> new RuntimeException("Warehouse not found"));
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }

    public Warehouse updateWarehouse(Integer id, Warehouse warehouse) {
        Warehouse existing = getWarehouseById(id);
        existing.setName(warehouse.getName());
        existing.setLocation(warehouse.getLocation());
        existing.setCapacity(warehouse.getCapacity());
        existing.setUpdatedAt(LocalDateTime.now());
        return warehouseRepository.save(existing);
    }

    public void deleteWarehouse(Integer id) {
        warehouseRepository.deleteById(id);
    }

    public Integer getWarehouseCapacityUsage(Integer warehouseId) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        return warehouse.getCurrentStock();
    }

    public double getUtilizationPercentage(Integer warehouseId) {
        Warehouse warehouse = getWarehouseById(warehouseId);
        if (warehouse.getCapacity() == 0) return 0;
        return (double) warehouse.getCurrentStock() / warehouse.getCapacity() * 100;
    }
}

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockService {
    private final StockRepository stockRepository;
    private final StockMovementRepository movementRepository;
    private final ProductRepository productRepository;
    private final WarehouseRepository warehouseRepository;

    public Integer getStockByProductAndWarehouse(Integer productId, Integer warehouseId) {
        List<Stock> stocks = stockRepository.findByProductId(productId);
        return stocks.stream()
                .filter(s -> s.getWarehouseId().equals(warehouseId))
                .mapToInt(Stock::getQuantity)
                .sum();
    }

    public void addStock(Integer productId, Integer warehouseId, Integer quantity, String reference) {
        List<Stock> stocks = stockRepository.findByProductId(productId);
        Stock stock = stocks.stream()
                .filter(s -> s.getWarehouseId().equals(warehouseId))
                .findFirst()
                .orElse(new Stock());

        stock.setProductId(productId);
        stock.setWarehouseId(warehouseId);
        stock.setQuantity((stock.getQuantity() != null ? stock.getQuantity() : 0) + quantity);
        stock.setLastUpdated(LocalDateTime.now());
        stockRepository.save(stock);

        recordMovement(productId, warehouseId, quantity, MovementType.IN, reference, null, null);
    }

    public void removeStock(Integer productId, Integer warehouseId, Integer quantity, String reference) {
        Stock stock = stockRepository.findByProductId(productId).stream()
                .filter(s -> s.getWarehouseId().equals(warehouseId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Stock not found"));

        if (stock.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient stock");
        }

        stock.setQuantity(stock.getQuantity() - quantity);
        stock.setLastUpdated(LocalDateTime.now());
        stockRepository.save(stock);

        recordMovement(productId, warehouseId, quantity, MovementType.OUT, reference, null, null);
    }

    public void transferStock(Integer productId, Integer fromWarehouse, Integer toWarehouse, Integer quantity, String reference) {
        removeStock(productId, fromWarehouse, quantity, reference);
        addStock(productId, toWarehouse, quantity, reference);

        recordMovement(productId, fromWarehouse, quantity, MovementType.TRANSFER, reference, fromWarehouse, toWarehouse);
    }

    public void recordMovement(Integer productId, Integer warehouseId, Integer quantity, MovementType type,
                               String reference, Integer fromLocation, Integer toLocation) {
        StockMovement movement = StockMovement.builder()
                .productId(productId)
                .warehouseId(warehouseId)
                .type(type)
                .quantity(quantity)
                .referenceNumber(reference)
                .fromLocationId(fromLocation)
                .toLocationId(toLocation)
                .createdAt(LocalDateTime.now())
                .build();
        movementRepository.save(movement);
    }
}

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public List<Supplier> getAllSuppliers() {
        return supplierRepository.findAll();
    }

    public Supplier getSupplierById(Integer id) {
        return supplierRepository.findById(id).orElseThrow(() -> new RuntimeException("Supplier not found"));
    }

    public Supplier createSupplier(Supplier supplier) {
        return supplierRepository.save(supplier);
    }

    public Supplier updateSupplier(Integer id, Supplier supplier) {
        Supplier existing = getSupplierById(id);
        existing.setName(supplier.getName());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());
        existing.setAddress(supplier.getAddress());
        existing.setLeadTimeDays(supplier.getLeadTimeDays());
        existing.setStatus(supplier.getStatus());
        return supplierRepository.save(existing);
    }

    public void deleteSupplier(Integer id) {
        supplierRepository.deleteById(id);
    }

    public List<Supplier> searchSuppliers(String name) {
        return supplierRepository.findByNameContainingIgnoreCase(name);
    }
}

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final PurchaseOrderRepository purchaseOrderRepository;
    private final SalesOrderRepository salesOrderRepository;
    private final OrderItemRepository orderItemRepository;

    // Purchase Order Methods
    public List<PurchaseOrder> getAllPurchaseOrders() {
        return purchaseOrderRepository.findAll();
    }

    public PurchaseOrder getPurchaseOrderById(Integer id) {
        return purchaseOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Purchase order not found"));
    }

    public PurchaseOrder createPurchaseOrder(PurchaseOrder po) {
        return purchaseOrderRepository.save(po);
    }

    public PurchaseOrder updatePurchaseOrderStatus(Integer id, String status) {
        PurchaseOrder po = getPurchaseOrderById(id);
        po.setStatus(status);
        po.setUpdatedAt(LocalDateTime.now());
        return purchaseOrderRepository.save(po);
    }

    // Sales Order Methods
    public List<SalesOrder> getAllSalesOrders() {
        return salesOrderRepository.findAll();
    }

    public SalesOrder getSalesOrderById(Integer id) {
        return salesOrderRepository.findById(id).orElseThrow(() -> new RuntimeException("Sales order not found"));
    }

    public SalesOrder createSalesOrder(SalesOrder so) {
        return salesOrderRepository.save(so);
    }

    public SalesOrder updateSalesOrderStatus(Integer id, String status) {
        SalesOrder so = getSalesOrderById(id);
        so.setStatus(status);
        so.setUpdatedAt(LocalDateTime.now());
        return salesOrderRepository.save(so);
    }
}

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class LocationService {
    private final LocationRepository locationRepository;

    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    public Location getLocationById(Integer id) {
        return locationRepository.findById(id).orElseThrow(() -> new RuntimeException("Location not found"));
    }

    public List<Location> getWarehouseLocations(Integer warehouseId) {
        return locationRepository.findByWarehouseId(warehouseId);
    }

    public Location createLocation(Location location) {
        return locationRepository.save(location);
    }

    public Location updateLocation(Integer id, Location location) {
        Location existing = getLocationById(id);
        existing.setRackNumber(location.getRackNumber());
        existing.setShelfNumber(location.getShelfNumber());
        existing.setBinNumber(location.getBinNumber());
        existing.setCapacity(location.getCapacity());
        return locationRepository.save(existing);
    }

    public void deleteLocation(Integer id) {
        locationRepository.deleteById(id);
    }
}

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class StockMovementService {
    private final StockMovementRepository movementRepository;

    public List<StockMovement> getAllMovements() {
        return movementRepository.findAll();
    }

    public StockMovement getMovementById(Integer id) {
        return movementRepository.findById(id).orElseThrow(() -> new RuntimeException("Movement not found"));
    }

    public List<StockMovement> getProductMovements(Integer productId) {
        return movementRepository.findByProductId(productId);
    }

    public List<StockMovement> getWarehouseMovements(Integer warehouseId) {
        return movementRepository.findByWarehouseId(warehouseId);
    }

    public List<StockMovement> getMovementsByType(MovementType type) {
        return movementRepository.findByType(type);
    }
}

