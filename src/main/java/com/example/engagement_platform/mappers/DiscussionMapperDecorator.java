package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class DiscussionMapperDecorator implements DiscussionMapper{
    @Autowired
    @Qualifier("delegate")
    private  DiscussionMapper discussionMapper;

    @Autowired
    private UserRepository userRepository;
    @Override
    public DiscussionDto toDto(Discussion discussion) {
        DiscussionDto mappedDto = discussionMapper.toDto(discussion);
        //mappedDto.setReplyCount(discussion.getComments().size());
        mappedDto.setReplyCount(discussion.getComments() != null ? discussion.getComments().size() : 0);
        mappedDto.setViewCount(discussion.getViewCount());
        mappedDto.setCreatedAt(LocalDate.from(discussion.getCreatedAt()));
        // custom mapping for user
        User user = discussion.getUser();
        if (user != null){
            mappedDto.setUser(UserDto.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .build());
        }
        return mappedDto;
    }

    @Override
    public Discussion toEntity(DiscussionDto discussionDto) {
        Discussion mappedEntity = discussionMapper.toEntity(discussionDto);
        mappedEntity.setReplyCount(0);
        mappedEntity.setViewCount(0);
        // Set createdAt only if it's a new discussion
        if (mappedEntity.getCreatedAt() == null) {
            mappedEntity.setCreatedAt(LocalDateTime.now());
        }
        UserDto userDto = discussionDto.getUser();
        if (userDto!=null){
            Long userId = userDto.getUserId();
            User user = userRepository.findByUserId(userId)
                    .orElse(User.builder()
                            .userId(userId)
                            .build());
            mappedEntity.setUser(user);
        }
        return mappedEntity;
    }
}
