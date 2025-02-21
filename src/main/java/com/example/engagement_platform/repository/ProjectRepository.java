package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProjectRepository extends JpaRepository<Project, BigDecimal> {
    Optional<Project> findByProjectId(BigDecimal projectId);

    List<Project> findByDaysRemaining(BigDecimal zero);

    List<Project> findAllByLocation(Location location);
}
