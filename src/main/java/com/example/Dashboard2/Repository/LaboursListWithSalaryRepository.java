package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.LaboursListWithSalary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaboursListWithSalaryRepository extends JpaRepository<LaboursListWithSalary, Long> {
}
