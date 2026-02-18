package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.StaffAdvancePortal;
import com.example.Dashboard2.Entity.StaffAdvancePortalAudit;
import com.example.Dashboard2.Service.StaffAdvancePortalService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff-advance")
public class StaffAdvancePortalController {

    @Autowired
    private StaffAdvancePortalService service;

    @PostMapping("/save")
    public ResponseEntity<StaffAdvancePortal> save(@RequestBody StaffAdvancePortal staffAdvance) {
        StaffAdvancePortal savedAdvance = service.save(staffAdvance);
        return ResponseEntity.ok(savedAdvance);
    }
    @GetMapping("/all")
    public ResponseEntity<List<StaffAdvancePortal>> getAll(@RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getAll(branchId));
    }
    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<List<StaffAdvancePortal>> getByEmployee(@PathVariable int employeeId,
                                                                  @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getByEmployeeId(employeeId, branchId));
    }
    @GetMapping("/type/{type}")
    public ResponseEntity<List<StaffAdvancePortal>> getByType(@PathVariable String type,
                                                              @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getByType(type, branchId));
    }
    @GetMapping("/employee/{employeeId}/type/{type}")
    public ResponseEntity<List<StaffAdvancePortal>> getByEmployeeAndType(@PathVariable int employeeId,
                                                                          @PathVariable String type,
                                                                          @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getByEmployeeIdAndType(employeeId, type, branchId));
    }
    @GetMapping("/week/{weekNo}")
    public ResponseEntity<List<StaffAdvancePortal>> getByWeek(@PathVariable int weekNo,
                                                              @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getByWeekNo(weekNo, branchId));
    }
    @GetMapping("/employee/{employeeId}/week/{weekNo}")
    public ResponseEntity<List<StaffAdvancePortal>> getByEmployeeAndWeek(@PathVariable int employeeId,
                                                                         @PathVariable int weekNo,
                                                                         @RequestParam(required = false) Long branchId) {
        return ResponseEntity.ok(service.getByEmployeeIdAndWeekNo(employeeId, weekNo, branchId));
    }
    @GetMapping("/{id}")
    public ResponseEntity<StaffAdvancePortal> getById(@PathVariable Long id,
                                                      @RequestParam(required = false) Long branchId) {
        Optional<StaffAdvancePortal> advance = service.getById(id, branchId);
        return advance.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/{id}")
    public ResponseEntity<List<StaffAdvancePortal>> updateStaffAdvance(
            @PathVariable Long id,
            @RequestBody StaffAdvancePortal staffAdvanceDetails,
            @RequestParam String editedBy) {
        try {
            List<StaffAdvancePortal> updated = service.updateStaffAdvance(id, staffAdvanceDetails, editedBy);
            return ResponseEntity.ok(updated);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        service.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStaffAdvancePortal(@PathVariable Long id) {
        service.deleteStaffAdvancePortal(id);
        return ResponseEntity.noContent().build(); // 204 if deleted successfully
    }
    @GetMapping("/audit/history/{staffAdvancePortalId}")
    public ResponseEntity<List<StaffAdvancePortalAudit>> getAuditHistory(@PathVariable Long staffAdvancePortalId) {
        List<StaffAdvancePortalAudit> audits = service.getAuditHistory(staffAdvancePortalId);
        return ResponseEntity.ok(audits);
    }
    @PutMapping("/allow/{id}")
    public StaffAdvancePortal updateAllowToEdit(@PathVariable Long id, @RequestParam boolean allow){
        return service.updateAllowToEdit(id, allow);
    }
}