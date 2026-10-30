package com.chipfupi.procurement.controller;

import com.chipfupi.procurement.dto.PurchaseOrderRequest;
import com.chipfupi.procurement.dto.PurchaseOrderResponse;
import com.chipfupi.procurement.service.PurchaseOrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/purchase-orders")
public class PurchaseOrderController {

    private final PurchaseOrderService service;

    public PurchaseOrderController(PurchaseOrderService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PurchaseOrderResponse> create(@Valid @RequestBody PurchaseOrderRequest request) {
        return new ResponseEntity<>(service.createPurchaseOrder(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<PurchaseOrderResponse>> getAll() {
        return ResponseEntity.ok(service.getAllPurchaseOrders());
    }

    // Use PATCH because we are only updating the status, not the whole object
    @PatchMapping("/{id}/approve")
    public ResponseEntity<PurchaseOrderResponse> approve(@PathVariable Long id) {
        return ResponseEntity.ok(service.approvePurchaseOrder(id));
    }

    @PatchMapping("/{id}/receive")
    public ResponseEntity<PurchaseOrderResponse> receive(@PathVariable Long id) {
        return ResponseEntity.ok(service.receivePurchaseOrder(id));
    }
}