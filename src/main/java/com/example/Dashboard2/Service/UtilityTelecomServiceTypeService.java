package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityTelecomServiceType;
import com.example.Dashboard2.Repository.UtilityTelecomServiceTypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityTelecomServiceTypeService {

    private final UtilityTelecomServiceTypeRepository repo;

    public UtilityTelecomServiceTypeService(UtilityTelecomServiceTypeRepository repo) {
        this.repo = repo;
    }

    public UtilityTelecomServiceType save(UtilityTelecomServiceType data){ return repo.save(data); }

    public UtilityTelecomServiceType update(Long id, UtilityTelecomServiceType data){
        data.setId(id);
        return repo.save(data);
    }

    public List<UtilityTelecomServiceType> getAll(){ return repo.findAll(); }

    public UtilityTelecomServiceType getById(Long id){ return repo.findById(id).orElse(null); }

    public void delete(Long id){ repo.deleteById(id); }
}
