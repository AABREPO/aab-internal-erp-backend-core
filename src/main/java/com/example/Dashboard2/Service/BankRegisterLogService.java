package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.BankRegisterLog;
import com.example.Dashboard2.Repository.BankRegisterLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
public class BankRegisterLogService {

    @Autowired
    private BankRegisterLogRepository repository;

    // SAVE DATA
    public String saveData(BankRegisterLog log) {

        // AUTO TIMESTAMP
        log.setTimestamp(LocalDateTime.now(ZoneId.of("Asia/Kolkata")));
        repository.save(log);
        return "Data saved successfully";
    }

    // GET ALL DATA
    public List<BankRegisterLog> getAllData() {

        return repository.findAll();
    }

    // GET DATA BY ID
    public BankRegisterLog getById(Long id) {

        return repository.findById(id)
                .orElse(null);
    }
}