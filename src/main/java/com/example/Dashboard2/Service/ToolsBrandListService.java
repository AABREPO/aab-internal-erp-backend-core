package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsBrandList;
import com.example.Dashboard2.Repository.ToolsBrandListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolsBrandListService {

    @Autowired
    private ToolsBrandListRepository toolsBrandListRepository;

    public ToolsBrandList saveToolsBrand(ToolsBrandList toolsBrandList) {
        return toolsBrandListRepository.save(toolsBrandList);
    }

    public List<ToolsBrandList> getAllToolsBrand() {
        return toolsBrandListRepository.findAll();
    }

    public ToolsBrandList getToolsBrandById(Long id) {
        return toolsBrandListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ToolsBrandList not found with id " + id));
    }

    public ToolsBrandList updateToolsBrand(Long id, ToolsBrandList updatedToolsBrandList) {
        return toolsBrandListRepository.findById(id)
                .map(existing -> {
                    if (updatedToolsBrandList.getToolsBrand() != null) {
                        existing.setToolsBrand(updatedToolsBrandList.getToolsBrand());
                    }
                    return toolsBrandListRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsBrandList not found with id " + id));
    }

    public void deleteToolsBrand(Long id) {
        if (!toolsBrandListRepository.existsById(id)) {
            throw new RuntimeException("ToolsBrandList not found with id " + id);
        }
        toolsBrandListRepository.deleteById(id);
    }
}

