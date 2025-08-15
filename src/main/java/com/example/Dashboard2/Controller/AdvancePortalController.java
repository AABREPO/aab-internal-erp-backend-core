package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.AdvancePortal;
import com.example.Dashboard2.Entity.AdvancePortalAudit;
import com.example.Dashboard2.Service.AdvancePortalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/advance_portal")
public class AdvancePortalController {

    @Autowired
    private AdvancePortalService advancePortalService;

    @GetMapping("/getAll")
    public List<AdvancePortal> getAllAdvancePortals() {
        return advancePortalService.getAllAdvancePortals();
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
        AdvancePortal updated = advancePortalService.updateAdvancePortal(id, updatedPortal, editedBy);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAdvancePortal(@PathVariable Long id) {
        if (advancePortalService.deleteAdvancePortal(id)) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
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

}
