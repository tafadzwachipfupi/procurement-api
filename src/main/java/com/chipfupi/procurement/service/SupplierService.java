package com.chipfupi.procurement.service;

import com.chipfupi.procurement.dto.SupplierRequest;
import com.chipfupi.procurement.dto.SupplierResponse;
import com.chipfupi.procurement.entity.Supplier;
import com.chipfupi.procurement.repository.SupplierRepository;
import org.springframework.stereotype.Service;
import com.chipfupi.procurement.exception.BusinessRuleException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierService {

    private final SupplierRepository repository;

    public SupplierService(SupplierRepository repository) {
        this.repository = repository;
    }

    public SupplierResponse createSupplier(SupplierRequest request) {
        // Business Rule: No duplicate names
        if (repository.existsByNameIgnoreCase(request.name())) {
           throw new BusinessRuleException("A supplier with this name already exists");
        }

        Supplier supplier = new Supplier();
        supplier.setName(request.name());
        supplier.setTaxNumber(request.taxNumber());
        supplier.setEmail(request.email());

        Supplier savedSupplier = repository.save(supplier);
        return SupplierResponse.from(savedSupplier);
    }

    public List<SupplierResponse> getAllSuppliers() {
        return repository.findAll()
                .stream()
                .map(SupplierResponse::from)
                .collect(Collectors.toList());
    }
}