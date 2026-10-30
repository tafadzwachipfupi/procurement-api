package com.chipfupi.procurement.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record SupplierRequest(
    @NotBlank(message = "Supplier name is required")
    @Size(max = 150)
    String name,

    @Size(max = 50)
    String taxNumber,

    @Email(message = "Please provide a valid email address")
    String email
) {}