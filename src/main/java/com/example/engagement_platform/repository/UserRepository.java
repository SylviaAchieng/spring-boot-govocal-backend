package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUserId(Long userId);
    Optional<User> findByEmail(String email);

    Optional<User> findByFullName(String fullName);

    Optional<User> findByFullNameAndEmail(String fullName, String email);

    List<User> findAllByLocation(Location location);
}
