package com.chipfupi.procurement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceRequest(
    @NotBlank(message = "Invoice number is required")
    String invoiceNumber,

    @NotNull(message = "Purchase Order ID is required")
    Long purchaseOrderId,

    @NotNull(message = "Invoice date is required")
    LocalDate invoiceDate,

    @NotNull(message = "Due date is required")
    LocalDate dueDate,

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be greater than zero")
    BigDecimal amount
) {}