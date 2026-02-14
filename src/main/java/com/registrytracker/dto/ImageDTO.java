package com.registrytracker.dto;

import java.time.LocalDate;

public class ImageDTO {
    
    private Long id;
    private String imageName;
    private String tag;
    private String registry;
    private LocalDate createdDate;
    
    public ImageDTO() {}
    
    public ImageDTO(Long id, String imageName, String tag, String registry, LocalDate createdDate) {
        this.id = id;
        this.imageName = imageName;
        this.tag = tag;
        this.registry = registry;
        this.createdDate = createdDate;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getImageName() { return imageName; }
    public void setImageName(String imageName) { this.imageName = imageName; }
    
    public String getTag() { return tag; }
    public void setTag(String tag) { this.tag = tag; }
    
    public String getRegistry() { return registry; }
    public void setRegistry(String registry) { this.registry = registry; }
    
    public LocalDate getCreatedDate() { return createdDate; }
    public void setCreatedDate(LocalDate createdDate) { this.createdDate = createdDate; }
}
