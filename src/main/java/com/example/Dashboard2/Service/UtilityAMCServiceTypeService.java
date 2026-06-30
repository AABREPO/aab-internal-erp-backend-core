package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityAMCServiceType;
import com.example.Dashboard2.Repository.UtilityAMCServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityAMCServiceTypeService {

    private final UtilityAMCServiceTypeRepository repo;

    public UtilityAMCServiceTypeService(UtilityAMCServiceTypeRepository repo) {
        this.repo = repo;
    }

    public UtilityAMCServiceType save(UtilityAMCServiceType data){ return repo.save(data); }
    public UtilityAMCServiceType update(Long id, UtilityAMCServiceType data){ data.setId(id); return repo.save(data); }
    public List<UtilityAMCServiceType> getAll(){ return repo.findAll(); }
    public UtilityAMCServiceType getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}

