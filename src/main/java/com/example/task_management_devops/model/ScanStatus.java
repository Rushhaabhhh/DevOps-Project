package com.example.task_management_devops.model;

/**
 * Enum representing the status of a vulnerability scan.
 */
public enum ScanStatus {
    /**
     * Scan is created but not yet started
     */
    PENDING,
    
    /**
     * Scan is currently in progress
     */
    SCANNING,
    
    /**
     * Scan completed successfully
     */
    COMPLETED,
    
    /**
     * Scan failed due to an error
     */
    FAILED
}