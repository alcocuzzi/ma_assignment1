package com.registrytracker.dto;

import java.time.LocalDate;

public class ScanDTO {
    
    private Long id;
    private Long imageId;
    private LocalDate scanDate;
    private Integer criticalVulns;
    private Integer highVulns;
    private Integer mediumVulns;
    private Integer lowVulns;
    private String status;
    
    public ScanDTO() {}
    
    public ScanDTO(Long id, Long imageId, LocalDate scanDate, Integer criticalVulns, 
                   Integer highVulns, Integer mediumVulns, Integer lowVulns, String status) {
        this.id = id;
        this.imageId = imageId;
        this.scanDate = scanDate;
        this.criticalVulns = criticalVulns;
        this.highVulns = highVulns;
        this.mediumVulns = mediumVulns;
        this.lowVulns = lowVulns;
        this.status = status;
    }
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getImageId() { return imageId; }
    public void setImageId(Long imageId) { this.imageId = imageId; }
    
    public LocalDate getScanDate() { return scanDate; }
    public void setScanDate(LocalDate scanDate) { this.scanDate = scanDate; }
    
    public Integer getCriticalVulns() { return criticalVulns; }
    public void setCriticalVulns(Integer criticalVulns) { this.criticalVulns = criticalVulns; }
    
    public Integer getHighVulns() { return highVulns; }
    public void setHighVulns(Integer highVulns) { this.highVulns = highVulns; }
    
    public Integer getMediumVulns() { return mediumVulns; }
    public void setMediumVulns(Integer mediumVulns) { this.mediumVulns = mediumVulns; }
    
    public Integer getLowVulns() { return lowVulns; }
    public void setLowVulns(Integer lowVulns) { this.lowVulns = lowVulns; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
