package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ClaimPayments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimPaymentsRepository extends JpaRepository<ClaimPayments, Long> {
    List<ClaimPayments> findByExpensesClaimId(int expensesClaimId);
}
