package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.BankRegisterLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankRegisterLogRepository
        extends JpaRepository<BankRegisterLog, Long> {
}