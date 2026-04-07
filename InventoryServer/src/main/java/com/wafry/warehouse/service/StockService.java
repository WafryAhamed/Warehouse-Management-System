package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.Stock;
import com.wafry.warehouse.repository.StockRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {
    private final StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public Stock createStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public Stock updateStock(Stock stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(Integer id) {
        stockRepository.deleteById(id);
    }

    public Optional<Stock> getStock(Integer id) {
        return stockRepository.findById(id);
    }

    public List<Stock> getAllStock() {
        return stockRepository.findAll();
    }
}

