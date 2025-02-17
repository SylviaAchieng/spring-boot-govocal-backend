package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.CommentMapper;
import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{

    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;

    @Override
    public GenericResponseV2<List<CommentDto>> getAllComments() {
        try {
            List<CommentDto> response = commentRepository.findAll()
                    .stream()
                    .map(commentMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<CommentDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comments retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<CommentDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve comments")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<CommentDto> createComment(CommentDto commentDto) {
        try {
            Comment commentToBeSaved = commentMapper.toEntity(commentDto);
            Comment savedComment = commentRepository.save(commentToBeSaved);
            CommentDto response = commentMapper.toDto(savedComment);
            return GenericResponseV2.<CommentDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comment created successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<CommentDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create comment")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<CommentDto> getCommentById(Long commentId) {
        try {
            Comment commentFromDb = commentRepository.findByCommentId(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
            CommentDto response = commentMapper.toDto(commentFromDb);
            return GenericResponseV2.<CommentDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comment retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<CommentDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve comment")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> deleteCommentById(Long commentId) {
        try {
            Comment commentFromDb = commentRepository.findByCommentId(commentId).orElseThrow(() -> new RuntimeException("Comment not found"));
            commentRepository.delete(commentFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comment deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete comment")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateCommentById(Long commentId, CommentDto commentDto) {
        try {
            Comment commentToSave = commentMapper.toEntity(commentDto);
            Comment savedComment  = commentRepository.save(commentToSave);
            commentMapper.toDto(savedComment);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Comment saved successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to save comment")
                    ._embedded(null)
                    .build();
        }
        }

    @Override
    public GenericResponseV2<List<CommentDto>> getCommentsByDiscussionId(Long discussionId) {
        try {
            Discussion discussion = Discussion.builder()
                    .discussionId(discussionId)
                    .build();
            List<Comment> commentByDiscussionId = commentRepository.findAllByDiscussion(discussion);
            List<CommentDto> response = commentByDiscussionId.stream()
                    .map(commentMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<CommentDto>>builder()
                    .message("Comments retrieved successfully")
                    .status(ResponseStatusEnum.SUCCESS)
                    ._embedded(response)
                    .build();
        }catch (Exception e){
         e.printStackTrace();
         return GenericResponseV2.<List<CommentDto>>builder()
                 .status(ResponseStatusEnum.ERROR)
                 .message("Unable to retrieve comments")
                 .build();
        }
    }
}
