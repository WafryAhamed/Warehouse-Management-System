package com.orbytex.orbytex1.model;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Supplier {
    private Long id;
    private String supplierName;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;
    private String city;
    private String state;
    private String postalCode;
    private String paymentTerms;
    private Boolean isActive;
    private LocalDateTime createdAt;
}

