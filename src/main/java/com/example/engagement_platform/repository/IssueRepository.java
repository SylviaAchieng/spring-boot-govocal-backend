package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByIssueId(Long issueId);

    Optional<Issue> findByLocation(Location location);
}
