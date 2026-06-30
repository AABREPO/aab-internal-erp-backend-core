package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.EditRequestRecords;
import com.example.Dashboard2.Repository.EditRequestRecordsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class EditRequestRecordsService {

    @Autowired
    private EditRequestRecordsRepository repository;

    // Save new request
    public EditRequestRecords createRequest(EditRequestRecords request) {
        if (request.getTimestamp() == null) {
            request.setTimestamp(LocalDateTime.now());
        }
        return repository.save(request);
    }

    // Get All
    public List<EditRequestRecords> getAll() {
        return repository.findAll();
    }

    // Get by ID
    public Optional<EditRequestRecords> getById(Long id) {
        return repository.findById(id);
    }

    // Update / Edit (only update fields provided)
    public EditRequestRecords updateRequest(Long id, EditRequestRecords updated) {
        return repository.findById(id).map(existing -> {
            existing.setModuleName(updated.getModuleName() != null ? updated.getModuleName() : existing.getModuleName());
            existing.setModuleNameId(updated.getModuleNameId() != null ? updated.getModuleNameId() : existing.getModuleNameId());
            existing.setModuleNameEno(updated.getModuleNameEno() != null ? updated.getModuleNameEno() : existing.getModuleNameEno());
            existing.setRequestSendBy(updated.getRequestSendBy() != null ? updated.getRequestSendBy() : existing.getRequestSendBy());
            existing.setRequestApproval(updated.isRequestApproval());
            existing.setRequestCompleted(updated.isRequestCompleted());
            return repository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Record not found"));
    }
}
