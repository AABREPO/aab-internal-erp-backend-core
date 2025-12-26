package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.MasterDataTableDataCorrectionMonitoring;
import com.example.Dashboard2.Repository.MasterDataTableDataCorrectionMonitoringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MasterDataTableDataCorrectionMonitoringService {

    @Autowired
    private MasterDataTableDataCorrectionMonitoringRepository monitoringRepository;

    public MasterDataTableDataCorrectionMonitoring saveMasterDataTableDataCorrection(MasterDataTableDataCorrectionMonitoring dataCorrectionMonitoring){
        return monitoringRepository.save(dataCorrectionMonitoring);
    }

    public List<MasterDataTableDataCorrectionMonitoring> getAllTableDataCorrectionMonitoring(){
        return monitoringRepository.findAll();
    }

    public List<MasterDataTableDataCorrectionMonitoring> getByTableNameAndDataId(
            String tableName,
            Long dataId
    ) {
        return monitoringRepository.findByTableNameAndDataId(tableName, dataId);
    }


}
