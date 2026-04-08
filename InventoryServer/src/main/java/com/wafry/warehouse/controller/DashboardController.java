package com.wafry.warehouse.controller;

import com.wafry.warehouse.dto.DashboardStatsDTO;
import com.wafry.warehouse.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/v1/dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {
    private static final Logger log = LoggerFactory.getLogger(DashboardController.class);
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<DashboardStatsDTO> getSystemStats() {
        log.info("Fetching system dashboard stats");
        DashboardStatsDTO stats = dashboardService.getSystemStats();
        return ResponseEntity.ok(stats);
    }
}

