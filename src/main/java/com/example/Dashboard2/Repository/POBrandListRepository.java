package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.POBrandList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface POBrandListRepository extends JpaRepository<POBrandList, Long> {
    Optional<POBrandList> findByBrandIgnoreCaseAndCategoryIgnoreCase(String brand, String category);
}
