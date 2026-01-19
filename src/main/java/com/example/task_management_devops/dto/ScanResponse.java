package com.example.task_management_devops.dto;


import java.time.LocalDateTime;

import com.example.task_management_devops.model.Scan;

/**
 * DTO for scan response with calculated risk level.
 */
public class ScanResponse {
    
    private Long scanId;
    private String projectName;
    private String projectVersion;
    private String status;
    private LocalDateTime scanDate;
    private Integer totalDependencies;
    private Integer vulnerableDependencies;
    private Integer criticalCount;
    private Integer highCount;
    private Integer mediumCount;
    private Integer lowCount;
    private Long scanDurationMs;
    private String riskLevel;
    
    public ScanResponse(Scan scan) {
        this.scanId = scan.getId();
        this.projectName = scan.getProjectName();
        this.projectVersion = scan.getProjectVersion();
        this.status = scan.getStatus().toString();
        this.scanDate = scan.getScanDate();
        this.totalDependencies = scan.getTotalDependencies();
        this.vulnerableDependencies = scan.getVulnerableDependencies();
        this.criticalCount = scan.getCriticalCount();
        this.highCount = scan.getHighCount();
        this.mediumCount = scan.getMediumCount();
        this.lowCount = scan.getLowCount();
        this.scanDurationMs = scan.getScanDurationMs();
        this.riskLevel = calculateRiskLevel(scan);
    }
    
    private String calculateRiskLevel(Scan scan) {
        if (scan.getCriticalCount() > 0) {
            return "CRITICAL";
        }
        if (scan.getHighCount() > 0) {
            return "HIGH";
        }
        if (scan.getMediumCount() > 0) {
            return "MEDIUM";
        }
        if (scan.getLowCount() > 0) {
            return "LOW";
        }
        return "SAFE";
    }
    
    // Getters
    public Long getScanId() {
        return scanId;
    }
    
    public String getProjectName() {
        return projectName;
    }
    
    public String getProjectVersion() {
        return projectVersion;
    }
    
    public String getStatus() {
        return status;
    }
    
    public LocalDateTime getScanDate() {
        return scanDate;
    }
    
    public Integer getTotalDependencies() {
        return totalDependencies;
    }
    
    public Integer getVulnerableDependencies() {
        return vulnerableDependencies;
    }
    
    public Integer getCriticalCount() {
        return criticalCount;
    }
    
    public Integer getHighCount() {
        return highCount;
    }
    
    public Integer getMediumCount() {
        return mediumCount;
    }
    
    public Integer getLowCount() {
        return lowCount;
    }
    
    public Long getScanDurationMs() {
        return scanDurationMs;
    }
    
    public String getRiskLevel() {
        return riskLevel;
    }
}

