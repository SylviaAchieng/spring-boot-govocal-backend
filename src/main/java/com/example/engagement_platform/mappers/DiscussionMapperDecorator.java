package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Discussion;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.response.DiscussionDto;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class DiscussionMapperDecorator implements DiscussionMapper{
    @Autowired
    @Qualifier("delegate")
    private  DiscussionMapper discussionMapper;

    @Autowired
    private UserRepository userRepository;
    @Override
    public DiscussionDto toDto(Discussion discussion) {
        DiscussionDto mappedDto = discussionMapper.toDto(discussion);
        User user = discussion.getUser();
        if (user != null){
            mappedDto.setUserId(user.getUserId());
        }
        return mappedDto;
    }

    @Override
    public Discussion toEntity(DiscussionDto discussionDto) {
        Discussion mappedEntity = discussionMapper.toEntity(discussionDto);
        Long userId = discussionDto.getUserId();
        User user = userRepository.findByUserId(userId)
                .orElse(User.builder()
                        .userId(userId)
                        .build());
        mappedEntity.setUser(user);
        return mappedEntity;
    }
}
