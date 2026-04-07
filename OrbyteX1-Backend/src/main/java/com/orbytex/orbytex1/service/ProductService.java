package com.orbytex.orbytex1.service;

import com.orbytex.orbytex1.dto.ProductDTO;
import com.orbytex.orbytex1.entity.Product;
import com.orbytex.orbytex1.exception.DuplicateResourceException;
import com.orbytex.orbytex1.exception.ResourceNotFoundException;
import com.orbytex.orbytex1.repository.ProductRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class ProductService {
    private ProductRepository productRepository;
    private ModelMapper modelMapper;

    public ProductDTO createProduct(ProductDTO productDTO) {
        // Check if SKU already exists
        if (productRepository.findBySku(productDTO.getSku()).isPresent()) {
            throw new DuplicateResourceException("Product with SKU " + productDTO.getSku() + " already exists");
        }

        Product product = modelMapper.map(productDTO, Product.class);
        product.setIsActive(true);
        Product saved = productRepository.save(product);

        return mapToDTO(saved);
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));

        // Check if SKU is being changed and if new SKU is unique
        if (!product.getSku().equals(productDTO.getSku()) &&
            productRepository.findBySku(productDTO.getSku()).isPresent()) {
            throw new DuplicateResourceException("Product with SKU " + productDTO.getSku() + " already exists");
        }

        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setSku(productDTO.getSku());
        product.setCostPrice(productDTO.getCostPrice());
        product.setSellingPrice(productDTO.getSellingPrice());
        product.setReorderLevel(productDTO.getReorderLevel());

        Product updated = productRepository.save(product);
        return mapToDTO(updated);
    }

    @Transactional(readOnly = true)
    public ProductDTO getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return mapToDTO(product);
    }

    @Transactional(readOnly = true)
    public Page<ProductDTO> searchProducts(String searchTerm, Pageable pageable) {
        return productRepository.findByNameContainingIgnoreCaseAndIsActiveTrue(searchTerm, pageable)
                .map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getLowStockProducts() {
        return productRepository.findLowStockProducts().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<ProductDTO> getAllProducts() {
        return productRepository.findByIsActiveTrue().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        product.setIsActive(false);
        productRepository.save(product);
        log.info("Product deleted: {}", id);
    }

    private ProductDTO mapToDTO(Product product) {
        ProductDTO dto = modelMapper.map(product, ProductDTO.class);
        if (product.getCategory() != null) {
            dto.setCategoryName(product.getCategory().getName());
        }
        if (product.getBrand() != null) {
            dto.setBrandName(product.getBrand().getName());
        }
        if (product.getInventory() != null) {
            dto.setQuantityOnHand(product.getInventory().getQuantityOnHand());
            dto.setQuantityAvailable(product.getInventory().getQuantityAvailable());
        }
        // Calculate margin
        if (product.getCostPrice() != null && product.getSellingPrice() != null) {
            BigDecimal margin = product.getSellingPrice().subtract(product.getCostPrice());
            dto.setMargin(margin);
        }
        return dto;
    }
}

