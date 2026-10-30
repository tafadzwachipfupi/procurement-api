package com.chipfupi.procurement.service;

import com.chipfupi.procurement.dto.*;
import com.chipfupi.procurement.entity.*;
import com.chipfupi.procurement.exception.BusinessRuleException;
import com.chipfupi.procurement.repository.PurchaseOrderRepository;
import com.chipfupi.procurement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Year;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class PurchaseOrderService {

    private final PurchaseOrderRepository poRepository;
    private final SupplierRepository supplierRepository;

    public PurchaseOrderService(PurchaseOrderRepository poRepository, SupplierRepository supplierRepository) {
        this.poRepository = poRepository;
        this.supplierRepository = supplierRepository;
    }

    @Transactional
    public PurchaseOrderResponse createPurchaseOrder(PurchaseOrderRequest request) {
        // 1. Find the supplier
        Supplier supplier = supplierRepository.findById(request.supplierId())
            .orElseThrow(() -> new BusinessRuleException("Supplier not found with ID: " + request.supplierId()));

        // 2. Create the PO header
        PurchaseOrder po = new PurchaseOrder();
        po.setSupplier(supplier);
        po.setOrderDate(request.orderDate());
        po.setPoNumber("PO-" + Year.now().getValue() + "-" + ThreadLocalRandom.current().nextInt(10000, 99999));

        // 3. Add the lines and calculate the total
        for (PurchaseOrderLineRequest lineReq : request.lines()) {
            PurchaseOrderLine line = new PurchaseOrderLine();
            line.setDescription(lineReq.description());
            line.setQuantity(lineReq.quantity());
            line.setUnitPrice(lineReq.unitPrice());
            po.addLine(line); // Our helper method ensures the line knows which PO it belongs to
        }

        // Calculate grand total
        BigDecimal total = po.getLines().stream()
            .map(PurchaseOrderLine::getLineTotal)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
        po.setTotalAmount(total);

        // 4. Save (Because of CascadeType.ALL in the Entity, this saves the PO AND the lines)
        PurchaseOrder savedPo = poRepository.save(po);
        return PurchaseOrderResponse.from(savedPo);
    }

    public List<PurchaseOrderResponse> getAllPurchaseOrders() {
        return poRepository.findAll().stream().map(PurchaseOrderResponse::from).toList();
    }

    // --- LIFECYCLE LOGIC ---

    @Transactional
    public PurchaseOrderResponse approvePurchaseOrder(Long id) {
        PurchaseOrder po = getPoById(id);
        
        if (po.getStatus() != PurchaseOrderStatus.DRAFT) {
            throw new BusinessRuleException("Only DRAFT purchase orders can be approved. Current status: " + po.getStatus());
        }
        
        po.setStatus(PurchaseOrderStatus.APPROVED);
        return PurchaseOrderResponse.from(poRepository.save(po));
    }

    @Transactional
    public PurchaseOrderResponse receivePurchaseOrder(Long id) {
        PurchaseOrder po = getPoById(id);
        
        if (po.getStatus() != PurchaseOrderStatus.APPROVED) {
            throw new BusinessRuleException("Only APPROVED purchase orders can be received. Current status: " + po.getStatus());
        }
        
        po.setStatus(PurchaseOrderStatus.RECEIVED);
        return PurchaseOrderResponse.from(poRepository.save(po));
    }

    private PurchaseOrder getPoById(Long id) {
        return poRepository.findById(id)
            .orElseThrow(() -> new BusinessRuleException("Purchase Order not found with ID: " + id));
    }
}