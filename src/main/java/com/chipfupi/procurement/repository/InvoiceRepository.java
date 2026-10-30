package com.chipfupi.procurement.repository;

import com.chipfupi.procurement.entity.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
    
    // This is the Native SQL query for the 30/60/90 day report!
    @Query(value = """
            SELECT 
              COALESCE(SUM(CASE WHEN DATEDIFF(CURDATE(), due_date) BETWEEN 0 AND 30 THEN amount END), 0) AS bucket0to30,
              COALESCE(SUM(CASE WHEN DATEDIFF(CURDATE(), due_date) BETWEEN 31 AND 60 THEN amount END), 0) AS bucket31to60,
              COALESCE(SUM(CASE WHEN DATEDIFF(CURDATE(), due_date) BETWEEN 61 AND 90 THEN amount END), 0) AS bucket61to90,
              COALESCE(SUM(CASE WHEN DATEDIFF(CURDATE(), due_date) > 90 THEN amount END), 0) AS bucket90plus
            FROM invoice
            WHERE status <> 'PAID'
            """, nativeQuery = true)
    Map<String, Object> getAgeingReport();
}