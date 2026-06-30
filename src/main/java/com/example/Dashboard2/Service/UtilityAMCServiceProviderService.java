package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityAMCServiceProvider;
import com.example.Dashboard2.Repository.UtilityAMCServiceProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityAMCServiceProviderService {

    private final UtilityAMCServiceProviderRepository repo;

    public UtilityAMCServiceProviderService(UtilityAMCServiceProviderRepository repo) {
        this.repo = repo;
    }

    public UtilityAMCServiceProvider save(UtilityAMCServiceProvider data){ return repo.save(data); }
    public UtilityAMCServiceProvider update(Long id, UtilityAMCServiceProvider data){ data.setId(id); return repo.save(data); }
    public List<UtilityAMCServiceProvider> getAll(){ return repo.findAll(); }
    public UtilityAMCServiceProvider getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}
