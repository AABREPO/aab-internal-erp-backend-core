package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.AccountDetailsWithAccountNumberAndName;
import com.example.Dashboard2.Service.AccountDetailsWithAccountNumberAndNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
@RestController
@RequestMapping("/api/account-details")
public class AccountDetailsWithAccountNumberAndNameController {

    @Autowired
    private AccountDetailsWithAccountNumberAndNameService accountNumberAndNameService;

    @PostMapping("/save")
    public ResponseEntity<AccountDetailsWithAccountNumberAndName> save(
            @RequestBody AccountDetailsWithAccountNumberAndName data) {
        return ResponseEntity.ok(accountNumberAndNameService.save(data));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<AccountDetailsWithAccountNumberAndName>> getAll() {
        return ResponseEntity.ok(accountNumberAndNameService.getAll());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<AccountDetailsWithAccountNumberAndName> getById(@PathVariable Long id) {
        return accountNumberAndNameService.getById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<AccountDetailsWithAccountNumberAndName> update(
            @PathVariable Long id,
            @RequestBody AccountDetailsWithAccountNumberAndName updatedData) {
        return ResponseEntity.ok(accountNumberAndNameService.update(id, updatedData));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        accountNumberAndNameService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/delete-all")
    public ResponseEntity<Void> deleteAll() {
        accountNumberAndNameService.deleteAll();
        return ResponseEntity.noContent().build();
    }
}
