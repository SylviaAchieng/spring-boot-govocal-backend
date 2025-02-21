package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.ProjectComments;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

public interface ProjectCommentRepository extends JpaRepository<ProjectComments, BigDecimal> {
    List<ProjectComments> findAllByProject(Project project);
}
