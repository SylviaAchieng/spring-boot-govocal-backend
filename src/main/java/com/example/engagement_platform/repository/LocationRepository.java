package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLocationId(Long locationId);
}
