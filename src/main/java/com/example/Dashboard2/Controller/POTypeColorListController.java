package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POTypeColorList;
import com.example.Dashboard2.Service.POTypeColorListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/po_type")
public class POTypeColorListController {

    @Autowired
    private POTypeColorListService poTypeColorListService;

    @PostMapping("/save")
    public POTypeColorList savePoTypeColorList(@RequestBody POTypeColorList poTypeColorList){
        return poTypeColorListService.savePoTypeColorList(poTypeColorList);
    }

    @GetMapping("/getAll")
    public List<POTypeColorList> getAllPoTypeColorList(){
        return poTypeColorListService.getAllPoTypeColorList();
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<POTypeColorList> getTypeColorById(@PathVariable Long id){
        return ResponseEntity.ok(poTypeColorListService.getPOTypeColorById(id));
    }

    @PutMapping("/edit/{id}")
    public POTypeColorList updatePoTypeColorList(@PathVariable Long id, @RequestBody POTypeColorList poTypeColorList){
        return poTypeColorListService.updatePOTypeColorList(id, poTypeColorList);
    }

    @DeleteMapping("/delete/{id}")
    public void deletePoTypeColor(@PathVariable Long id){
        poTypeColorListService.deletePoTypeColor(id);
    }

    @DeleteMapping("/deleteAll")
    public String deleteAllPoTypeColorList(){
        poTypeColorListService.deleteAllPoTypeColorList();
        return "All Po Type/Color List Deleted Successfully";
    }

    @PostMapping("/bulkUpload")
    public String uploadPOTypeLists(@RequestParam("file") MultipartFile file) {
        return poTypeColorListService.uploadPOTypeLists(file);
    }

}
