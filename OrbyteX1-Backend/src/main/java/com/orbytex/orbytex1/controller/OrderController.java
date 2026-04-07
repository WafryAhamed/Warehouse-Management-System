package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.dto.OrderDTO;
import com.orbytex.orbytex1.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

@Slf4j
@RestController
@RequestMapping("/api/v1/orders")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class OrderController {
    private OrderService orderService;

    @PostMapping
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CASHIER')")
    public ResponseEntity<OrderDTO> createOrder(@Valid @RequestBody OrderDTO orderDTO) {
        log.info("Creating new order for customer: {}", orderDTO.getCustomerId());
        OrderDTO created = orderService.createOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<OrderDTO> updateOrder(@PathVariable Long id, @Valid @RequestBody OrderDTO orderDTO) {
        log.info("Updating order: {}", id);
        OrderDTO updated = orderService.updateOrder(id, orderDTO);
        return ResponseEntity.ok(updated);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CASHIER')")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderService.getOrderById(id);
        return ResponseEntity.ok(order);
    }

    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER', 'CUSTOMER_SUPPORT')")
    public ResponseEntity<Page<OrderDTO>> getOrdersByCustomer(
            @PathVariable Long customerId,
            Pageable pageable) {
        log.info("Fetching orders for customer: {}", customerId);
        Page<OrderDTO> orders = orderService.getOrdersByCustomer(customerId, pageable);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/status/{status}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<Page<OrderDTO>> getOrdersByStatus(
            @PathVariable String status,
            Pageable pageable) {
        log.info("Fetching orders with status: {}", status);
        Page<OrderDTO> orders = orderService.getOrdersByStatus(status, pageable);
        return ResponseEntity.ok(orders);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN')")
    public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
        log.info("Deleting order: {}", id);
        orderService.deleteOrder(id);
        return ResponseEntity.ok("Order deleted successfully");
    }
}

