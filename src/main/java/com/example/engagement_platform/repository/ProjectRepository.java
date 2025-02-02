package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Projects;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Projects, BigDecimal> {
    Optional<Projects> findByProjectId(BigDecimal projectId);
}
