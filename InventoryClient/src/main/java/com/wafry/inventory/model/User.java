package com.wafry.inventory.model;

import java.time.LocalDateTime;

/**
 * User - Entity class representing a system user
 *
 * Properties:
 * - Authentication (username, email)
 * - Profile (full name, role)
 * - Status tracking
 *
 * @author Wafry Team
 */
public class User {
    private Integer id;
    private String username;
    private String email;
    private String fullName;
    private String role; // ADMIN, MANAGER, STAFF
    private String status; // ACTIVE, INACTIVE
    private Integer branchId;
    private LocalDateTime createdAt;
    private LocalDateTime lastLogin;

    // Constructors
    public User() {}

    public User(String username, String email, String fullName, String role) {
        this.username = username;
        this.email = email;
        this.fullName = fullName;
        this.role = role;
        this.status = "ACTIVE";
    }

    // Getters & Setters
    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Integer getBranchId() { return branchId; }
    public void setBranchId(Integer branchId) { this.branchId = branchId; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getLastLogin() { return lastLogin; }
    public void setLastLogin(LocalDateTime lastLogin) { this.lastLogin = lastLogin; }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", fullName='" + fullName + '\'' +
                ", role='" + role + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id != null && id.equals(user.id);
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}

