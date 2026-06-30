package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.AdvancePortal;
import com.example.Dashboard2.Entity.AdvancePortalAudit;
import com.example.Dashboard2.Service.AdvancePortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/advance_portal")
public class AdvancePortalController {

    @Autowired
    private AdvancePortalService advancePortalService;

    /**
     * Returns every row — avoid from the browser for large datasets (can freeze the tab).
     * Use {@code /paged} or {@code /getLast250} instead.
     */
    @GetMapping("/getAll")
    public List<AdvancePortal> getAllAdvancePortals() {
        return advancePortalService.getAllAdvancePortals();
    }

    /**
     * Paginated list (default: 50 rows, newest first). Query: {@code ?page=0&size=50&sort=advancePortalId,desc}
     */
    @GetMapping("/paged")
    public Page<AdvancePortal> getAdvancePortalsPaged(
            @PageableDefault(size = 50, sort = "advancePortalId", direction = Sort.Direction.DESC) Pageable pageable) {
        return advancePortalService.getAdvancePortalsPage(pageable);
    }

    @GetMapping("/getLast150")
    public List<AdvancePortal> getLast150AdvancePortals() {
        return advancePortalService.getLast150AdvancePortals();
    }

    @GetMapping("/getLast250")
    public List<AdvancePortal> getLast250AdvancePortals() {
        return advancePortalService.getLast250AdvancePortals();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AdvancePortal> getAdvancePortalById(@PathVariable Long id) {
        return advancePortalService.getAdvancePortalById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public ResponseEntity<AdvancePortal> createAdvancePortal(@RequestBody AdvancePortal advancePortal) {
        try {
            AdvancePortal created = advancePortalService.createAdvancePortal(advancePortal);
            return ResponseEntity.ok(created);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<AdvancePortal> updateAdvancePortal(
            @PathVariable Long id,
            @RequestBody AdvancePortal updatedPortal,
            @RequestParam String editedBy
    ) {
        try {
            AdvancePortal updated = advancePortalService.updateAdvancePortal(id, updatedPortal, editedBy);
            if (updated != null) {
                return ResponseEntity.ok(updated);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvancePortal(@PathVariable Long id) {
        advancePortalService.deleteAdvancePortal(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/history/{id}")
    public List<AdvancePortalAudit> getAuditHistory(@PathVariable int id) {
        return advancePortalService.getAuditHistory(id);
    }

    @PostMapping("/upload-sql")
    public ResponseEntity<String> uploadAdvancePortalSql(@RequestParam("file") MultipartFile file) {
        String result = advancePortalService.uploadAdvancePortalData(file);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AdvancePortal> updateDescription(
            @PathVariable Long id,
            @RequestBody AdvancePortal advancePortal
    ) {
        AdvancePortal updated = advancePortalService.updateDescription(id, advancePortal.getDescription());

        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/allow/{id}")
    public AdvancePortal updateAllowToEdit(
            @PathVariable Long id,
            @RequestParam boolean allow) {
        return advancePortalService.updateAllowToEdit(id, allow);
    }

}