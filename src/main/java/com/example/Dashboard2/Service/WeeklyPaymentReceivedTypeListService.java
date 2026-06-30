package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentReceivedTypeList;
import com.example.Dashboard2.Repository.WeeklyPaymentReceivedTypeListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyPaymentReceivedTypeListService {

    @Autowired
    private WeeklyPaymentReceivedTypeListRepository receivedTypeListRepository;

    public WeeklyPaymentReceivedTypeList saveWeeklyPaymentReceived(WeeklyPaymentReceivedTypeList weeklyPaymentReceivedTypeList){
        return receivedTypeListRepository.save(weeklyPaymentReceivedTypeList);
    }
    public List<WeeklyPaymentReceivedTypeList> getAllWeeklyPaymentReceivedTypes(){
        return receivedTypeListRepository.findAll();
    }
    public WeeklyPaymentReceivedTypeList updateWeeklyPaymentReceivedType(Long id, WeeklyPaymentReceivedTypeList weeklyPaymentReceivedTypeList){
        Optional<WeeklyPaymentReceivedTypeList> existingTypes = receivedTypeListRepository.findById(id);
        if (existingTypes.isPresent()){
            WeeklyPaymentReceivedTypeList updatedTypes = existingTypes.get();
            updatedTypes.setReceivedType(weeklyPaymentReceivedTypeList.getReceivedType());
            return receivedTypeListRepository.save(updatedTypes);
        } else {
            throw new RuntimeException("Type not found" + id);
        }
    }
    public void deleteReceivedTypes(Long id){
        if (receivedTypeListRepository.existsById(id)){
            receivedTypeListRepository.deleteById(id);
        } else {
            throw new RuntimeException("Type not found" + id);
        }
    }
    public void deleteAllTypes(){
        receivedTypeListRepository.deleteAll();
    }
}
