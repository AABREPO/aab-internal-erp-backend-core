package com.example.Dashboard2.Controller;

import com.example.Dashboard2.Entity.UtilityDirectorySubscription;
import com.example.Dashboard2.Service.UtilityDirectorySubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/utility-subscription")
public class UtilityDirectorySubscriptionController {

    private final UtilityDirectorySubscriptionService service;

    public UtilityDirectorySubscriptionController(UtilityDirectorySubscriptionService service){
        this.service = service;
    }

    @PostMapping("/save")
    public UtilityDirectorySubscription save(@RequestBody UtilityDirectorySubscription utilityDirectorySubscription){
        return service.save(utilityDirectorySubscription);
    }

    @PutMapping("/update/{id}")
    public UtilityDirectorySubscription update(@PathVariable Long id, @RequestBody UtilityDirectorySubscription utilityDirectorySubscription){
        return service.update(id, utilityDirectorySubscription);
    }

    @GetMapping("/getAll")
    public List<UtilityDirectorySubscription> getAll(){
        return service.getAll();
    }

    @GetMapping("/get/{id}")
    public UtilityDirectorySubscription getById(@PathVariable Long id){
        return service.getById(id);
    }
    @DeleteMapping("/delete/{id}")
    public void delete(@PathVariable Long id){
        service.delete(id);
    }
}
