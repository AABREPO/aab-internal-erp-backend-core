package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.BankAccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BankAccountTypeRepository extends JpaRepository<BankAccountType, Long> {
}
