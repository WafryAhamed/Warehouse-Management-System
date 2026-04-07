package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.StockMovement;
import com.wafry.warehouse.repository.StockMovementRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockMovementService {
    private final StockMovementRepository stockMovementRepository;

    public StockMovementService(StockMovementRepository stockMovementRepository) {
        this.stockMovementRepository = stockMovementRepository;
    }

    public StockMovement createMovement(StockMovement movement) {
        return stockMovementRepository.save(movement);
    }

    public List<StockMovement> getAllMovements() {
        return stockMovementRepository.findAll();
    }

    public Optional<StockMovement> getMovement(Integer id) {
        return stockMovementRepository.findById(id);
    }
}

