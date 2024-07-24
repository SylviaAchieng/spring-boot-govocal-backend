package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Discussions;
import com.example.engagement_platform.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussions, Long> {
}
