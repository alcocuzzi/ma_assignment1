package com.registrytracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "images")
public class DockerImage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "image_name")
    private String imageName;
    
    @Column(name = "tag")
    private String tag;
    
    @Column(name = "registry")
    private String registry;
    
    @Column(name = "created_date")
    private LocalDate createdDate;
    
    @OneToMany(mappedBy = "dockerImage", cascade = CascadeType.ALL)
    private List<SecurityScan> securityScans = new ArrayList<>();
    
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
    
    public List<SecurityScan> getSecurityScans() { return securityScans; }
    public void setSecurityScans(List<SecurityScan> securityScans) { this.securityScans = securityScans; }
}
