package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.POGroupNameList;
import com.example.Dashboard2.Repository.POGroupNameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class POGroupNameService {

    @Autowired
    private POGroupNameListRepository poGroupNameListRepository;

    public POGroupNameList saveGroupNameList(POGroupNameList poGroupNameList){
        return poGroupNameListRepository.save(poGroupNameList);
    }
    public List<POGroupNameList> getAllPOGroupNameList(){
        return poGroupNameListRepository.findAll();
    }
    public POGroupNameList updatePOGroupNameList(Long id, POGroupNameList poGroupNameList){
        Optional<POGroupNameList> existingPOGroupNameList = poGroupNameListRepository.findById(id);
        if (existingPOGroupNameList.isPresent()){
            POGroupNameList updatedPOGroupNameList = existingPOGroupNameList.get();
            updatedPOGroupNameList.setGroupName(poGroupNameList.getGroupName());
            return poGroupNameListRepository.save(updatedPOGroupNameList);
        } else {
            throw new RuntimeException("Group Name not found" + id);
        }
    }
    public void deletePOGroupName(Long id){
        if (poGroupNameListRepository.existsById(id)){
            poGroupNameListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Group Name not found" + id);
        }
    }
    public void deleteAllPOGroupNameList(){
        poGroupNameListRepository.deleteAll();
    }
}
