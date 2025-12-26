package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityAMCPurpose;
import com.example.Dashboard2.Repository.UtilityAMCPurposeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityAMCPurposeService {

    private final UtilityAMCPurposeRepository repo;

    public UtilityAMCPurposeService(UtilityAMCPurposeRepository repo) {
        this.repo = repo;
    }

    public UtilityAMCPurpose save(UtilityAMCPurpose data){ return repo.save(data); }
    public UtilityAMCPurpose update(Long id, UtilityAMCPurpose data){ data.setId(id); return repo.save(data); }
    public List<UtilityAMCPurpose> getAll(){ return repo.findAll(); }
    public UtilityAMCPurpose getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}
