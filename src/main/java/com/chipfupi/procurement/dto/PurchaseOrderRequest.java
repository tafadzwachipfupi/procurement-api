package com.chipfupi.procurement.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderRequest(
    @NotNull(message = "Supplier ID is required")
    Long supplierId,

    @NotNull(message = "Order date is required")
    LocalDate orderDate,

    @NotEmpty(message = "A purchase order must have at least one line item")
    @Valid // This tells Spring to also validate the items inside the list
    List<PurchaseOrderLineRequest> lines
) {}