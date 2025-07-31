package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.POCategoryList;
import com.example.Dashboard2.Repository.POCategoryListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POCategoryListService {

    @Autowired
    private POCategoryListRepository poCategoryListRepository;

    public POCategoryList savePoCategoryList(POCategoryList poCategoryList){
        return poCategoryListRepository.save(poCategoryList);
    }
    public List<POCategoryList> getAllPOCategoryList(){
        return poCategoryListRepository.findAll();
    }
    public POCategoryList updatePoCategoryList(Long id, POCategoryList poCategoryList){
        Optional<POCategoryList> existingPoCategoryList = poCategoryListRepository.findById(id);
        if (existingPoCategoryList.isPresent()){
            POCategoryList updatedPocategoryList = existingPoCategoryList.get();
            updatedPocategoryList.setCategory(poCategoryList.getCategory());
            return poCategoryListRepository.save(updatedPocategoryList);
        } else {
            throw new RuntimeException("Category not found" + id);
        }
    }
    public void deletePoCategory(Long id){
        if (poCategoryListRepository.existsById(id)){
            poCategoryListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Category id not found"+ id);
        }
    }
    public void deleteAllPoCategoryList(){
        poCategoryListRepository.deleteAll();
    }
}
