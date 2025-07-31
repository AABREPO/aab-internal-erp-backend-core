package com.example.Dashboard2.Controller;

import com.example.Dashboard2.DTO.ConfirmedAgreementUrlDTO;
import com.example.Dashboard2.Entity.AgreementPropertyDataWithFileNames;
import com.example.Dashboard2.Service.AgreementPropertyDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/agreements")
public class AgreementPropertyDataController {

    @Autowired
    private AgreementPropertyDataService service;

    @PostMapping("/save")
    public AgreementPropertyDataWithFileNames saveAgreement(@RequestBody AgreementPropertyDataWithFileNames agreement) {
        return service.saveAgreement(agreement);
    }

    @GetMapping("/all")
    public List<AgreementPropertyDataWithFileNames> getAllAgreements() {
        return service.getAllAgreements();
    }

    @GetMapping("/{id}")
    public AgreementPropertyDataWithFileNames getAgreementById(@PathVariable Long id) {
        return service.getAgreementById(id)
                .orElseThrow(() -> new RuntimeException("Agreement not found with ID: " + id));
    }

    @PutMapping("/edit/{id}")
    public AgreementPropertyDataWithFileNames updateAgreement(
            @PathVariable Long id,
            @RequestBody AgreementPropertyDataWithFileNames agreement
    ) {
        return service.updateAgreement(id, agreement);
    }


    @PutMapping("/updateConfirmedUrl/{id}")
    public AgreementPropertyDataWithFileNames updateConfirmedAgreementUrl(
            @PathVariable Long id,
            @RequestBody ConfirmedAgreementUrlDTO dto) {
        return service.updateConfirmedAgreementUrl(id, dto.getConfirmedAgreementUrl());
    }

    @DeleteMapping("/delete/{id}")
    public void deleteAgreementById(@PathVariable Long id) {
        service.deleteAgreementById(id);
    }

    @DeleteMapping("/deleteAll")
    public void deleteAllAgreements() {
        service.deleteAllAgreements();
    }
}
