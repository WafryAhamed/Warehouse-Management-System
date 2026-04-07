package com.wafry.warehouse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxying;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * WarehouseApplication - Main entry point for Spring Boot backend
 *
 * Features:
 * - REST API for Warehouse Management System
 * - JWT Authentication
 * - PostgreSQL Database
 * - Production-ready security
 *
 * @author Wafry Team
 */
@SpringBootApplication
@EnableAsync
@EnableAspectJAutoProxying
public class WarehouseApplication {

    public static void main(String[] args) {
        SpringApplication.run(WarehouseApplication.class, args);
    }
}

