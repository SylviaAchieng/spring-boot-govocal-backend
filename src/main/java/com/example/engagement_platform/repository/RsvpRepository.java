package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.RSVP;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RsvpRepository extends JpaRepository<RSVP, Long> {
}
