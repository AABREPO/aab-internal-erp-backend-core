package com.example.Dashboard2.Repository;

import com.example.Dashboard2.DTO.EmployeeBasicDetailsDto;
import com.example.Dashboard2.Entity.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, Long> {
    List<EmployeeDetails> findByRoleOfEmployee(String roleOfEmployee);

    @Query("SELECT new com.example.Dashboard2.DTO.EmployeeBasicDetailsDto(e.id, e.employeeName, e.employeeMobileNumber, e.userName) " +
            "FROM EmployeeDetails e WHERE e.roleOfEmployee = :roleOfEmployee")
    List<EmployeeBasicDetailsDto> findEmployeeBasicDetailsByRoleOfEmployee(@Param("roleOfEmployee") String roleOfEmployee);

    @Query("SELECT new com.example.Dashboard2.DTO.EmployeeBasicDetailsDto(e.id, e.employeeName, e.employeeMobileNumber, e.userName) " +
            "FROM EmployeeDetails e")
    List<EmployeeBasicDetailsDto> findAllEmployeeBasicDetails();

    @Query("SELECT new com.example.Dashboard2.DTO.EmployeeBasicDetailsDto(e.id, e.employeeName, e.employeeMobileNumber, e.userName) " +
            "FROM EmployeeDetails e WHERE e.isSiteEngineer = true")
    List<EmployeeBasicDetailsDto> findEmployeeBasicDetailsByIsSiteEngineerTrue();

}
