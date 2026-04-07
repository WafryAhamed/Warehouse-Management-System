package com.orbytex.orbytex1.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import jakarta.sql.DataSource;
import java.sql.Connection;

@Slf4j
@Configuration
public class DatabaseInitializer {

    @Bean
    public CommandLineRunner initializeDatabase(DataSource dataSource) {
        return args -> {
            try (Connection connection = dataSource.getConnection()) {
                // Execute schema.sql
                ScriptUtils.executeSqlScript(connection, new ClassPathResource("db/schema.sql"));
                log.info("Database schema initialized successfully");
            } catch (Exception e) {
                log.error("Error initializing database", e);
            }
        };
    }
}

