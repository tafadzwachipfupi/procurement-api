package com.chipfupi.procurement.repository;

import com.chipfupi.procurement.entity.Supplier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SupplierRepository extends JpaRepository<Supplier, Long> {
    
    // Spring Boot writes the SQL for this automatically!
    boolean existsByNameIgnoreCase(String name);
}