package com.orbytex.orbytex1.service;

import com.orbytex.orbytex1.entity.Supplier;
import com.orbytex.orbytex1.exception.DuplicateResourceException;
import com.orbytex.orbytex1.exception.ResourceNotFoundException;
import com.orbytex.orbytex1.repository.SupplierRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class SupplierService {
    private SupplierRepository supplierRepository;

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

