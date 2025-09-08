package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceived;
import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceivedAudit;
import com.example.Dashboard2.Repository.WeeklyPaymentRefundReceivedAuditRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentRefundReceivedRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class WeeklyPaymentRefundReceivedService {

    @Autowired
    private WeeklyPaymentRefundReceivedRepository refundReceivedRepository;

    @Autowired
    private WeeklyPaymentRefundReceivedAuditRepository auditRepository;

    @Autowired
    private WeeklyPaymentsReceivedService paymentsReceivedService;

    public WeeklyPaymentRefundReceived saveRefundReceived(WeeklyPaymentRefundReceived refundReceived){
        if (refundReceived.getCreatedAt() == null){
            refundReceived.setCreatedAt(LocalDateTime.now());
        }
        WeeklyPaymentRefundReceived saved = refundReceivedRepository.save(refundReceived);

        paymentsReceivedService.recalculateWeeklyRefundPayment(saved.getWeeklyNumber(), saved.getDate());
        return saved;
    }
    public List<WeeklyPaymentRefundReceived> getAllRefundReceived(){
        return refundReceivedRepository.findAll();
    }
    public List<WeeklyPaymentRefundReceived> getRefundReceivedByDate(LocalDate date){
        return refundReceivedRepository.findByDate(date);
    }
    public void deleteRefundReceived(Long id){
        WeeklyPaymentRefundReceived existing = refundReceivedRepository.findById(id)
                        .orElseThrow(() -> new RuntimeException("Refund Received not found"));
        refundReceivedRepository.deleteById(id);

        paymentsReceivedService.recalculateWeeklyRefundPayment(existing.getWeeklyNumber(), existing.getDate());
    }
    public WeeklyPaymentRefundReceived editRefundReceived(Long id, String username, WeeklyPaymentRefundReceived updatedRefundReceived){
        WeeklyPaymentRefundReceived existing = refundReceivedRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Refund Received not found"));
        //save Audit Before Updating
        WeeklyPaymentRefundReceivedAudit audit = new WeeklyPaymentRefundReceivedAudit();
        audit.setWeeklyPaymentRefundReceivedId(existing.getId());
        audit.setEditedBy(username);
        audit.setEditedDate(LocalDateTime.now());
        audit.setWeeklyNumber(existing.getWeeklyNumber() !=null ? existing.getWeeklyNumber().toString() : null);

        audit.setOldDate(existing.getDate() !=null ? existing.getDate().toString() : null);
        audit.setOldAmount(existing.getAmount() !=null ? existing.getAmount().toString() : null);
        audit.setOldLabourId(existing.getLabourId() !=null ? existing.getLabourId().toString() : null);

        audit.setNewDate(existing.getDate() !=null ? existing.getDate().toString() : null);
        audit.setNewAmount(updatedRefundReceived.getAmount() != null? updatedRefundReceived.getAmount().toString() : null);
        audit.setNewLabourId(updatedRefundReceived.getLabourId() !=null ? updatedRefundReceived.getLabourId().toString() : null);

        auditRepository.save(audit);

        //Apply Updates
        existing.setAmount(updatedRefundReceived.getAmount());
        existing.setLabourId(updatedRefundReceived.getLabourId());
        WeeklyPaymentRefundReceived saved = refundReceivedRepository.save(existing);
        paymentsReceivedService.recalculateWeeklyRefundPayment(saved.getWeeklyNumber(), saved.getDate());
        return saved;
    }
}
