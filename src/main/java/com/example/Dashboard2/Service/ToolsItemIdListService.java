package com.example.Dashboard2.Service;

import com.example.Dashboard2.Entity.ToolsItemIdList;
import com.example.Dashboard2.Repository.ToolsItemIdListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ToolsItemIdListService {

    @Autowired
    private ToolsItemIdListRepository toolsItemIdListRepository;

    public ToolsItemIdList saveToolsItemId(ToolsItemIdList toolsItemIdList) {
        return toolsItemIdListRepository.save(toolsItemIdList);
    }

    public List<ToolsItemIdList> getAllToolsItemId() {
        return toolsItemIdListRepository.findAll();
    }

    public ToolsItemIdList getToolsItemIdById(Long id) {
        return toolsItemIdListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ToolsItemIdList not found with id " + id));
    }

    public ToolsItemIdList updateToolsItemId(Long id, ToolsItemIdList updatedToolsItemIdList) {
        return toolsItemIdListRepository.findById(id)
                .map(existing -> {
                    if (updatedToolsItemIdList.getItemId() != null) {
                        existing.setItemId(updatedToolsItemIdList.getItemId());
                    }
                    return toolsItemIdListRepository.save(existing);
                })
                .orElseThrow(() -> new RuntimeException("ToolsItemIdList not found with id " + id));
    }

    public void deleteToolsItemId(Long id) {
        if (!toolsItemIdListRepository.existsById(id)) {
            throw new RuntimeException("ToolsItemIdList not found with id " + id);
        }
        toolsItemIdListRepository.deleteById(id);
    }
}

