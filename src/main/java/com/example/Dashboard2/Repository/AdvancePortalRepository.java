package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AdvancePortal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvancePortalRepository extends JpaRepository<AdvancePortal, Long> {
}
