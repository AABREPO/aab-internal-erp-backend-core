package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.ToolsBrandList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToolsBrandListRepository extends JpaRepository<ToolsBrandList, Long> {
    List<ToolsBrandList> findByToolsBrand(String toolsBrand);
}

