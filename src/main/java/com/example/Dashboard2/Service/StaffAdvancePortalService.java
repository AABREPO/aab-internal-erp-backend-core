package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.StaffAdvancePortal;
import com.example.Dashboard2.Repository.StaffAdvancePortalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class StaffAdvancePortalService {

    @Autowired
    private StaffAdvancePortalRepository repository;

    public Long getNextEntryNo() {
        Long maxEntryNo = repository.findMaxEntryNo();
        return (maxEntryNo == null) ? 1L : maxEntryNo + 1;
    }

    @Transactional
    public StaffAdvancePortal save(StaffAdvancePortal staffAdvance) {
        if (staffAdvance.getEntryNo() == null) {
            staffAdvance.setEntryNo(getNextEntryNo());
        }

        if (staffAdvance.getWeekNo() == 0) {
            LocalDate dateForWeek;
            try {
                dateForWeek = LocalDate.parse(staffAdvance.getDate());
            } catch (Exception e) {
                dateForWeek = LocalDate.now();
            }
            WeekFields weekFields = WeekFields.of(Locale.getDefault());
            int weekNo = dateForWeek.get(weekFields.weekOfWeekBasedYear());
            staffAdvance.setWeekNo(weekNo);
        }

        staffAdvance.setTimestamp(LocalDateTime.now());

        if ("Transfer".equalsIgnoreCase(staffAdvance.getType())) {
            StaffAdvancePortal fromEntry = new StaffAdvancePortal();
            fromEntry.setEntryNo(staffAdvance.getEntryNo());
            fromEntry.setDate(staffAdvance.getDate());
            fromEntry.setWeekNo(staffAdvance.getWeekNo());
            fromEntry.setEmployeeId(staffAdvance.getEmployeeId());
            fromEntry.setType(staffAdvance.getType());
            fromEntry.setTimestamp(LocalDateTime.now());
            fromEntry.setFromPurposeId(staffAdvance.getFromPurposeId());
            fromEntry.setToPurposeId(staffAdvance.getToPurposeId());
            fromEntry.setAmount(-Math.abs(staffAdvance.getAmount()));
            fromEntry.setStaffPaymentMode(staffAdvance.getStaffPaymentMode());
            fromEntry.setDescription(staffAdvance.getDescription());
            fromEntry.setStaffRefundAmount(0);

            StaffAdvancePortal toEntry = new StaffAdvancePortal();
            toEntry.setEntryNo(staffAdvance.getEntryNo());
            toEntry.setDate(staffAdvance.getDate());
            toEntry.setWeekNo(staffAdvance.getWeekNo());
            toEntry.setEmployeeId(staffAdvance.getEmployeeId());
            toEntry.setType(staffAdvance.getType());
            toEntry.setTimestamp(LocalDateTime.now());
            toEntry.setFromPurposeId(staffAdvance.getToPurposeId());
            toEntry.setToPurposeId(staffAdvance.getFromPurposeId());
            toEntry.setAmount(Math.abs(staffAdvance.getAmount()));
            toEntry.setStaffPaymentMode(staffAdvance.getStaffPaymentMode());
            toEntry.setDescription(staffAdvance.getDescription());
            toEntry.setStaffRefundAmount(0);

            repository.save(fromEntry);
            repository.save(toEntry);

            return fromEntry;

        } else {
            staffAdvance.setToPurposeId(null);
            return repository.save(staffAdvance);
        }
    }

    public List<StaffAdvancePortal> getAll() {
        return repository.findAll();
    }

    public List<StaffAdvancePortal> getByEmployeeId(int employeeId) {
        return repository.findByEmployeeId(employeeId);
    }

    public List<StaffAdvancePortal> getByType(String type) {
        return repository.findByType(type);
    }

    public List<StaffAdvancePortal> getByEmployeeIdAndType(int employeeId, String type) {
        return repository.findByEmployeeIdAndType(employeeId, type);
    }

    public List<StaffAdvancePortal> getByWeekNo(int weekNo) {
        return repository.findByWeekNo(weekNo);
    }

    public List<StaffAdvancePortal> getByEmployeeIdAndWeekNo(int employeeId, int weekNo) {
        return repository.findByEmployeeIdAndWeekNo(employeeId, weekNo);
    }

    public Optional<StaffAdvancePortal> getById(Long id) {
        return repository.findById(id);
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}