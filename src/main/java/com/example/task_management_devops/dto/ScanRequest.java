package com.example.task_management_devops.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for triggering a new vulnerability scan.
 */
public class ScanRequest {
    
    @NotBlank(message = "Project name is required")
    private String projectName;
    
    @NotBlank(message = "Project version is required")
    private String projectVersion;
    
    public ScanRequest() {
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
}