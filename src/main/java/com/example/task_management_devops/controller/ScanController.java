package com.example.task_management_devops.controller;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.task_management_devops.dto.HealthResponse;
import com.example.task_management_devops.dto.ScanRequest;
import com.example.task_management_devops.dto.ScanResponse;
import com.example.task_management_devops.model.Scan;
import com.example.task_management_devops.model.Vulnerability;
import com.example.task_management_devops.service.ScannerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

/**
 * REST Controller for vulnerability scanning operations.
 * Provides endpoints for triggering scans, retrieving results, and managing scan history.
 */
@RestController
@RequestMapping("/api/scans")
@Tag(name = "Vulnerability Scanner", description = "API for dependency vulnerability scanning")
public class ScanController {
    
    private final ScannerService scannerService;
    
    public ScanController(ScannerService scannerService) {
        this.scannerService = scannerService;
    }
    
    /**
     * Trigger a new vulnerability scan for a project.
     */
    @PostMapping("/trigger")
    @Operation(summary = "Trigger a new vulnerability scan", 
               description = "Creates and executes a vulnerability scan for the specified project")
    public ResponseEntity<ScanResponse> triggerScan(@Valid @RequestBody ScanRequest request) {
        // Create scan
        Scan scan = scannerService.createScan(
            request.getProjectName(), 
            request.getProjectVersion()
        );
        
        // Execute scan
        scan = scannerService.executeScan(scan.getId());
        
        // Create response
        ScanResponse response = new ScanResponse(scan);
        
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
    
    /**
     * Get all recent scans (last 10).
     */
    @GetMapping
    @Operation(summary = "Get all scans", 
               description = "Retrieve the 10 most recent vulnerability scans")
    public ResponseEntity<List<ScanResponse>> getAllScans() {
        List<Scan> scans = scannerService.getAllScans();
        List<ScanResponse> responses = scans.stream()
            .map(ScanResponse::new)
            .toList();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Get a specific scan by ID.
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get scan by ID", 
               description = "Retrieve detailed information about a specific scan")
    public ResponseEntity<ScanResponse> getScanById(@PathVariable Long id) {
        return scannerService.getScanById(id)
            .map(scan -> ResponseEntity.ok(new ScanResponse(scan)))
            .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Get all vulnerabilities found in a scan.
     */
    @GetMapping("/{id}/vulnerabilities")
    @Operation(summary = "Get vulnerabilities for a scan", 
               description = "Retrieve all vulnerabilities (CVEs) found in a specific scan")
    public ResponseEntity<List<Vulnerability>> getVulnerabilities(@PathVariable Long id) {
        List<Vulnerability> vulnerabilities = scannerService.getVulnerabilitiesByScanId(id);
        return ResponseEntity.ok(vulnerabilities);
    }
    
    /**
     * Get scan history for a specific project.
     */
    @GetMapping("/project/{projectName}")
    @Operation(summary = "Get scan history for a project", 
               description = "Retrieve all scans for a specific project ordered by date")
    public ResponseEntity<List<ScanResponse>> getProjectHistory(
            @PathVariable String projectName) {
        List<Scan> scans = scannerService.getProjectHistory(projectName);
        List<ScanResponse> responses = scans.stream()
            .map(ScanResponse::new)
            .toList();
        return ResponseEntity.ok(responses);
    }
    
    /**
     * Delete a scan by ID.
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a scan", 
               description = "Delete a scan and all its associated vulnerabilities")
    public ResponseEntity<Void> deleteScan(@PathVariable Long id) {
        boolean deleted = scannerService.deleteScan(id);
        return deleted ? ResponseEntity.noContent().build() 
                      : ResponseEntity.notFound().build();
    }
    
    /**
     * Check scanner health status.
     */
    @GetMapping("/health")
    @Operation(summary = "Check scanner health", 
               description = "Verify that the vulnerability scanner is running and operational")
    public ResponseEntity<HealthResponse> getHealth() {
        HealthResponse health = new HealthResponse(
            "UP",
            "Vulnerability Scanner is running",
            LocalDateTime.now()
        );
        return ResponseEntity.ok(health);
    }
}