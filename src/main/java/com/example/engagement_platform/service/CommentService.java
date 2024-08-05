package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Comments;

import java.util.List;

public interface CommentService {
    List<Comments> getAllComments();

    Comments createComment(Comments comments);

    Comments getCommentById(Long commentId);

    void deleteCommentById(Long commentId);

    void updateCommentById(Long commentId, Comments comments);
}
