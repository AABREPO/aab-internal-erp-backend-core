package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilitySubscriptionPurpose;
import com.example.Dashboard2.Repository.UtilitySubscriptionPurposeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilitySubscriptionPurposeService {

    private final UtilitySubscriptionPurposeRepository repo;

    public UtilitySubscriptionPurposeService(UtilitySubscriptionPurposeRepository repo) {
        this.repo = repo;
    }

    public UtilitySubscriptionPurpose save(UtilitySubscriptionPurpose data){ return repo.save(data); }
    public UtilitySubscriptionPurpose update(Long id, UtilitySubscriptionPurpose data){ data.setId(id); return repo.save(data); }
    public List<UtilitySubscriptionPurpose> getAll(){ return repo.findAll(); }
    public UtilitySubscriptionPurpose getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}
