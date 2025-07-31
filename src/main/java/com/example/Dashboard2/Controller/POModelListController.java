package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POModelList;
import com.example.Dashboard2.Service.POModelListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/po_model")
public class POModelListController {

    @Autowired
    private POModelListService poModelListService;

    @PostMapping("/save")
    public POModelList savePoModelList(@RequestBody POModelList poModelList){
        return poModelListService.savePoModelList(poModelList);
    }
    @GetMapping("/getAll")
    public List<POModelList> getAllPOModelList(){
        return poModelListService.getAllPOModelList();
    }
    @PutMapping("/edit/{id}")
    public POModelList updatePoModelList(@PathVariable Long id, @RequestBody POModelList poModelList){
        return poModelListService.updatePOModelList(id, poModelList);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePoModel(@PathVariable Long id){
        poModelListService.deletePoModel(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllPoModelList(){
        poModelListService.deleteAllPoModelList();
        return "All Po Model List Deleted Successfully!!";
    }

    @PostMapping("/model_bulkUpload")
    public String uploadPOModelList(@RequestParam("file") MultipartFile file) {
        return poModelListService.uploadPOModels(file);
    }
}