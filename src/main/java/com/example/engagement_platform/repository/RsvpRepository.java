package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.RSVP;
import com.example.engagement_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RsvpRepository extends JpaRepository<RSVP, Long> {
    boolean existsByUserAndEvent(User user, Event event);

    List<RSVP> findAllByEvent(Event event);
}
