package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.Product;
import com.wafry.warehouse.repository.ProductRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }
    
    public Product updateProduct(Product product) {
        return productRepository.save(product);
    }
    
    public void deleteProduct(Integer id) {
        productRepository.deleteById(id);
    }
    
    public Optional<Product> getProduct(Integer id) {
        return productRepository.findById(id);
    }
    
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}

