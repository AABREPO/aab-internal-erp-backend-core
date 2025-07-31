package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.InventoryStockingLocation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryStockingLocationRepository extends JpaRepository<InventoryStockingLocation,Long> {
}
