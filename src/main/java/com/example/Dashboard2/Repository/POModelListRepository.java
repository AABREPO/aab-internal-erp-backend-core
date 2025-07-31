package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.POModelList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface POModelListRepository extends JpaRepository<POModelList, Long> {
    Optional<POModelList> findByModelIgnoreCaseAndCategoryIgnoreCase(String model, String category);

}
