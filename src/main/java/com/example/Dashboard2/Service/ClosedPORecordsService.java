package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ClosedPORecords;
import com.example.Dashboard2.Repository.ClosedPORecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClosedPORecordsService {

    @Autowired
    private ClosedPORecordsRepository closedPORecordsRepository;

    public ClosedPORecords saveClosedPORecords(ClosedPORecords closedPORecords){
        return closedPORecordsRepository.save(closedPORecords);
    }

    public List<ClosedPORecords> getAllClosedPORecords(){
        return closedPORecordsRepository.findAll();
    }
}
