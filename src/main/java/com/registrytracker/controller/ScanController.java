package com.registrytracker.controller;

import com.registrytracker.dto.ScanDTO;
import com.registrytracker.entity.DockerImage;
import com.registrytracker.entity.SecurityScan;
import com.registrytracker.exception.ResourceNotFoundException;
import com.registrytracker.repository.ImageRepository;
import com.registrytracker.repository.ScanRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class ScanController {
    
    private final ScanRepository scanRepository;
    private final ImageRepository imageRepository;
    
    public ScanController(ScanRepository scanRepository, ImageRepository imageRepository) {
        this.scanRepository = scanRepository;
        this.imageRepository = imageRepository;
    }
    
    @GetMapping("/images/{imageId}/scans")
    public List<ScanDTO> getScansByImageId(@PathVariable Long imageId) {
        if (!imageRepository.existsById(imageId)) {
            throw new ResourceNotFoundException("Docker image", imageId);
        }
        return scanRepository.findByDockerImageId(imageId).stream()
            .map(this::toDTO)
            .toList();
    }
    
    @PostMapping("/images/{imageId}/scans")
    @ResponseStatus(HttpStatus.CREATED)
    public ScanDTO createScan(@PathVariable Long imageId, @RequestBody ScanDTO scanDTO) {
        DockerImage image = imageRepository.findById(imageId)
            .orElseThrow(() -> new ResourceNotFoundException("Docker image", imageId));
        SecurityScan scan = toEntity(scanDTO);
        scan.setDockerImage(image);
        return toDTO(scanRepository.save(scan));
    }
    
    @GetMapping("/scans")
    public List<ScanDTO> getAllScans(
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate fromDate,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate toDate) {
        
        List<SecurityScan> scans = (fromDate != null && toDate != null) 
            ? scanRepository.findByScanDateBetween(fromDate, toDate)
            : scanRepository.findAll();
        
        return scans.stream().map(this::toDTO).toList();
    }
    
    @GetMapping("/scans/{id}")
    public ScanDTO getScanById(@PathVariable Long id) {
        return toDTO(scanRepository.findById(id)
            .orElseThrow(() -> new ResourceNotFoundException("Security scan", id)));
    }
    
    @DeleteMapping("/scans/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteScan(@PathVariable Long id) {
        if (!scanRepository.existsById(id)) {
            throw new ResourceNotFoundException("Security scan", id);
        }
        scanRepository.deleteById(id);
    }
    
    // DTO Mapping
    private ScanDTO toDTO(SecurityScan scan) {
        return new ScanDTO(
            scan.getId(),
            scan.getDockerImage().getId(),
            scan.getScanDate(),
            scan.getCriticalVulns(),
            scan.getHighVulns(),
            scan.getMediumVulns(),
            scan.getLowVulns(),
            scan.getStatus()
        );
    }
    
    private SecurityScan toEntity(ScanDTO dto) {
        SecurityScan scan = new SecurityScan();
        scan.setScanDate(dto.getScanDate());
        scan.setCriticalVulns(dto.getCriticalVulns());
        scan.setHighVulns(dto.getHighVulns());
        scan.setMediumVulns(dto.getMediumVulns());
        scan.setLowVulns(dto.getLowVulns());
        scan.setStatus(dto.getStatus());
        // Internal field - not exposed in DTO, auto-populated
        scan.setScannerVersion("Trivy-v0.48.0");
        return scan;
    }
}
