package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceProvider;
import com.example.Dashboard2.Repository.UtilitySubscriptionServiceProviderRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilitySubscriptionServiceProviderService {

    private final UtilitySubscriptionServiceProviderRepository repo;

    public UtilitySubscriptionServiceProviderService(UtilitySubscriptionServiceProviderRepository repo) {
        this.repo = repo;
    }

    public UtilitySubscriptionServiceProvider save(UtilitySubscriptionServiceProvider data){ return repo.save(data); }
    public UtilitySubscriptionServiceProvider update(Long id, UtilitySubscriptionServiceProvider data){ data.setId(id); return repo.save(data); }
    public List<UtilitySubscriptionServiceProvider> getAll(){ return repo.findAll(); }
    public UtilitySubscriptionServiceProvider getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}
