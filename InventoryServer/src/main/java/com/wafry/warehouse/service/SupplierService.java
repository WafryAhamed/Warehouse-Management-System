package com.wafry.warehouse.service;

import com.wafry.warehouse.entity.Supplier;
import com.wafry.warehouse.exception.DuplicateResourceException;
import com.wafry.warehouse.exception.ResourceNotFoundException;
import com.wafry.warehouse.repository.SupplierRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class SupplierService {
    private static final Logger log = LoggerFactory.getLogger(SupplierService.class);
    private SupplierRepository supplierRepository;

    public SupplierService(SupplierRepository supplierRepository) {
        this.supplierRepository = supplierRepository;
    }

    public Supplier createSupplier(Supplier supplier) {
        if (supplierRepository.findBySupplierName(supplier.getSupplierName()).isPresent()) {
            throw new DuplicateResourceException("Supplier already exists");
        }
        supplier.setIsActive(true);
        Supplier saved = supplierRepository.save(supplier);
        log.info("Supplier created: {}", saved.getId());
        return saved;
    }

    public Supplier updateSupplier(Long id, Supplier supplier) {
        Supplier existing = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));

        existing.setSupplierName(supplier.getSupplierName());
        existing.setContactPerson(supplier.getContactPerson());
        existing.setEmail(supplier.getEmail());
        existing.setPhone(supplier.getPhone());
        existing.setAddress(supplier.getAddress());
        existing.setPaymentTerms(supplier.getPaymentTerms());

        Supplier updated = supplierRepository.save(existing);
        return updated;
    }

    @Transactional(readOnly = true)
    public Supplier getSupplierById(Long id) {
        return supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
    }

    @Transactional(readOnly = true)
    public Page<Supplier> getAllSuppliers(Pageable pageable) {
        return supplierRepository.findByIsActiveTrue(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Supplier> searchSuppliers(String name, Pageable pageable) {
        return supplierRepository.findBySupplierNameContainingIgnoreCaseAndIsActiveTrue(name, pageable);
    }

    public void deleteSupplier(Long id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found"));
        supplier.setIsActive(false);
        supplierRepository.save(supplier);
        log.info("Supplier deleted: {}", id);
    }
}

