package com.chipfupi.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;

public record PurchaseOrderLineRequest(
    @NotBlank(message = "Description is required")
    String description,

    @NotNull(message = "Quantity is required")
    @Positive(message = "Quantity must be greater than zero")
    Integer quantity,

    @NotNull(message = "Unit price is required")
    @Positive(message = "Unit price must be greater than zero")
    BigDecimal unitPrice
) {}