package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentsReceived;
import com.example.Dashboard2.Service.WeeklyPaymentExpenseService;
import com.example.Dashboard2.Service.WeeklyPaymentsReceivedService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/payments-received")
public class WeeklyPaymentsReceivedController {
    private final WeeklyPaymentsReceivedService paymentsService;
    private final WeeklyPaymentExpenseService expenseService;

    public WeeklyPaymentsReceivedController(WeeklyPaymentsReceivedService paymentsService,
                                      WeeklyPaymentExpenseService expenseService) {
        this.paymentsService = paymentsService;
        this.expenseService = expenseService;
    }

    @GetMapping("/week/{weekNumber}")
    public java.util.List<WeeklyPaymentsReceived> getByWeek(@PathVariable Integer weekNumber) {
        return paymentsService.getPaymentsByWeek(weekNumber);
    }

    @PostMapping("/save")
    public WeeklyPaymentsReceived save(@RequestBody WeeklyPaymentsReceived payment) {
        return paymentsService.savePayment(payment);
    }

    @GetMapping("/current-week")
    public Integer getCurrentWeekNumber() {
        Integer max = paymentsService.getMaxWeeklyNumber();
        if (max == null) {
            return paymentsService.getCurrentCalendarWeek();
        }
        return max;
    }
    /**
     * Account closure endpoint.
     *
     * @param carryForward - Optional boolean query param to indicate carry forward
     * @param carryAmount  - Optional carry forward amount
     * @return new weekly_number after account closure
     */
    @PostMapping("/account-closure")
    public Integer accountClosure(
            @RequestParam(required = false, defaultValue = "false") boolean carryForward,
            @RequestParam(required = false, defaultValue = "0") double carryAmount) {

        Integer currentWeek = paymentsService.getMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = paymentsService.getCurrentCalendarWeek();
        }
        int nextWeek = currentWeek + 1;

        // Close current period: mark status true and set period_end_date for current week entries
        paymentsService.closeCurrentPeriod(currentWeek);
        expenseService.closeCurrentPeriod(currentWeek);

        // If carry forward requested, save carry forward payment for next week
        if (carryForward && carryAmount > 0) {
            WeeklyPaymentsReceived carryPayment = new WeeklyPaymentsReceived();
            carryPayment.setDate(LocalDate.now());
            carryPayment.setAmount(carryAmount);
            carryPayment.setType("Carry Forward");
            carryPayment.setWeeklyNumber(nextWeek);  // Important: explicitly setting next week number
            carryPayment.setStatus(false);
            carryPayment.setPeriodStartDate(LocalDate.now());

            paymentsService.savePayment(carryPayment);
        }

        // Return new weekly number for frontend update
        return nextWeek;
    }

    @GetMapping("/active_weeks")
    public List<Integer> getActiveWeeks() {
        return paymentsService.getAllActiveWeekNumbers();
    }
    @PutMapping("/update/{id}")
    public WeeklyPaymentsReceived updatePayment(@PathVariable Long id, @RequestBody WeeklyPaymentsReceived payment) {
        return paymentsService.updatePayment(id, payment);
    }

    @GetMapping("/last-closed-week")
    public Integer getLastClosedWeek() {
        return paymentsService.getLastClosedWeek(); // Add this method in service
    }

    @PostMapping("/update/save")
    public WeeklyPaymentsReceived savePaymentReceivedForSameWeeklyNumber(@RequestBody WeeklyPaymentsReceived paymentsReceived){
        return paymentsService.savePaymentReceivedForSameWeeklyNumber(paymentsReceived);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<WeeklyPaymentsReceived> editPayment(
            @PathVariable Long id,
            @RequestBody WeeklyPaymentsReceived updatedPayment) {
        return ResponseEntity.ok(paymentsService.editPayment(id, updatedPayment));
    }
}