package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.POTypeColorList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface POTypeColorListRepository extends JpaRepository<POTypeColorList, Long> {
    Optional<POTypeColorList> findByTypeColorIgnoreCaseAndCategoryIgnoreCase(String typeColor, String category);
}
