package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.Warehouse;
import com.wafry.warehouse.repository.WarehouseRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class WarehouseService {
    private final WarehouseRepository warehouseRepository;
    
    public WarehouseService(WarehouseRepository warehouseRepository) {
        this.warehouseRepository = warehouseRepository;
    }

    public Warehouse createWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    
    public Warehouse updateWarehouse(Warehouse warehouse) {
        return warehouseRepository.save(warehouse);
    }
    
    public void deleteWarehouse(Integer id) {
        warehouseRepository.deleteById(id);
    }
    
    public Optional<Warehouse> getWarehouse(Integer id) {
        return warehouseRepository.findById(id);
    }
    
    public List<Warehouse> getAllWarehouses() {
        return warehouseRepository.findAll();
    }
}

