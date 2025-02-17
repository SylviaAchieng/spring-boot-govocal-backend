package com.example.engagement_platform.repository;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Discussion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    Optional<Comment> findByCommentId(Long commentId);

    List<Comment> findAllByDiscussion(Discussion discussion);
}
