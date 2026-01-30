package com.example.Dashboard2.Entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ToolsItemLiveImages {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @JsonProperty("tools_image_url")
    private String toolsImageUrl;
    @Lob
    @Column(name = "tools_image", columnDefinition = "LONGBLOB")
    @JsonProperty("tools_image")
    private byte[] toolsImage;
}
