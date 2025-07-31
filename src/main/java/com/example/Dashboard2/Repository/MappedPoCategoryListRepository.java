package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.MappedPoCategoryList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MappedPoCategoryListRepository extends JpaRepository<MappedPoCategoryList,Long> {
}
