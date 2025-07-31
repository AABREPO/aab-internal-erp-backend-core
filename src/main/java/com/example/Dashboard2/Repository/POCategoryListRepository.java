package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.POCategoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POCategoryListRepository extends JpaRepository<POCategoryList, Long> {
}
