package com.orbytex.orbytex1.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    private Long id;
    private String username;
    private String email;
    private String firstName;
    private String lastName;
    private String roleName;
    private Boolean isActive;
    private LocalDateTime lastLogin;
    private LocalDateTime createdAt;

    public String getFullName() {
        return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : "");
    }
}

