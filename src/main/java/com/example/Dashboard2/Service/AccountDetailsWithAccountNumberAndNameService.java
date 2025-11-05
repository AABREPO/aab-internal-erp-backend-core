package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AccountDetailsWithAccountNumberAndName;
import com.example.Dashboard2.Repository.AccountDetailsWithAccountNumberAndNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
@Service
public class AccountDetailsWithAccountNumberAndNameService {

    @Autowired
    private AccountDetailsWithAccountNumberAndNameRepository accountNumberAndNameRepository;

    public AccountDetailsWithAccountNumberAndName save(AccountDetailsWithAccountNumberAndName data) {
        return accountNumberAndNameRepository.save(data);
    }

    public List<AccountDetailsWithAccountNumberAndName> getAll() {
        return accountNumberAndNameRepository.findAll();
    }

    public Optional<AccountDetailsWithAccountNumberAndName> getById(Long id) {
        return accountNumberAndNameRepository.findById(id);
    }

    public AccountDetailsWithAccountNumberAndName update(Long id, AccountDetailsWithAccountNumberAndName updatedData) {
        return accountNumberAndNameRepository.findById(id).map(existing -> {
            existing.setAccountHolderName(updatedData.getAccountHolderName());
            existing.setAccountNumber(updatedData.getAccountNumber());
            existing.setBankName(updatedData.getBankName());
            existing.setIfscCode(updatedData.getIfscCode());
            existing.setBranch(updatedData.getBranch());
            existing.setUpiId(updatedData.getUpiId());
            existing.setGpayNumber(updatedData.getGpayNumber());
            existing.setAccountType(updatedData.getAccountType());

            // ✅ update image only if new one is provided
            if (updatedData.getUpiQRImage() != null && updatedData.getUpiQRImage().length > 0) {
                existing.setUpiQRImage(updatedData.getUpiQRImage());
            }

            // ✅ update timestamp automatically if needed
            existing.setTimestamp(updatedData.getTimestamp() != null ? updatedData.getTimestamp() : existing.getTimestamp());

            return accountNumberAndNameRepository.save(existing);
        }).orElseThrow(() -> new RuntimeException("Record not found with id " + id));
    }

    public void deleteById(Long id) {
        accountNumberAndNameRepository.deleteById(id);
    }

    public void deleteAll() {
        accountNumberAndNameRepository.deleteAll();
    }
}
