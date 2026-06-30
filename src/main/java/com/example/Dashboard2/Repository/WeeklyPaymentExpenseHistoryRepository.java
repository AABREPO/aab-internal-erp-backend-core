package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentExpenseHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WeeklyPaymentExpenseHistoryRepository extends JpaRepository<WeeklyPaymentExpenseHistory, Long> {
    Optional<WeeklyPaymentExpenseHistory>
    findTopByExpenseIdOrderByRemovedAtDesc(Long expenseId);
}
