package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Comment;
import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.repository.DiscussionRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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

        // custom mapping for user
        User user = comment.getUser();
        if (user != null){
            mappedDto.setUserId(user.getUserId());
        }
        // custom mapping for user
        Discussion discussion = comment.getDiscussion();
        if (discussion != null){
            mappedDto.setDiscussionId(discussion.getDiscussionId());
        }

        return mappedDto;
    }

    @Override
    public Comment toEntity(CommentDto commentDto) {
        Comment mappedEntity = commentMapper.toEntity(commentDto);

        Long userId = commentDto.getUserId();
        User user = userRepository.findByUserId(userId)
                .orElse(User.builder()
                        .userId(userId)
                        .build());
        mappedEntity.setUser(user);

        Long discussionId = commentDto.getDiscussionId();
        Discussion discussion = discussionRepository.findByDiscussionId(discussionId).orElseThrow();
        mappedEntity.setDiscussion(discussion);

        return mappedEntity;
    }
}
