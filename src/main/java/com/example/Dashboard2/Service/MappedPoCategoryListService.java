package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.MappedPoCategoryList;
import com.example.Dashboard2.Repository.MappedPoCategoryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappedPoCategoryListService {

    @Autowired
    private MappedPoCategoryListRepository repository;

    public MappedPoCategoryList save(MappedPoCategoryList category) {
        return repository.save(category);
    }

    public List<MappedPoCategoryList> getAll() {
        return repository.findAll();
    }

    public MappedPoCategoryList update(Long id, MappedPoCategoryList updatedCategory) {
        return repository.findById(id).map(existing -> {
            existing.setMappedCategory(updatedCategory.getMappedCategory());
            return repository.save(existing);
        }).orElse(null);
    }
    public void deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            System.out.println("Deleted ID from DB: " + id);
        } else {
            System.out.println("ID not found: " + id);
        }
    }


}

