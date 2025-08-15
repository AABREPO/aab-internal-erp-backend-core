package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Repository.WeeklyPaymentsReceivedRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;

@Service
public class WeeklyPaymentsReceivedService {
    private final WeeklyPaymentsReceivedRepository repo;

    public WeeklyPaymentsReceivedService(WeeklyPaymentsReceivedRepository repo) {
        this.repo = repo;
    }

    public Integer getCurrentCalendarWeek() {
        LocalDate now = LocalDate.now();
        WeekFields wf = WeekFields.of(Locale.getDefault());
        return now.get(wf.weekOfWeekBasedYear());
    }

    public List<WeeklyPaymentsReceived> getPaymentsByWeek(Integer weekNumber) {
        return repo.findByWeeklyNumber(weekNumber);
    }


    public WeeklyPaymentsReceived savePayment(WeeklyPaymentsReceived payment) {
        Integer currentWeek = repo.findMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = getCurrentCalendarWeek();
        }
        // IMPORTANT: Only set weeklyNumber if not already set in payment object
        if (payment.getWeeklyNumber() == null) {
            payment.setWeeklyNumber(currentWeek);
        }
        if (payment.getPeriodStartDate() == null) {
            payment.setPeriodStartDate(LocalDate.now());
        }
        return repo.save(payment);
    }

    @Transactional
    public void closeCurrentPeriod(Integer weekNumber) {
        repo.closePeriod(weekNumber, LocalDate.now());
    }

    public Integer getMaxWeeklyNumber() {
        return repo.findMaxWeeklyNumber();
    }
}
