package com.chipfupi.procurement.controller;

import com.chipfupi.procurement.dto.InvoiceRequest;
import com.chipfupi.procurement.dto.InvoiceResponse;
import com.chipfupi.procurement.service.InvoiceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/invoices")
public class InvoiceController {

    private final InvoiceService service;

    public InvoiceController(InvoiceService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InvoiceResponse> create(@Valid @RequestBody InvoiceRequest request) {
        return new ResponseEntity<>(service.createInvoice(request), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<InvoiceResponse>> getAll() {
        return ResponseEntity.ok(service.getAllInvoices());
    }

    // This is our special report endpoint!
    @GetMapping("/ageing-report")
    public ResponseEntity<Map<String, Object>> getAgeingReport() {
        return ResponseEntity.ok(service.getAgeingReport());
    }
}