package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ClaimPaymentsCashRegister;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClaimPaymentsCashRegisterRepository extends JpaRepository<ClaimPaymentsCashRegister,Long> {
    List<ClaimPaymentsCashRegister> findByClaimPaymentsId(int claimPaymentsId);
}
