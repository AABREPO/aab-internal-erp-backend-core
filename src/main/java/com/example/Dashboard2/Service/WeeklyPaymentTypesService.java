package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentTypes;
import com.example.Dashboard2.Repository.WeeklyPaymentTypesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WeeklyPaymentTypesService {

    @Autowired
    private WeeklyPaymentTypesRepository weeklyPaymentTypesRepository;

    public WeeklyPaymentTypes saveWeeklyPaymentTypes(WeeklyPaymentTypes weeklyPaymentTypes){
        return weeklyPaymentTypesRepository.save(weeklyPaymentTypes);
    }

    public List<WeeklyPaymentTypes> getAllWeeklyPaymentTypes(){
        return weeklyPaymentTypesRepository.findAll();
    }
    public WeeklyPaymentTypes updateWeeklyPaymentTypes(Long id, WeeklyPaymentTypes weeklyPaymentTypes){
        Optional<WeeklyPaymentTypes> existingTypes= weeklyPaymentTypesRepository.findById(id);
        if (existingTypes.isPresent()){
            WeeklyPaymentTypes updatedTypes = existingTypes.get();
            updatedTypes.setType(weeklyPaymentTypes.getType());
            return weeklyPaymentTypesRepository.save(updatedTypes);
        } else {
            throw new RuntimeException("Type not found" + id);
        }
    }
    public void deleteTypes(Long id){
        if (weeklyPaymentTypesRepository.existsById(id)){
            weeklyPaymentTypesRepository.deleteById(id);
        } else {
            throw new RuntimeException("Type not found" + id);
        }
    }
    public void deleteAllTypes(){
        weeklyPaymentTypesRepository.deleteAll();
    }

}
