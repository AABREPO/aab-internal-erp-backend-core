package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.RequestForQuotationHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RequestForQuotationHistoryRepository extends JpaRepository<RequestForQuotationHistory,Long> {
    List<RequestForQuotationHistory> findByRfqId(Long rfqId);
}
