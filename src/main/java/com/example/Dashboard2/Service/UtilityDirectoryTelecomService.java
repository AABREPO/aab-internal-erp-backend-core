package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityDirectoryTelecom;
import com.example.Dashboard2.Repository.UtilityDirectoryTelecomRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityDirectoryTelecomService {

    private final UtilityDirectoryTelecomRepository repository;

    public UtilityDirectoryTelecomService(UtilityDirectoryTelecomRepository repository) {
        this.repository = repository;
    }

    public UtilityDirectoryTelecom save(UtilityDirectoryTelecom utilityDirectoryTelecom){
        return repository.save(utilityDirectoryTelecom);
    }

    public UtilityDirectoryTelecom update(Long id, UtilityDirectoryTelecom utilityDirectoryTelecom){
        utilityDirectoryTelecom.setId(id);
        return repository.save(utilityDirectoryTelecom);
    }

    public List<UtilityDirectoryTelecom> getAll(){
        return repository.findAll();
    }

    public UtilityDirectoryTelecom getById(Long id){
        return repository.findById(id).orElse(null);
    }

    public void delete(Long id){
        repository.deleteById(id);
    }
}
