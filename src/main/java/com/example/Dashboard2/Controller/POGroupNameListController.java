package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POGroupNameList;
import com.example.Dashboard2.Service.POGroupNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/group_name")
public class POGroupNameListController {

    @Autowired
    private POGroupNameService poGroupNameService;


    @PostMapping("/save")
    public POGroupNameList saveGroupName(@RequestBody POGroupNameList poGroupNameList){
        return poGroupNameService.saveGroupNameList(poGroupNameList);
    }
    @GetMapping("/getAll")
    public List<POGroupNameList> getAllGroupNameLst(){
        return poGroupNameService.getAllPOGroupNameList();
    }
    @PutMapping("/edit/{id}")
    public POGroupNameList updatePOGroupNameList(@PathVariable Long id, @RequestBody POGroupNameList poGroupNameList){
        return poGroupNameService.updatePOGroupNameList(id, poGroupNameList);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePOGroupName(@PathVariable Long id){
        poGroupNameService.deletePOGroupName(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllPoGroupNameList(){
        poGroupNameService.deleteAllPOGroupNameList();
        return "All Po Group Name List Deleted Successfully";
    }
}
