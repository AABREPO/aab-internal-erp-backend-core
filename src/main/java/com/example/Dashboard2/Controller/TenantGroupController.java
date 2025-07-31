package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.TenantGroup;
import com.example.Dashboard2.Service.TenantGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tenant-groups")
public class TenantGroupController {

    @Autowired
    private TenantGroupService tenantGroupService;

    @PostMapping("/bulk-save")
    public List<TenantGroup> createMultipleTenantGroups(@RequestBody List<TenantGroup> groups) {
        return tenantGroupService.saveMultipleTenantGroups(groups);
    }

    @PostMapping("/save")
    public TenantGroup createTenantGroup(@RequestBody TenantGroup group) {
        return tenantGroupService.saveTenantGroup(group);
    }
    @GetMapping("/all")
    public List<TenantGroup> getAllTenantGroups() {
        return tenantGroupService.getAllTenantGroups();
    }
    @GetMapping("/{id}")
    public Optional<TenantGroup> getTenantGroupById(@PathVariable Long id) {
        return tenantGroupService.getTenantGroupById(id);
    }
    @PutMapping("/update/{id}")
    public TenantGroup updateTenantGroup(@PathVariable Long id, @RequestBody TenantGroup group) {
        return tenantGroupService.updateTenantGroup(id, group);
    }
    @DeleteMapping("/delete/{id}")
    public void deleteTenantGroup(@PathVariable Long id) {
        tenantGroupService.deleteTenantGroup(id);
    }
    @DeleteMapping("/delete-all")
    public void deleteAllTenantGroups() {
        tenantGroupService.deleteAllTenantGroups();
    }

}
