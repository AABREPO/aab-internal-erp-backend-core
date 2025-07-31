package com.example.Dashboard2.Service;

import com.example.Dashboard2.DTO.RentFormEdit;
import com.example.Dashboard2.Entity.RentFormAudit;
import com.example.Dashboard2.Entity.RentalForm;

import java.util.List;

public interface RentalFormServices {
    RentalForm saveRentalForm(RentalForm rentalForm);
    List<RentalForm> getAllRentForms();
    boolean updateRentForm(Long id, RentFormEdit rentFormEdit);
    List<RentFormAudit> getRentalFormById(Long rentFormId);
    boolean clearRentalFormData(Long id, String editedBy);
}
