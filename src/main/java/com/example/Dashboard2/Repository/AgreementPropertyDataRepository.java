package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AgreementPropertyDataWithFileNames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AgreementPropertyDataRepository extends JpaRepository<AgreementPropertyDataWithFileNames, Long> {
}
