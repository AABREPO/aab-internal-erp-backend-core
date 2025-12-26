package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityTelecomPurpose;
import com.example.Dashboard2.Repository.UtilityTelecomPurposeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityTelecomPurposeService {

    private final UtilityTelecomPurposeRepository repo;

    public UtilityTelecomPurposeService(UtilityTelecomPurposeRepository repo) { this.repo = repo; }

    public UtilityTelecomPurpose save(UtilityTelecomPurpose data){ return repo.save(data); }
    public UtilityTelecomPurpose update(Long id, UtilityTelecomPurpose data){
        data.setId(id);
        return repo.save(data);
    }
    public List<UtilityTelecomPurpose> getAll(){ return repo.findAll(); }
    public UtilityTelecomPurpose getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}
