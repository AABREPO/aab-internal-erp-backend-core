package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.POCategoryList;
import com.example.Dashboard2.Service.POCategoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/po_category")
public class POCategoryListController {

    @Autowired
    private POCategoryListService poCategoryListService;

    @PostMapping("/save")
    public POCategoryList savePoCategoryList(@RequestBody POCategoryList poCategoryList){
        return poCategoryListService.savePoCategoryList(poCategoryList);
    }
    @GetMapping("/getAll")
    public List<POCategoryList> getAllPoCategoryList(){
        return poCategoryListService.getAllPOCategoryList();
    }
    @PutMapping("/edit/{id}")
    public POCategoryList updatePoCategoryList(@PathVariable Long id, @RequestBody POCategoryList poCategoryList){
        return poCategoryListService.updatePoCategoryList(id, poCategoryList);
    }
    @DeleteMapping("/delete/{id}")
    public void deletePoCategory(@PathVariable Long id){
        poCategoryListService.deletePoCategory(id);
    }
    @DeleteMapping("/deleteAll")
    public String deleteAllPoCategoryList(){
        poCategoryListService.deleteAllPoCategoryList();
        return "All Po Category List Deleted Successfully!!";
    }
}
