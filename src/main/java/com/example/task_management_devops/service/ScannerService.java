package com.example.task_management_devops.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.task_management_devops.model.Scan;
import com.example.task_management_devops.model.ScanStatus;
import com.example.task_management_devops.model.Vulnerability;
import com.example.task_management_devops.model.VulnerabilitySeverity;
import com.example.task_management_devops.repository.ScanRepository;
import com.example.task_management_devops.repository.VulnerabilityRepository;

/**
 * Service class for vulnerability scanning operations.
 * Handles scan execution, vulnerability detection, and data management.
 */
@Service
public class ScannerService {
    
    private final ScanRepository scanRepository;
    private final VulnerabilityRepository vulnerabilityRepository;
    
    public ScannerService(ScanRepository scanRepository, 
                         VulnerabilityRepository vulnerabilityRepository) {
        this.scanRepository = scanRepository;
        this.vulnerabilityRepository = vulnerabilityRepository;
    }
    
    /**
     * Create a new scan.
     */
    public Scan createScan(String projectName, String projectVersion) {
        Scan scan = new Scan(projectName, projectVersion);
        return scanRepository.save(scan);
    }
    
    /**
     * Execute vulnerability scan (simulated with realistic data).
     */
    public Scan executeScan(Long scanId) {
        Optional<Scan> optionalScan = scanRepository.findById(scanId);
        if (optionalScan.isEmpty()) {
            throw new RuntimeException("Scan not found with id: " + scanId);
        }
        
        Scan scan = optionalScan.get();
        scan.setStatus(ScanStatus.SCANNING);
        scanRepository.save(scan);
        
        long startTime = System.currentTimeMillis();
        
        // Simulate scanning with realistic vulnerability data
        simulateScan(scan);
        
        long endTime = System.currentTimeMillis();
        scan.setScanDurationMs(endTime - startTime);
        scan.setStatus(ScanStatus.COMPLETED);
        
        return scanRepository.save(scan);
    }
    
    /**
     * Simulate a realistic vulnerability scan with known CVEs.
     */
    private void simulateScan(Scan scan) {
        List<Vulnerability> vulnerabilities = new ArrayList<>();
        
        // Example 1: Log4j vulnerability (Critical - Log4Shell)
        Vulnerability log4j = new Vulnerability(
            "CVE-2021-44228",
            "org.apache.logging.log4j:log4j-core",
            "2.14.1",
            VulnerabilitySeverity.CRITICAL,
            10.0
        );
        log4j.setSafeVersion("2.17.1");
        log4j.setDescription("Apache Log4j2 JNDI features do not protect against " +
            "attacker-controlled LDAP and other JNDI related endpoints. " +
            "This allows remote code execution.");
        log4j.setRemediationAdvice("Update to version 2.17.1 or later immediately");
        log4j.setScan(scan);
        vulnerabilities.add(log4j);
        
        // Example 2: Spring Framework vulnerability (High - Spring4Shell)
        Vulnerability spring = new Vulnerability(
            "CVE-2022-22965",
            "org.springframework:spring-core",
            "5.3.17",
            VulnerabilitySeverity.HIGH,
            9.8
        );
        spring.setSafeVersion("5.3.18");
        spring.setDescription("Spring Framework RCE via Data Binding on JDK 9+");
        spring.setRemediationAdvice("Upgrade to Spring Framework 5.3.18 or later");
        spring.setScan(scan);
        vulnerabilities.add(spring);
        
        // Example 3: Jackson vulnerability (Medium)
        Vulnerability jackson = new Vulnerability(
            "CVE-2020-36518",
            "com.fasterxml.jackson.core:jackson-databind",
            "2.12.3",
            VulnerabilitySeverity.MEDIUM,
            7.5
        );
        jackson.setSafeVersion("2.12.6.1");
        jackson.setDescription("Java StackOverflow exception and denial of service " +
            "via a large depth of nested objects");
        jackson.setRemediationAdvice("Update to version 2.12.6.1 or later");
        jackson.setScan(scan);
        vulnerabilities.add(jackson);
        
        // Example 4: Commons Text vulnerability (Low)
        Vulnerability commonsText = new Vulnerability(
            "CVE-2022-42889",
            "org.apache.commons:commons-text",
            "1.9",
            VulnerabilitySeverity.LOW,
            4.3
        );
        commonsText.setSafeVersion("1.10.0");
        commonsText.setDescription("Apache Commons Text performs variable " +
            "interpolation, allowing properties to be dynamically evaluated and expanded");
        commonsText.setRemediationAdvice("Upgrade to version 1.10.0 or later");
        commonsText.setScan(scan);
        vulnerabilities.add(commonsText);
        
        // Save all vulnerabilities
        vulnerabilityRepository.saveAll(vulnerabilities);
        
        // Update scan statistics
        scan.setTotalDependencies(25); // Simulated total
        scan.setVulnerableDependencies(vulnerabilities.size());
        scan.setCriticalCount((int) vulnerabilities.stream()
            .filter(v -> v.getSeverity() == VulnerabilitySeverity.CRITICAL).count());
        scan.setHighCount((int) vulnerabilities.stream()
            .filter(v -> v.getSeverity() == VulnerabilitySeverity.HIGH).count());
        scan.setMediumCount((int) vulnerabilities.stream()
            .filter(v -> v.getSeverity() == VulnerabilitySeverity.MEDIUM).count());
        scan.setLowCount((int) vulnerabilities.stream()
            .filter(v -> v.getSeverity() == VulnerabilitySeverity.LOW).count());
    }
    
    /**
     * Get all scans (limited to 10 most recent).
     */
    public List<Scan> getAllScans() {
        return scanRepository.findTop10ByOrderByScanDateDesc();
    }
    
    /**
     * Get scan by ID.
     */
    public Optional<Scan> getScanById(Long id) {
        return scanRepository.findById(id);
    }
    
    /**
     * Get vulnerabilities for a scan.
     */
    public List<Vulnerability> getVulnerabilitiesByScanId(Long scanId) {
        return vulnerabilityRepository.findByScanId(scanId);
    }
    
    /**
     * Get project scan history.
     */
    public List<Scan> getProjectHistory(String projectName) {
        return scanRepository.findByProjectNameOrderByScanDateDesc(projectName);
    }
    
    /**
     * Delete scan.
     */
    public boolean deleteScan(Long id) {
        if (scanRepository.existsById(id)) {
            scanRepository.deleteById(id);
            return true;
        }
        return false;
    }
}