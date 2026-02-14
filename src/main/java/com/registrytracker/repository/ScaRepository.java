package com.registrytracker.repository;

import com.registrytracker.entity.SecurityScan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScanRepository extends JpaRepository<SecurityScan, Long> {
    
    List<SecurityScan> findByDockerImageId(Long imageId);
    
    List<SecurityScan> findByScanDateBetween(LocalDate fromDate, LocalDate toDate);
}
