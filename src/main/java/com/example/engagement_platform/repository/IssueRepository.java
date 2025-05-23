package com.example.engagement_platform.repository;

import com.example.engagement_platform.enums.IssueStatusEnum;
import com.example.engagement_platform.model.Issue;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IssueRepository extends JpaRepository<Issue, Long> {
    Optional<Issue> findByIssueId(Long issueId);

    Optional<Issue> findByLocation(Location location);

    List<Issue> findAllByLocation(Location location);

    List<Issue> findAllByStatus(IssueStatusEnum status);

    List<Issue> findAllByStatusAndUser(IssueStatusEnum status, User user);

    List<Issue> findAllByUser(User user);
}
