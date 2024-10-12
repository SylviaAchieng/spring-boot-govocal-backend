package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.*;
import com.example.engagement_platform.model.dto.response.CommentDto;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.repository.DiscussionRepository;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class EventMapperDecorator implements EventMapper{
    @Autowired
    @Qualifier("delegate")
    private  EventMapper eventMapper;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public EventDto toDto(Event event) {
        EventDto mappedDto = eventMapper.toDto(event);

        // custom mapping for user
        User user = event.getUser();
        if (user != null){
            mappedDto.setUserId(user.getUserId());
        }
        // custom mapping for location
        Location location = event.getLocation();
        if (location != null){
            mappedDto.setLocationId(location.getLocationId());
        }

        return mappedDto;
    }

    @Override
    public Event toEntity(EventDto eventDto) {
        Event mappedEntity = eventMapper.toEntity(eventDto);

        Long userId = eventDto.getUserId();
        User user = userRepository.findByUserId(userId)
                .orElse(User.builder()
                        .userId(userId)
                        .build());
        mappedEntity.setUser(user);

        Long locationId = eventDto.getLocationId();
        Location location = locationRepository.findByLocationId(locationId)
                .orElse(Location.builder()
                        .locationId(locationId)
                        .build());
        mappedEntity.setLocation(location);

        return mappedEntity;
    }
}
