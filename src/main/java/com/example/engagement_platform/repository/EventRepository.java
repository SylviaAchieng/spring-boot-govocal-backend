package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {
    Optional<Event> findByEventId(Long eventId);

    List<Event> findAllByLocation(Location location);
}
