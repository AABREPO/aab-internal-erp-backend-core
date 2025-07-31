package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AgreementPropertyDataWithFileNames;
import com.example.Dashboard2.Repository.AgreementPropertyDataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgreementPropertyDataService {

    @Autowired
    private AgreementPropertyDataRepository repository;

    public AgreementPropertyDataWithFileNames saveAgreement(AgreementPropertyDataWithFileNames agreement) {
        return repository.save(agreement);
    }

    public List<AgreementPropertyDataWithFileNames> getAllAgreements() {
        return repository.findAll();
    }

    public Optional<AgreementPropertyDataWithFileNames> getAgreementById(Long id) {
        return repository.findById(id);
    }

    public AgreementPropertyDataWithFileNames updateAgreement(Long id, AgreementPropertyDataWithFileNames updatedAgreement) {
        return repository.findById(id)
                .map(existing -> {
                    updatedAgreement.setId(id); // ensure ID stays the same
                    return repository.save(updatedAgreement);
                })
                .orElseThrow(() -> new RuntimeException("Agreement not found with ID: " + id));
    }
    public AgreementPropertyDataWithFileNames updateConfirmedAgreementUrl(Long id, String confirmedAgreementUrl) {
        return repository.findById(id)
                .map(existing -> {
                    existing.setConfirmedAgreementUrl(confirmedAgreementUrl);
                    return repository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("Agreement not found with ID: " + id));
    }

    public void deleteAgreementById(Long id) {
        repository.deleteById(id);
    }

    public void deleteAllAgreements() {
        repository.deleteAll();
    }
}
