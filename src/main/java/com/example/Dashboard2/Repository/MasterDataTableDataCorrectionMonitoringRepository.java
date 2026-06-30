package com.example.Dashboard2.Repository;

import com.example.Dashboard2.Entity.MasterDataTableDataCorrectionMonitoring;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MasterDataTableDataCorrectionMonitoringRepository extends JpaRepository<MasterDataTableDataCorrectionMonitoring, Long> {

    List<MasterDataTableDataCorrectionMonitoring> findByTableNameAndDataId(
            String tableName,
            Long dataId
    );
}
