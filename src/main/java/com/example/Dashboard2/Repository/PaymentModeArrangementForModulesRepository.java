package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.PaymentModeArrangementForModules;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentModeArrangementForModulesRepository extends JpaRepository<PaymentModeArrangementForModules, Long> {

    Optional<PaymentModeArrangementForModules> findByModuleNameIgnoreCase(String moduleName);

    List<PaymentModeArrangementForModules> findAllByOrderByModuleNameAsc();

    boolean existsByModuleNameIgnoreCase(String moduleName);
}
