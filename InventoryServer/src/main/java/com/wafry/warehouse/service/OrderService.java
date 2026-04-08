package com.wafry.warehouse.service;

import com.wafry.warehouse.dto.OrderDTO;
import com.wafry.warehouse.entity.Order;
import com.wafry.warehouse.entity.Customer;
import com.wafry.warehouse.exception.ResourceNotFoundException;
import com.wafry.warehouse.repository.OrderRepository;
import com.wafry.warehouse.repository.CustomerRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional
public class OrderService {
    private static final Logger log = LoggerFactory.getLogger(OrderService.class);
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ModelMapper modelMapper;

    public OrderDTO createOrder(OrderDTO orderDTO) {
        Customer customer = customerRepository.findById(orderDTO.getCustomerId())
                .orElseThrow(() -> new ResourceNotFoundException("Customer not found"));

        Order order = modelMapper.map(orderDTO, Order.class);
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");

        Order saved = orderRepository.save(order);
        log.info("Order created: {}", saved.getOrderNumber());

        return mapToDTO(saved);
    }

    public OrderDTO updateOrder(Long id, OrderDTO orderDTO) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));

        order.setStatus(orderDTO.getStatus());
        order.setPaymentMethod(orderDTO.getPaymentMethod());
        order.setNotes(orderDTO.getNotes());

        Order updated = orderRepository.save(order);
        return mapToDTO(updated);
    }

    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        return mapToDTO(order);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByCustomer(Long customerId, Pageable pageable) {
        return orderRepository.findByCustomer_IdOrderByCreatedAtDesc(customerId, pageable)
                .map(this::mapToDTO);
    }

    @Transactional(readOnly = true)
    public Page<OrderDTO> getOrdersByStatus(String status, Pageable pageable) {
        return orderRepository.findByStatusOrderByCreatedAtDesc(status, pageable)
                .map(this::mapToDTO);
    }

    public void deleteOrder(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found"));
        orderRepository.delete(order);
        log.info("Order deleted: {}", id);
    }

    private OrderDTO mapToDTO(Order order) {
        OrderDTO dto = modelMapper.map(order, OrderDTO.class);
        if (order.getCustomer() != null) {
            dto.setCustomerName(order.getCustomer().getCustomerName());
        }
        return dto;
    }
}

