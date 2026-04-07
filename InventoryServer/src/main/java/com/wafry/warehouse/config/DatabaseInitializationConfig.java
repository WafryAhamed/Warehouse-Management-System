package com.wafry.warehouse.config;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import javax.sql.DataSource;
import java.sql.Connection;

/**
 * Database Initialization Configuration
 * Verifies database connection on application startup
 */
@Configuration
public class DatabaseInitializationConfig {

    private final DataSource dataSource;

    public DatabaseInitializationConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean
    public ApplicationRunner databaseConnectionVerifier() {
        return args -> verifyDatabaseConnection();
    }

    private void verifyDatabaseConnection() {
        try (Connection connection = dataSource.getConnection()) {
            System.out.println("✅ Connected to database: " + connection.getMetaData().getDatabaseProductName());
            System.out.println("✅ Database version: " + connection.getMetaData().getDatabaseProductVersion());
            System.out.println("✅ Connection pool is healthy");
        } catch (Exception e) {
            System.err.println("Database connection error: " + e.getMessage());
            throw new RuntimeException("Cannot connect to database. Check your configuration.", e);
        }
    }
}

