package com.chipfupi.procurement.dto;

import com.chipfupi.procurement.entity.Invoice;
import com.chipfupi.procurement.entity.InvoiceStatus;
import java.math.BigDecimal;
import java.time.LocalDate;

public record InvoiceResponse(
    Long id,
    String invoiceNumber,
    Long purchaseOrderId,
    String poNumber,
    LocalDate invoiceDate,
    LocalDate dueDate,
    BigDecimal amount,
    InvoiceStatus status,
    LocalDate paidDate
) {
    public static InvoiceResponse from(Invoice invoice) {
        return new InvoiceResponse(
            invoice.getId(),
            invoice.getInvoiceNumber(),
            invoice.getPurchaseOrder().getId(),
            invoice.getPurchaseOrder().getPoNumber(),
            invoice.getInvoiceDate(),
            invoice.getDueDate(),
            invoice.getAmount(),
            invoice.getStatus(),
            invoice.getPaidDate()
        );
    }
}