package com.wafry.warehouse.service;

import com.wafry.warehouse.dto.DashboardStatsDTO;
import com.wafry.warehouse.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@Transactional(readOnly = true)
public class DashboardService {
    private static final Logger log = LoggerFactory.getLogger(DashboardService.class);
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ExpenseRepository expenseRepository;
    private UserRepository userRepository;

    public DashboardService(OrderRepository orderRepository, CustomerRepository customerRepository,
                           ProductRepository productRepository, ExpenseRepository expenseRepository,
                           UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.expenseRepository = expenseRepository;
        this.userRepository = userRepository;
    }

    public DashboardStatsDTO getSystemStats() {
        try {
            Integer totalOrders = orderRepository.countCompletedOrders();
            Integer totalCustomers = (int) customerRepository.findByIsActiveTrue().size();
            Integer totalProducts = (int) productRepository.findByIsActiveTrue().size();
            Integer lowStockProducts = (int) productRepository.findLowStockProducts().size();
            Integer activeUsers = (int) userRepository.findByIsActiveTrue().size();

            // Calculate revenue for current month
            LocalDateTime startOfMonth = LocalDateTime.now().with(TemporalAdjusters.firstDayOfMonth());
            BigDecimal totalRevenue = orderRepository.getTotalRevenueSince(startOfMonth);
            if (totalRevenue == null) {
                totalRevenue = BigDecimal.ZERO;
            }

            // Calculate expenses
            BigDecimal totalExpenses = expenseRepository.getTotalExpensesSince(startOfMonth);
            if (totalExpenses == null) {
                totalExpenses = BigDecimal.ZERO;
            }

            BigDecimal netProfit = totalRevenue.subtract(totalExpenses);

            return DashboardStatsDTO.builder()
                    .totalRevenue(totalRevenue)
                    .totalOrders(totalOrders != null ? totalOrders : 0)
                    .totalCustomers(totalCustomers)
                    .totalProducts(totalProducts)
                    .lowStockProducts(lowStockProducts)
                    .totalExpenses(totalExpenses)
                    .netProfit(netProfit)
                    .activeUsers(activeUsers)
                    .lastUpdated(LocalDateTime.now())
                    .build();
        } catch (Exception e) {
            log.error("Error fetching dashboard stats", e);
            return DashboardStatsDTO.builder()
                    .totalRevenue(BigDecimal.ZERO)
                    .totalOrders(0)
                    .totalCustomers(0)
                    .totalProducts(0)
                    .lowStockProducts(0)
                    .totalExpenses(BigDecimal.ZERO)
                    .netProfit(BigDecimal.ZERO)
                    .activeUsers(0)
                    .lastUpdated(LocalDateTime.now())
                    .build();
        }
    }
}

