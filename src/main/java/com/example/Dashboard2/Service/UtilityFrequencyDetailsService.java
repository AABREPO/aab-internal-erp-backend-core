package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.UtilityFrequencyDetails;
import com.example.Dashboard2.Repository.UtilityFrequencyDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UtilityFrequencyDetailsService {

    @Autowired
    private UtilityFrequencyDetailsRepository utilityFrequencyDetailsRepository;

    public UtilityFrequencyDetails saveAllUtilityFrequencyDetails(UtilityFrequencyDetails utilityFrequencyDetails){
        return utilityFrequencyDetailsRepository.save(utilityFrequencyDetails);
    }
    public List<UtilityFrequencyDetails> getAllUtilityFrequencyDetails(){
        return utilityFrequencyDetailsRepository.findAll();
    }
}
