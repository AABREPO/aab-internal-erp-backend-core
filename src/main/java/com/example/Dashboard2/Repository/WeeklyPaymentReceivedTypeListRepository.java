package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.WeeklyPaymentReceivedTypeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WeeklyPaymentReceivedTypeListRepository extends JpaRepository<WeeklyPaymentReceivedTypeList , Long> {
}
