package com.orbytex.orbytex1.service;

import com.orbytex.orbytex1.entity.Customer;
import com.orbytex.orbytex1.exception.DuplicateResourceException;
import com.orbytex.orbytex1.exception.ResourceNotFoundException;
import com.orbytex.orbytex1.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
@Transactional
public class CustomerService {
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    public Customer createCustomer(Customer customer) {
        if (customer.getEmail() != null && customerRepository.findByEmail(customer.getEmail()).isPresent()) {
            throw new DuplicateResourceException("Customer with email " + customer.getEmail() + " already exists");
        }
        customer.setIsActive(true);
        Customer saved = customerRepository.save(customer);
        log.info("Customer created: {}", saved.getId());
        return saved;
    }

    public Customer updateCustomer(Long id, Customer customer) {
        Customer existing = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        existing.setCustomerName(customer.getCustomerName());
        existing.setEmail(customer.getEmail());
        existing.setPhone(customer.getPhone());
        existing.setAddress(customer.getAddress());
        existing.setCity(customer.getCity());
        existing.setState(customer.getState());
        existing.setPostalCode(customer.getPostalCode());
        existing.setCustomerType(customer.getCustomerType());

        Customer updated = customerRepository.save(existing);
        return updated;
    }

    @Transactional(readOnly = true)
    public Customer getCustomerById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
    }

    @Transactional(readOnly = true)
    public List<Customer> getAllCustomers() {
        return customerRepository.findByIsActiveTrue();
    }

    @Transactional(readOnly = true)
    public Page<Customer> searchCustomers(String name, Pageable pageable) {
        return customerRepository.findByCustomerNameContainingIgnoreCaseAndIsActiveTrue(name, pageable);
    }

    public void deleteCustomer(Long id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));
        customer.setIsActive(false);
        customerRepository.save(customer);
        log.info("Customer deleted: {}", id);
    }
}

