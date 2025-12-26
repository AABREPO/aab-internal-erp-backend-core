package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityDirectorySubscription;
import com.example.Dashboard2.Repository.UtilityDirectorySubscriptionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityDirectorySubscriptionService {

    private final UtilityDirectorySubscriptionRepository repository;

    public UtilityDirectorySubscriptionService(UtilityDirectorySubscriptionRepository repository){
        this.repository = repository;
    }

    public UtilityDirectorySubscription save(UtilityDirectorySubscription utilityDirectorySubscription){
        return repository.save(utilityDirectorySubscription);
    }

    public UtilityDirectorySubscription update(Long id, UtilityDirectorySubscription utilityDirectorySubscription){
        utilityDirectorySubscription.setId(id);
        return repository.save(utilityDirectorySubscription);
    }
    public List<UtilityDirectorySubscription> getAll(){
        return repository.findAll();
    }
    public UtilityDirectorySubscription getById(Long id){
        return repository.findById(id).orElse(null);
    }
    public void delete(Long id){
        repository.deleteById(id);
    }

}