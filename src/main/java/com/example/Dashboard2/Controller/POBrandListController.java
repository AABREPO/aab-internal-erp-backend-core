package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POBrandList;
import com.example.Dashboard2.Repository.POBrandListRepository;
import com.example.Dashboard2.Service.POBrandListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/po_brand")
public class POBrandListController {

    @Autowired
    private POBrandListService poBrandListService;

    @PostMapping("/save")
    public POBrandList savePoBrandList(@RequestBody POBrandList poBrandList){
        return poBrandListService.savePoBrandList(poBrandList);
    }
    @GetMapping("/getAll")
    public List<POBrandList> getAllPOBrandList(){
        return poBrandListService.getAllPOBrandList();
    }

    @PutMapping("/edit/{id}")
    public POBrandList updatePoBrandList(@PathVariable Long id, @RequestBody POBrandList poBrandList){
        return poBrandListService.updatePOBrandList(id, poBrandList);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePoBrand(@PathVariable Long id){
        poBrandListService.deletePoBrand(id);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllPoBrandList(){
        poBrandListService.deleteAllPoBrandList();
        return "All Po Brand List Deleted Successfully";
    }

    @PostMapping("/bulkUpload")
    public String uploadPOBrandLists(@RequestParam("file") MultipartFile file) {
        return poBrandListService.uploadPOBrandLists(file);
    }
}
