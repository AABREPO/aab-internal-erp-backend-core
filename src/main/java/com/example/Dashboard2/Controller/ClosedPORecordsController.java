package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ClosedPORecords;
import com.example.Dashboard2.Service.ClosedPORecordsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/closed_po_records")
public class ClosedPORecordsController {

    @Autowired
    private ClosedPORecordsService closedPORecordsService;

    @PostMapping("/save")
    public ClosedPORecords saveClosedPORecords(@RequestBody ClosedPORecords closedPORecords){
        return closedPORecordsService.saveClosedPORecords(closedPORecords);
    }
    @GetMapping("/getAll")
    public List<ClosedPORecords> getAllClosedPORecords(){
        return closedPORecordsService.getAllClosedPORecords();
    }
}
