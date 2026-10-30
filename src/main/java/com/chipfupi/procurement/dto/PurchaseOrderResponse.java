package com.chipfupi.procurement.dto;

import com.chipfupi.procurement.entity.PurchaseOrder;
import com.chipfupi.procurement.entity.PurchaseOrderStatus;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record PurchaseOrderResponse(
    Long id,
    String poNumber,
    Long supplierId,
    String supplierName,
    PurchaseOrderStatus status,
    LocalDate orderDate,
    BigDecimal totalAmount,
    List<PurchaseOrderLineResponse> lines
) {
    public static PurchaseOrderResponse from(PurchaseOrder po) {
        return new PurchaseOrderResponse(
            po.getId(),
            po.getPoNumber(),
            po.getSupplier().getId(),
            po.getSupplier().getName(),
            po.getStatus(),
            po.getOrderDate(),
            po.getTotalAmount(),
            po.getLines().stream().map(PurchaseOrderLineResponse::from).toList()
        );
    }
}