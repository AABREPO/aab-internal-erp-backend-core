package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AgreementPropertyOwnerDetails;
import com.example.Dashboard2.Entity.AgreementPropertyName;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface AgreementPropertyOwnerDetailsRepository extends JpaRepository<AgreementPropertyOwnerDetails, Long> {

    // Find an existing AgreementPropertyOwnerDetails by AgreementPropertyName and Owner Name
    Optional<AgreementPropertyOwnerDetails> findByAgreementPropertyNameAndOwnerName(
            AgreementPropertyName agreementPropertyName, String ownerName);
}
