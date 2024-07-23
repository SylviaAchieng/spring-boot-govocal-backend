package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Events;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EventRepository extends JpaRepository<Events, Long> {
}
