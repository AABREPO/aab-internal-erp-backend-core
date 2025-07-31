package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.MappedPoCategoryList;
import com.example.Dashboard2.Service.MappedPoCategoryListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/mapped/category")
public class MappedPoCategoryListController {

    @Autowired
    private MappedPoCategoryListService service;

    @PostMapping("/save")
    public MappedPoCategoryList save(@RequestBody MappedPoCategoryList category) {
        return service.save(category);
    }

    @GetMapping("/getAll")
    public List<MappedPoCategoryList> getAll() {
        return service.getAll();
    }

    @PutMapping("/{id}")
    public MappedPoCategoryList update(@PathVariable Long id, @RequestBody MappedPoCategoryList category) {
        return service.update(id, category);
    }

    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteById(id);
    }

}
