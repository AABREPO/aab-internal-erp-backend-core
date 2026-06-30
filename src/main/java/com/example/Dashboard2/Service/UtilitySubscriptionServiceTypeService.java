package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilitySubscriptionServiceType;
import com.example.Dashboard2.Repository.UtilitySubscriptionServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilitySubscriptionServiceTypeService {

    private final UtilitySubscriptionServiceTypeRepository repo;

    public UtilitySubscriptionServiceTypeService(UtilitySubscriptionServiceTypeRepository repo) {
        this.repo = repo;
    }

    public UtilitySubscriptionServiceType save(UtilitySubscriptionServiceType data){ return repo.save(data); }
    public UtilitySubscriptionServiceType update(Long id, UtilitySubscriptionServiceType data){ data.setId(id); return repo.save(data); }
    public List<UtilitySubscriptionServiceType> getAll(){ return repo.findAll(); }
    public UtilitySubscriptionServiceType getById(Long id){ return repo.findById(id).orElse(null); }
    public void delete(Long id){ repo.deleteById(id); }
}

