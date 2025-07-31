package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.AgreementPropertyDetailsWithDoorNoAndFloorName;
import com.example.Dashboard2.Entity.AgreementPropertyName;
import com.example.Dashboard2.Entity.AgreementPropertyOwnerDetails;
import com.example.Dashboard2.Repository.AgreementPropertyNameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AgreementPropertyService {

    @Autowired
    private AgreementPropertyNameRepository propertyRepository;

    public AgreementPropertyName saveProperty(AgreementPropertyName property) {
        if (property.getOwnerDetailsList() != null) {
            for (AgreementPropertyOwnerDetails owner : property.getOwnerDetailsList()) {
                owner.setAgreementPropertyName(property);
            }
        }
        if (property.getPropertyDetailsList() != null) {
            for (AgreementPropertyDetailsWithDoorNoAndFloorName detail : property.getPropertyDetailsList()) {
                detail.setAgreementPropertyName(property);
            }
        }
        return propertyRepository.save(property);
    }

    public List<AgreementPropertyName> getAllProperties() {
        return propertyRepository.findAll();
    }

    public Optional<AgreementPropertyName> getPropertyById(Long id) {
        return propertyRepository.findById(id);
    }

    public AgreementPropertyName updateProperty(Long id, AgreementPropertyName updatedProperty) {
        return propertyRepository.findById(id).map(existing -> {
            existing.setPropertyName(updatedProperty.getPropertyName());
            existing.setPropertyAddress(updatedProperty.getPropertyAddress());
            // Replace owners
            existing.getOwnerDetailsList().clear();
            if (updatedProperty.getOwnerDetailsList() != null) {
                updatedProperty.getOwnerDetailsList().forEach(owner -> {
                    owner.setAgreementPropertyName(existing);
                    existing.getOwnerDetailsList().add(owner);
                });
            }
            // Replace property details
            existing.getPropertyDetailsList().clear();
            if (updatedProperty.getPropertyDetailsList() != null) {
                updatedProperty.getPropertyDetailsList().forEach(detail -> {
                    detail.setAgreementPropertyName(existing);
                    existing.getPropertyDetailsList().add(detail);
                });
            }
            return propertyRepository.saveAndFlush(existing); // <-- ensure immediate DB sync
        }).orElseThrow(() -> new RuntimeException("Property not found with id: " + id));
    }
    public void deleteById(Long id) {
        propertyRepository.deleteById(id);
    }

    public void deleteAll() {
        propertyRepository.deleteAll();
    }
}
