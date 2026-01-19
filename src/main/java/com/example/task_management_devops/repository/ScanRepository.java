package com.example.task_management_devops.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.task_management_devops.model.Scan;

/**
 * Repository interface for Scan entity.
 * Provides CRUD operations and custom query methods.
 */
@Repository
public interface ScanRepository extends JpaRepository<Scan, Long> {
    
    /**
     * Find all scans for a specific project ordered by scan date descending.
     * 
     * @param projectName the name of the project
     * @return list of scans for the project
     */
    List<Scan> findByProjectNameOrderByScanDateDesc(String projectName);
    
    /**
     * Find the top 10 most recent scans.
     * 
     * @return list of the 10 most recent scans
     */
    List<Scan> findTop10ByOrderByScanDateDesc();
}