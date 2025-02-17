package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.DiscussionRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

public class CommentMapperDecorator implements CommentMapper{
    @Autowired
    @Qualifier("delegate")
    private  CommentMapper commentMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DiscussionRepository discussionRepository;

    @Override
    public CommentDto toDto(Comment comment) {
        CommentDto mappedDto = commentMapper.toDto(comment);
        mappedDto.setCreatedAt(LocalDate.now());
        // custom mapping for user
        User user = comment.getUser();
        if (user != null){
            mappedDto.setUser(UserDto.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .build());
        }
        // custom mapping for user
        Discussion discussion = comment.getDiscussion();
        if (discussion != null){
            mappedDto.setDiscussion(DiscussionDto.builder()
                    .discussionId(discussion.getDiscussionId())
                    .title(discussion.getTitle())
                    .description(discussion.getDescription())
                    .category(discussion.getCategory())
                    .createdAt(LocalDate.from(discussion.getCreatedAt()))
                    .replyCount(discussion.getReplyCount())
                    .viewCount(discussion.getViewCount())
                    .build());
        }
        return mappedDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        Comment mappedEntity = commentMapper.toEntity(commentDto);
        mappedEntity.setCreatedAt(LocalDate.now());
        UserDto userDto = commentDto.getUser();
        if (userDto!=null){
            Long userId = userDto.getUserId();
            User user = userRepository.findByUserId(userId)
                    .orElse(User.builder()
                            .userId(userId)
                            .build());
            mappedEntity.setUser(user);
        }

        DiscussionDto discussionDto = commentDto.getDiscussion();
        if (discussionDto!=null){
            Long discussionId = discussionDto.getDiscussionId();
            Discussion discussion = discussionRepository.findByDiscussionId(discussionId).orElseThrow(() -> new RuntimeException("Discussion not found"));
            mappedEntity.setDiscussion(discussion);
        }

        return mappedEntity;
    }
}
