package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.LoanPortal;
import com.example.Dashboard2.Entity.LoanPortalAudit;
import com.example.Dashboard2.Service.LoanPortalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class LoanPortalController {
    @Autowired
    private LoanPortalService service;
    @PostMapping("/save")
    public ResponseEntity<LoanPortal> save(@RequestBody LoanPortal loanPortal) {
        LoanPortal savedLoan = service.save(loanPortal);
        return ResponseEntity.ok(savedLoan);
    }
    @GetMapping("/all")
    public ResponseEntity<List<LoanPortal>> getAll(@RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getAll(branchId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<LoanPortal> getById(@PathVariable Long id,
                                              @RequestParam(required = false) Long branchId) {
        return service.getById(id, branchId)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<List<LoanPortal>> updateLoanEntry(
            @PathVariable Long id,
            @RequestBody LoanPortal loanPortalDetails,
            @RequestParam String editedBy
    ) {
        try {
            List<LoanPortal> updatedLoans = service.updateLoan(id, loanPortalDetails, editedBy);
            return ResponseEntity.ok(updatedLoans);
        } catch (EntityNotFoundException ex) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().build();
        }
    }
    @GetMapping("/audit/history/{loanPortalId}")
    public List<LoanPortalAudit> getAuditHistory(@PathVariable Long loanPortalId) {
        return service.getAuditHistory(loanPortalId);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @PutMapping("/allow/{id}")
    public LoanPortal updateAllowToEdit(@PathVariable Long id, @RequestParam boolean allow){
        return service.updateAllowToEdit(id, allow);
    }
}