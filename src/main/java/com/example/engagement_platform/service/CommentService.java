package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.dto.response.CommentDto;

import java.util.List;

public interface CommentService {
    GenericResponseV2<List<CommentDto>> getAllComments();

    GenericResponseV2<CommentDto> createComment(CommentDto commentDto);

    GenericResponseV2<CommentDto> getCommentById(Long commentId);

    GenericResponseV2<Boolean> deleteCommentById(Long commentId);

    GenericResponseV2<Boolean> updateCommentById(Long commentId, CommentDto commentDto);

    GenericResponseV2<List<CommentDto>> getCommentsByDiscussionId(Long discussionId);

}
