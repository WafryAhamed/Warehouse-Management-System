package com.orbytex.orbytex1.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer {
    private Long id;
    private String customerName;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String customerType;
    private Integer loyaltyPoints;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

