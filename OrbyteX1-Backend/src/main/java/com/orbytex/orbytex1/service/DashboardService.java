package com.orbytex.orbytex1.service;

import com.orbytex.orbytex1.dto.DashboardStatsDTO;
import com.orbytex.orbytex1.repository.*;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

@Slf4j
@Service
@AllArgsConstructor
@Transactional(readOnly = true)
public class DashboardService {
    private OrderRepository orderRepository;
    private CustomerRepository customerRepository;
    private ProductRepository productRepository;
    private ExpenseRepository expenseRepository;
    private UserRepository userRepository;

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

