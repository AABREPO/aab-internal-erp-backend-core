package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import java.util.List;

@Entity
public class ToolsItemNameList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("category_id")
    private String categoryId;
    @JsonProperty("item_name")
    private String itemName;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "tools_item_name_list_id")
    @JsonProperty("tools_details")
    private List<ToolsItemNameWithOtherDetails> toolsDetails;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public List<ToolsItemNameWithOtherDetails> getToolsDetails() {
        return toolsDetails;
    }

    public void setToolsDetails(List<ToolsItemNameWithOtherDetails> toolsDetails) {
        this.toolsDetails = toolsDetails;
    }
}
