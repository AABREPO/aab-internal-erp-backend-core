package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
    List<EmployeeDetails> findByRoleOfEmployee(String roleOfEmployee);
}
