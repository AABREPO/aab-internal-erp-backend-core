package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AdvancePortal;
import com.example.Dashboard2.Repository.AdvancePortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Optional;

@Service
public class AdvancePortalService {

    @Autowired
    private AdvancePortalRepository advancePortalRepository;

    public List<AdvancePortal> getAllAdvancePortals() {
        return advancePortalRepository.findAll();
    }

    public Optional<AdvancePortal> getAdvancePortalById(Long id) {
        return advancePortalRepository.findById(id);
    }

    public AdvancePortal createAdvancePortal(AdvancePortal advancePortal) {
        // Set timestamp if not provided
        if (advancePortal.getTimestamp() == null) {
            advancePortal.setTimestamp(LocalDateTime.now());
        }
        // Parse date and calculate week number
        try {
            LocalDate parsedDate = LocalDate.parse(
                    advancePortal.getDate(),
                    DateTimeFormatter.ofPattern("yyyy-MM-dd")
            );
            int weekNo = parsedDate.get(WeekFields.ISO.weekOfWeekBasedYear());
            advancePortal.setWeekNo(weekNo);
        } catch (Exception e) {
            throw new IllegalArgumentException("Invalid date format. Expected yyyy-MM-dd");
        }
        return advancePortalRepository.save(advancePortal);
    }

    public AdvancePortal updateAdvancePortal(Long id, AdvancePortal updatedPortal) {
        return advancePortalRepository.findById(id).map(existing -> {
            // Do NOT update timestamp, entryNo, or weekNo
            existing.setType(updatedPortal.getType());
            existing.setDate(updatedPortal.getDate());
            existing.setVendorId(updatedPortal.getVendorId());
            existing.setContractorId(updatedPortal.getContractorId());
            existing.setProjectId(updatedPortal.getProjectId());
            existing.setTransferSiteId(updatedPortal.getTransferSiteId());
            existing.setPaymentMode(updatedPortal.getPaymentMode());
            existing.setAmount(updatedPortal.getAmount());
            existing.setRefundAmount(updatedPortal.getRefundAmount());
            existing.setBillAmount(updatedPortal.getBillAmount());
            return advancePortalRepository.save(existing);
        }).orElse(null);
    }

    public boolean deleteAdvancePortal(Long id) {
        if (advancePortalRepository.existsById(id)) {
            advancePortalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
