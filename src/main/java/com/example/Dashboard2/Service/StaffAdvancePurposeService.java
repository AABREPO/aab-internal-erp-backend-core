package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.StaffAdvancePurpose;
import com.example.Dashboard2.Repository.StaffAdvancePurposeRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffAdvancePurposeService {

    private final StaffAdvancePurposeRepository repository;

    public StaffAdvancePurposeService(StaffAdvancePurposeRepository repository) {
        this.repository = repository;
    }

    public List<StaffAdvancePurpose> getAllPurposes() {
        return repository.findAll();
    }

    public StaffAdvancePurpose savePurpose(StaffAdvancePurpose purpose) {
        return repository.save(purpose);
    }

    public StaffAdvancePurpose updateStaffAdvancePurpose(Long id, StaffAdvancePurpose staffAdvancePurpose){
        Optional<StaffAdvancePurpose> existingStaffAdvancePurpose = repository.findById(id);
        if (existingStaffAdvancePurpose.isPresent()){
            StaffAdvancePurpose updateStaffAdvancePurpose = existingStaffAdvancePurpose.get();
            updateStaffAdvancePurpose.setPurpose(staffAdvancePurpose.getPurpose());
            return repository.save(updateStaffAdvancePurpose);
        } else {
            throw new RuntimeException("Purpose not found" + id);
        }
    }
    public void deleteStaffAdvancePurpose(Long id){
        if (repository.existsById(id)){
            repository.deleteById(id);
        } else {
            throw new RuntimeException("Purpose not found" + id);
        }
    }
    public void deleteAllStaffAdvancePurpose(){
        repository.deleteAll();
    }
}