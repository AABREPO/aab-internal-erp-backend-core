package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentRefundReceivedAudit;
import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntry;
import com.example.Dashboard2.Entity.WeeklyPaymentsDailyEntryAudit;
import com.example.Dashboard2.Repository.WeeklyPaymentRefundReceivedAuditRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentRefundReceivedRepository;
import com.example.Dashboard2.Repository.WeeklyPaymentsDailyEntryAuditRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/daily_entry_audit")
public class WeeklyPaymentsDailyEntryAuditController {

    @Autowired
    private WeeklyPaymentRefundReceivedAuditRepository refundReceivedRepository;
    @Autowired
    private WeeklyPaymentsDailyEntryAuditRepository dailyEntryAuditRepository;

    @GetMapping("/daily_expense/{dailyId}")
    public List<WeeklyPaymentsDailyEntryAudit> getDailyEntryAudit(@PathVariable Long dailyId){
        return dailyEntryAuditRepository.findByWeeklyPaymentsDailyEntryId(dailyId);
    }
    @GetMapping("/refund/{refundId}")
    public List<WeeklyPaymentRefundReceivedAudit> getRefundReceivedAudit(@PathVariable Long refundId){
        return refundReceivedRepository.findByWeeklyPaymentRefundReceivedId(refundId);
    }
}
