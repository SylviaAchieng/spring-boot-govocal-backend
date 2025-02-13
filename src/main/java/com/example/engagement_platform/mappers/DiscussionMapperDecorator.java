package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.time.LocalDate;

public class DiscussionMapperDecorator implements DiscussionMapper{
    @Autowired
    @Qualifier("delegate")
    private  DiscussionMapper discussionMapper;

    @Autowired
    private UserRepository userRepository;
    @Override
    public DiscussionDto toDto(Discussion discussion) {
        DiscussionDto mappedDto = discussionMapper.toDto(discussion);
        mappedDto.setReplyCount(discussion.getComments().size());
        mappedDto.setViewCount(discussion.getViewCount());
        mappedDto.setCreatedAt(LocalDate.now());
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
        mappedEntity.setCreatedAt(LocalDate.now());
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
