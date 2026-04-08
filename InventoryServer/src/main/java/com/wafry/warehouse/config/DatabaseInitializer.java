package com.wafry.warehouse.config;

import com.wafry.warehouse.entity.Role;
import com.wafry.warehouse.entity.User;
import com.wafry.warehouse.repository.RoleRepository;
import com.wafry.warehouse.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class DatabaseInitializer {
    private static final Logger log = LoggerFactory.getLogger(DatabaseInitializer.class);

    @Bean
    @Transactional
    public CommandLineRunner initializeDatabase(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            try {
                // Check if roles already exist
                if (roleRepository.count() == 0) {
                    log.info("Initializing roles...");

                    // Create all required roles
                    Role superAdmin = new Role();
                    superAdmin.setRoleName("SUPER_ADMIN");
                    superAdmin.setDescription("Full system access");
                    roleRepository.save(superAdmin);

                    Role admin = new Role();
                    admin.setRoleName("ADMIN");
                    admin.setDescription("Administrative access");
                    roleRepository.save(admin);

                    Role manager = new Role();
                    manager.setRoleName("MANAGER");
                    manager.setDescription("Manager access");
                    roleRepository.save(manager);

                    Role inventoryManager = new Role();
                    inventoryManager.setRoleName("INVENTORY_MANAGER");
                    inventoryManager.setDescription("Inventory operations");
                    roleRepository.save(inventoryManager);

                    Role cashier = new Role();
                    cashier.setRoleName("CASHIER");
                    cashier.setDescription("Point of sale operations");
                    roleRepository.save(cashier);

                    Role salesAnalyst = new Role();
                    salesAnalyst.setRoleName("SALES_ANALYST");
                    salesAnalyst.setDescription("Analytics and reporting");
                    roleRepository.save(salesAnalyst);

                    Role customerSupport = new Role();
                    customerSupport.setRoleName("CUSTOMER_SUPPORT");
                    customerSupport.setDescription("Customer service");
                    roleRepository.save(customerSupport);

                    log.info("Roles initialized successfully");
                }

                // Check if default admin user exists
                if (userRepository.findByUsername("admin").isEmpty()) {
                    log.info("Creating default admin user...");

                    Role superAdminRole = roleRepository.findByRoleName("SUPER_ADMIN")
                            .orElseThrow(() -> new RuntimeException("SUPER_ADMIN role not found"));

                    User adminUser = new User();
                    adminUser.setUsername("admin");
                    adminUser.setEmail("admin@inventory.local");
                    adminUser.setPassword(passwordEncoder.encode("admin123"));
                    adminUser.setFirstName("Admin");
                    adminUser.setLastName("User");
                    adminUser.setRole(superAdminRole);
                    adminUser.setIsActive(Boolean.TRUE);  // Explicitly set to TRUE
                    adminUser.setCreatedAt(java.time.LocalDateTime.now());
                    adminUser.setUpdatedAt(java.time.LocalDateTime.now());

                    userRepository.save(adminUser);
                    log.info("Default admin user created successfully");
                }

                log.info("Database initialization completed successfully");
            } catch (Exception e) {
                log.error("Error initializing database", e);
                throw e;
            }
        };
    }
}

