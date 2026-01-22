package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.WeeklyPaymentTypes;
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
    @GetMapping("/getAll")
    public List<WeeklyPaymentsReceived> getAllTypes(){
        return paymentsService.getAllWeeklyPaymentsReceived();
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
            @RequestParam String closureType,
            @RequestParam(required = false, defaultValue = "false") boolean carryForward,
            @RequestParam(required = false, defaultValue = "0") double carryAmount,
            @RequestParam(required = false, defaultValue = "0") double discountAmount,
            @RequestParam(required = false) Integer currentWeek) {  // Add this parameter
        Integer weekToClose = currentWeek != null ? currentWeek : paymentsService.getMaxWeeklyNumber();
        if (weekToClose == null) {
            weekToClose = paymentsService.getCurrentCalendarWeek();
        }
        int nextWeek = weekToClose + 1;
        // ... rest of the code using weekToClose instead of currentWeek
        // Close current periods
        paymentsService.closeCurrentPeriod(currentWeek);
        expenseService.closeCurrentPeriod(currentWeek);
        if (carryForward && carryAmount > 0) {
            WeeklyPaymentsReceived carryPayment = new WeeklyPaymentsReceived();
            carryPayment.setDate(LocalDate.now());
            carryPayment.setAmount(carryAmount);
            carryPayment.setType(closureType);
            int targetWeek = "Handover".equalsIgnoreCase(closureType) ? currentWeek : nextWeek;
            carryPayment.setWeeklyNumber(targetWeek);
            carryPayment.setStatus(false);
            carryPayment.setPeriodStartDate(LocalDate.now());
            carryPayment.setDiscountAmount(discountAmount);
            paymentsService.savePayment(carryPayment);
        }
        return nextWeek;
    }
    @PostMapping("/account")
    public Integer accountClosure(
            @RequestParam String closureType,
            @RequestParam(required = false, defaultValue = "false") boolean carryForward,
            @RequestParam(required = false, defaultValue = "0") double carryAmount,
            @RequestParam(required = false, defaultValue = "0") double discountAmount) {
        Integer currentWeek = paymentsService.getMaxWeeklyNumber();
        if (currentWeek == null) {
            currentWeek = paymentsService.getCurrentCalendarWeek();
        }
        int nextWeek = currentWeek + 1;
        // Close current periods
        paymentsService.closeCurrentPeriod(currentWeek);
        expenseService.closeCurrentPeriod(currentWeek);
        if (carryForward && carryAmount > 0) {
            WeeklyPaymentsReceived carryPayment = new WeeklyPaymentsReceived();
            carryPayment.setDate(LocalDate.now());
            carryPayment.setAmount(carryAmount);
            carryPayment.setType(closureType);
            int targetWeek = "Handover".equalsIgnoreCase(closureType) ? currentWeek : nextWeek;
            carryPayment.setWeeklyNumber(targetWeek);
            carryPayment.setStatus(false);
            carryPayment.setPeriodStartDate(LocalDate.now());
            carryPayment.setDiscountAmount(discountAmount);
            paymentsService.savePayment(carryPayment);
        }
        return nextWeek;
    }
    @GetMapping("/active_weeks")
    public List<Integer> getActiveWeeks() {
        return paymentsService.getAllActiveWeekNumbers();
    }
    @PutMapping("/update/{id}")
    public WeeklyPaymentsReceived updatePayment(@PathVariable Long id,@RequestParam String username, @RequestBody WeeklyPaymentsReceived payment) {
        return paymentsService.updatePayment(id, username ,payment);
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
            @RequestParam String username,
            @RequestBody WeeklyPaymentsReceived updatedPayment) {
        return ResponseEntity.ok(paymentsService.editPayment(id, username,updatedPayment));
    }
    @DeleteMapping("/delete/{id}")
    public void deleteWeeklyPaymentsReceived(@PathVariable Long id){
        paymentsService.deleteWeeklyPaymentsReceived(id);
    }
}