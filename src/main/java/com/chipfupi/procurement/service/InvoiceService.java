package com.chipfupi.procurement.service;

import com.chipfupi.procurement.dto.InvoiceRequest;
import com.chipfupi.procurement.dto.InvoiceResponse;
import com.chipfupi.procurement.entity.Invoice;
import com.chipfupi.procurement.entity.InvoiceStatus;
import com.chipfupi.procurement.entity.PurchaseOrder;
import com.chipfupi.procurement.entity.PurchaseOrderStatus;
import com.chipfupi.procurement.exception.BusinessRuleException;
import com.chipfupi.procurement.repository.InvoiceRepository;
import com.chipfupi.procurement.repository.PurchaseOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class InvoiceService {

    private final InvoiceRepository invoiceRepository;
    private final PurchaseOrderRepository poRepository;

    public InvoiceService(InvoiceRepository invoiceRepository, PurchaseOrderRepository poRepository) {
        this.invoiceRepository = invoiceRepository;
        this.poRepository = poRepository;
    }

    @Transactional
    public InvoiceResponse createInvoice(InvoiceRequest request) {
        PurchaseOrder po = poRepository.findById(request.purchaseOrderId())
            .orElseThrow(() -> new BusinessRuleException("Purchase Order not found"));

        // Business Rule 1: Can only invoice a RECEIVED order
        if (po.getStatus() != PurchaseOrderStatus.RECEIVED) {
            throw new BusinessRuleException("Invoices can only be created against RECEIVED purchase orders.");
        }

        // Business Rule 2: Invoice amount cannot exceed the PO total
        if (request.amount().compareTo(po.getTotalAmount()) > 0) {
            throw new BusinessRuleException("Invoice amount exceeds the Purchase Order total of " + po.getTotalAmount());
        }

        Invoice invoice = new Invoice();
        invoice.setInvoiceNumber(request.invoiceNumber());
        invoice.setPurchaseOrder(po);
        invoice.setInvoiceDate(request.invoiceDate());
        invoice.setDueDate(request.dueDate());
        invoice.setAmount(request.amount());

        return InvoiceResponse.from(invoiceRepository.save(invoice));
    }

    // ADDED: @Transactional(readOnly = true) to fix the LazyInitializationException
    @Transactional(readOnly = true) 
    public List<InvoiceResponse> getAllInvoices() {
        return invoiceRepository.findAll().stream().map(InvoiceResponse::from).toList();
    }

    @Transactional(readOnly = true) // Added here as well, as a best practice for read-only operations
    public Map<String, Object> getAgeingReport() {
        return invoiceRepository.getAgeingReport();
    }
}