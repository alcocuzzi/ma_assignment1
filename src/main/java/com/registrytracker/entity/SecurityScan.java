package com.registrytracker.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "scans")
public class SecurityScan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "image_id")
    private DockerImage dockerImage;
    
    @Column(name = "scan_date")
    private LocalDate scanDate;
    
    @Column(name = "critical_vulns")
    private Integer criticalVulns;
    
    @Column(name = "high_vulns")
    private Integer highVulns;
    
    @Column(name = "medium_vulns")
    private Integer mediumVulns;
    
    @Column(name = "low_vulns")
    private Integer lowVulns;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "scanner_version")
    private String scannerVersion;  // Internal field - NOT exposed in API
    
    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public DockerImage getDockerImage() { return dockerImage; }
    public void setDockerImage(DockerImage dockerImage) { this.dockerImage = dockerImage; }
    
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
    
    public String getScannerVersion() { return scannerVersion; }
    public void setScannerVersion(String scannerVersion) { this.scannerVersion = scannerVersion; }
}
