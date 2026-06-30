package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.LoanPurpose;
import com.example.Dashboard2.Repository.LoanPurposeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanPurposeService {

    private final LoanPurposeRepository repository;

    public LoanPurposeService(LoanPurposeRepository repository) {
        this.repository = repository;
    }
    public List<LoanPurpose> getAllPurposes() {
        return repository.findAll();
    }
    public LoanPurpose savePurpose(LoanPurpose purpose) {
        return repository.save(purpose);
    }
    public LoanPurpose updateLoanPurpose(Long id, LoanPurpose loanPurpose) {
        Optional<LoanPurpose> existingLoanPurpose = repository.findById(id);
        if (existingLoanPurpose.isPresent()) {
            LoanPurpose updatedLoanPurpose = existingLoanPurpose.get();
            updatedLoanPurpose.setPurpose(loanPurpose.getPurpose());
            return repository.save(updatedLoanPurpose);
        } else {
            throw new RuntimeException("Loan Purpose not found with id " + id);
        }
    }
    public void deleteLoanPurpose(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Loan Purpose not found with id " + id);
        }
    }
    public void deleteAllLoanPurpose() {
        repository.deleteAll();
    }
}