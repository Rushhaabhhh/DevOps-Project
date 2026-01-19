package com.example.task_management_devops.dto;


import java.time.LocalDateTime;

/**
 * DTO for health check response.
 */
public class HealthResponse {
    
    private String status;
    private String message;
    private LocalDateTime timestamp;
    
    public HealthResponse(String status, String message, LocalDateTime timestamp) {
        this.status = status;
        this.message = message;
        this.timestamp = timestamp;
    }
    
    // Getters
    public String getStatus() {
        return status;
    }
    
    public String getMessage() {
        return message;
    }
    
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}