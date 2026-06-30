package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.SupportStaffNameList;
import com.example.Dashboard2.Repository.SupportStaffNameListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupportStaffNameListService {

    @Autowired
    private SupportStaffNameListRepository supportStaffNameListRepository;

    public SupportStaffNameList saveSupportStaffNameList(SupportStaffNameList supportStaffNameList){
        return supportStaffNameListRepository.save(supportStaffNameList);
    }
    public List<SupportStaffNameList> getAllSupportStaffNameList(){
        return supportStaffNameListRepository.findAll();
    }
    public void deleteSupportStaffNameListWithId(Long id){
        supportStaffNameListRepository.deleteById(id);
    }
    public void deleteAllSupportStaffNameList(){
        supportStaffNameListRepository.deleteAll();
    }
    public SupportStaffNameList updateSupportStaffName(Long id, SupportStaffNameList supportStaffNameList){
        return supportStaffNameListRepository.findById(id)
                .map(existingSupportStaff ->{
                    existingSupportStaff.setSupportStaffName(supportStaffNameList.getSupportStaffName());
                    existingSupportStaff.setMobileNumber(supportStaffNameList.getMobileNumber());
                    return supportStaffNameListRepository.save(existingSupportStaff);
                })
                .orElseThrow(() -> new RuntimeException("Support Staff not found with id " + id));
    }
}
