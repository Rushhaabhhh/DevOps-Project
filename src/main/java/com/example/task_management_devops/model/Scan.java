package com.example.task_management_devops.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

/**
 * Entity representing a security scan of a project.
 * Tracks scan metadata and aggregated vulnerability statistics.
 */
@Entity
@Table(name = "scans")
public class Scan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "Project name is required")
    @Column(nullable = false)
    private String projectName;
    
    @NotBlank(message = "Project version is required")
    @Column(nullable = false)
    private String projectVersion;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ScanStatus status = ScanStatus.PENDING;
    
    @Column(name = "scan_date", nullable = false)
    private LocalDateTime scanDate;
    
    @Column(name = "total_dependencies")
    private Integer totalDependencies = 0;
    
    @Column(name = "vulnerable_dependencies")
    private Integer vulnerableDependencies = 0;
    
    @Column(name = "critical_count")
    private Integer criticalCount = 0;
    
    @Column(name = "high_count")
    private Integer highCount = 0;
    
    @Column(name = "medium_count")
    private Integer mediumCount = 0;
    
    @Column(name = "low_count")
    private Integer lowCount = 0;
    
    @Column(name = "scan_duration_ms")
    private Long scanDurationMs;
    
    @OneToMany(mappedBy = "scan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vulnerability> vulnerabilities = new ArrayList<>();
    
    @PrePersist
    protected void onCreate() {
        scanDate = LocalDateTime.now();
    }
    
    // Constructors
    public Scan() {
    }
    
    public Scan(String projectName, String projectVersion) {
        this.projectName = projectName;
        this.projectVersion = projectVersion;
        this.status = ScanStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    
    public String getProjectVersion() {
        return projectVersion;
    }
    
    public void setProjectVersion(String projectVersion) {
        this.projectVersion = projectVersion;
    }
    
    public ScanStatus getStatus() {
        return status;
    }
    
    public void setStatus(ScanStatus status) {
        this.status = status;
    }
    
    public LocalDateTime getScanDate() {
        return scanDate;
    }
    
    public void setScanDate(LocalDateTime scanDate) {
        this.scanDate = scanDate;
    }
    
    public Integer getTotalDependencies() {
        return totalDependencies;
    }
    
    public void setTotalDependencies(Integer totalDependencies) {
        this.totalDependencies = totalDependencies;
    }
    
    public Integer getVulnerableDependencies() {
        return vulnerableDependencies;
    }
    
    public void setVulnerableDependencies(Integer vulnerableDependencies) {
        this.vulnerableDependencies = vulnerableDependencies;
    }
    
    public Integer getCriticalCount() {
        return criticalCount;
    }
    
    public void setCriticalCount(Integer criticalCount) {
        this.criticalCount = criticalCount;
    }
    
    public Integer getHighCount() {
        return highCount;
    }
    
    public void setHighCount(Integer highCount) {
        this.highCount = highCount;
    }
    
    public Integer getMediumCount() {
        return mediumCount;
    }
    
    public void setMediumCount(Integer mediumCount) {
        this.mediumCount = mediumCount;
    }
    
    public Integer getLowCount() {
        return lowCount;
    }
    
    public void setLowCount(Integer lowCount) {
        this.lowCount = lowCount;
    }
    
    public Long getScanDurationMs() {
        return scanDurationMs;
    }
    
    public void setScanDurationMs(Long scanDurationMs) {
        this.scanDurationMs = scanDurationMs;
    }
    
    public List<Vulnerability> getVulnerabilities() {
        return vulnerabilities;
    }
    
    public void setVulnerabilities(List<Vulnerability> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }
    
    public void addVulnerability(Vulnerability vulnerability) {
        vulnerabilities.add(vulnerability);
        vulnerability.setScan(this);
    }
}