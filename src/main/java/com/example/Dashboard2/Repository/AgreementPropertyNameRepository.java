package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.AgreementPropertyName;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AgreementPropertyNameRepository extends JpaRepository<AgreementPropertyName, Long> {

    @EntityGraph(attributePaths = {"ownerDetailsList", "propertyDetailsList"})
    List<AgreementPropertyName> findAll();
}

