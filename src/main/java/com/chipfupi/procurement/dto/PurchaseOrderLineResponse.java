package com.chipfupi.procurement.dto;

import com.chipfupi.procurement.entity.PurchaseOrderLine;
import java.math.BigDecimal;

public record PurchaseOrderLineResponse(
    Long id,
    String description,
    Integer quantity,
    BigDecimal unitPrice,
    BigDecimal lineTotal
) {
    public static PurchaseOrderLineResponse from(PurchaseOrderLine line) {
        return new PurchaseOrderLineResponse(
            line.getId(),
            line.getDescription(),
            line.getQuantity(),
            line.getUnitPrice(),
            line.getLineTotal()
        );
    }
}