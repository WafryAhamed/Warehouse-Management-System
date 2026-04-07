package com.orbytex.orbytex1.repository;

import com.orbytex.orbytex1.entity.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByEmail(String email);
    Optional<Customer> findByPhone(String phone);
    List<Customer> findByIsActiveTrue();
    Page<Customer> findByCustomerNameContainingIgnoreCaseAndIsActiveTrue(String name, Pageable pageable);
    Page<Customer> findByCustomerTypeAndIsActiveTrue(String type, Pageable pageable);
}

