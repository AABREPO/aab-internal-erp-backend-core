package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.POItemNameList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface POItemNameListRepo extends JpaRepository<POItemNameList, Long> {
}
