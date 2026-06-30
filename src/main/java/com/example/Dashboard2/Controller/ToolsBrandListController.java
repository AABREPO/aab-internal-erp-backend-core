package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.ToolsBrandList;
import com.example.Dashboard2.Service.ToolsBrandListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tools_brand")
public class ToolsBrandListController {

    @Autowired
    private ToolsBrandListService toolsBrandListService;

    @PostMapping("/save")
    public ResponseEntity<ToolsBrandList> saveToolsBrand(@RequestBody ToolsBrandList toolsBrandList) {
        return ResponseEntity.ok(toolsBrandListService.saveToolsBrand(toolsBrandList));
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<ToolsBrandList>> getAllToolsBrand() {
        return ResponseEntity.ok(toolsBrandListService.getAllToolsBrand());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ToolsBrandList> getToolsBrandById(@PathVariable Long id) {
        return ResponseEntity.ok(toolsBrandListService.getToolsBrandById(id));
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<ToolsBrandList> updateToolsBrand(
            @PathVariable Long id,
            @RequestBody ToolsBrandList updatedToolsBrandList) {
        return ResponseEntity.ok(toolsBrandListService.updateToolsBrand(id, updatedToolsBrandList));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteToolsBrand(@PathVariable Long id) {
        toolsBrandListService.deleteToolsBrand(id);
        return ResponseEntity.ok("ToolsBrandList with ID " + id + " deleted successfully.");
    }
}

