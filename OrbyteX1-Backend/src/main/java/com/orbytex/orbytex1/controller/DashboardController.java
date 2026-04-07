package com.orbytex.orbytex1.controller;

import com.orbytex.orbytex1.dto.DashboardStatsDTO;
import com.orbytex.orbytex1.service.DashboardService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/v1/dashboard")
@AllArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class DashboardController {
    private DashboardService dashboardService;

    @GetMapping("/stats")
    @PreAuthorize("hasAnyRole('SUPER_ADMIN', 'ADMIN', 'MANAGER')")
    public ResponseEntity<DashboardStatsDTO> getSystemStats() {
        log.info("Fetching system dashboard stats");
        DashboardStatsDTO stats = dashboardService.getSystemStats();
        return ResponseEntity.ok(stats);
    }
}

