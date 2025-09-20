package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.LoanPurpose;
import com.example.Dashboard2.Service.LoanPurposeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loan-purposes")
public class LoanPurposeController {

    private final LoanPurposeService service;

    public LoanPurposeController(LoanPurposeService service) {
        this.service = service;
    }
    @GetMapping("/getAll")
    public List<LoanPurpose> getAllPurposes() {
        return service.getAllPurposes();
    }
    @PostMapping("/save")
    public LoanPurpose savePurpose(@RequestBody LoanPurpose purpose) {
        return service.savePurpose(purpose);
    }
    @PutMapping("/edit/{id}")
    public LoanPurpose updateLoanPurpose(@PathVariable Long id, @RequestBody LoanPurpose loanPurpose) {
        return service.updateLoanPurpose(id, loanPurpose);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteLoanPurpose(@PathVariable Long id) {
        service.deleteLoanPurpose(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllPurpose() {
        service.deleteAllLoanPurpose();
        return "All Loan Purposes Deleted Successfully";
    }
}