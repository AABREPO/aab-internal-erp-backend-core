package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.MasterDataTableDataCorrectionMonitoring;
import com.example.Dashboard2.Service.MasterDataTableDataCorrectionMonitoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/data_correction")
public class MasterDataTableDataCorrectionMonitoringController {

    @Autowired
    private MasterDataTableDataCorrectionMonitoringService tableDataCorrectionMonitoringService;

    @PostMapping("/save")
    public MasterDataTableDataCorrectionMonitoring saveTableDataCorrectionMonitoring(@RequestBody MasterDataTableDataCorrectionMonitoring dataCorrectionMonitoring){
        return tableDataCorrectionMonitoringService.saveMasterDataTableDataCorrection(dataCorrectionMonitoring);
    }
    @GetMapping("/getAll")
    public List<MasterDataTableDataCorrectionMonitoring> getAllTableDataCorrectionMonitoring(){
        return tableDataCorrectionMonitoringService.getAllTableDataCorrectionMonitoring();
    }
    @GetMapping("/monitoring/{tableName}/{dataId}")
    public List<MasterDataTableDataCorrectionMonitoring> getByTableNameAndDataId(
            @PathVariable String tableName,
            @PathVariable Long dataId
    ) {
        return tableDataCorrectionMonitoringService.getByTableNameAndDataId(tableName, dataId);
    }

}
