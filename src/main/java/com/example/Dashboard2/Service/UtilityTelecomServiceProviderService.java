package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityTelecomServiceProvider;
import com.example.Dashboard2.Repository.UtilityTelecomServiceProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityTelecomServiceProviderService {

    private final UtilityTelecomServiceProviderRepository repo;

    public UtilityTelecomServiceProviderService(UtilityTelecomServiceProviderRepository repo) { this.repo = repo; }

    public UtilityTelecomServiceProvider save(UtilityTelecomServiceProvider data){ return repo.save(data); }
    public UtilityTelecomServiceProvider update(Long id, UtilityTelecomServiceProvider data){ data.setId(id); return repo.save(data); }
    public List<UtilityTelecomServiceProvider> getAll(){ return repo.findAll(); }
    public UtilityTelecomServiceProvider getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}

