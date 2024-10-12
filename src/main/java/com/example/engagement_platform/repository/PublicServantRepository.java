package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.PublicServant;
import com.example.engagement_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PublicServantRepository extends JpaRepository<PublicServant, Long> {
    Optional<PublicServant> findByUser(User user);
}
