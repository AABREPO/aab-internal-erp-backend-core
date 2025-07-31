package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PiNetStockDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiNetStockDetailsRepository extends JpaRepository<PiNetStockDetails, Long> {
}
