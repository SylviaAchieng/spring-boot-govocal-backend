package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Comment;

import java.util.List;

public interface CommentService {
    List<Comment> getAllComments();

    Comment createComment(Comment comments);

    Comment getCommentById(Long commentId);

    void deleteCommentById(Long commentId);

    void updateCommentById(Long commentId, Comment comments);
}
