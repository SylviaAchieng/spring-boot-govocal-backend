package com.example.engagement_platform.repository;

import com.example.engagement_platform.enums.CategoriesEnum;
import com.example.engagement_platform.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiscussionRepository extends JpaRepository<Discussion, Long> {
    Optional<Discussion> findByDiscussionId(Long discussionId);

    List<Discussion> findAllByCategory(CategoriesEnum category);

}
