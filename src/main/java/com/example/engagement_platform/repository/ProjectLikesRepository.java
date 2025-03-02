package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Project;
import com.example.engagement_platform.model.ProjectLikes;
import com.example.engagement_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ProjectLikesRepository extends JpaRepository<ProjectLikes, BigDecimal> {

    List<ProjectLikes> findAllByProject(Project project);

    boolean existsByUserAndProject(User user, Project project);
}
