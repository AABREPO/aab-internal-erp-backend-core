package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PiIncomingDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PiIncomingDetailsRepository extends JpaRepository<PiIncomingDetails, Long> {
}
