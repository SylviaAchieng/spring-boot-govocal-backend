package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, BigDecimal> {
    Optional<Project> findByProjectId(BigDecimal projectId);
}
