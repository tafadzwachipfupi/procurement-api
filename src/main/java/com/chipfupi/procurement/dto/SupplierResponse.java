package com.chipfupi.procurement.dto;

import com.chipfupi.procurement.entity.Supplier;
import java.time.LocalDateTime;

public record SupplierResponse(
    Long id,
    String name,
    String taxNumber,
    String email,
    String status,
    LocalDateTime createdAt
) {
    // Helper method to convert a Database Entity into a Response
    public static SupplierResponse from(Supplier supplier) {
        return new SupplierResponse(
            supplier.getId(),
            supplier.getName(),
            supplier.getTaxNumber(),
            supplier.getEmail(),
            supplier.getStatus(),
            supplier.getCreatedAt()
        );
    }
}