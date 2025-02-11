package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.*;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.Base64;

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
        mappedDto.setTime(event.getTime());
        // custom mapping for user
        User user = event.getUser();
        if (user != null){
            mappedDto.setUser(UserDto.builder()
                    .userId(user.getUserId())
                    .fullName(user.getFullName())
                    .email(user.getEmail())
                    .userType(user.getUserType())
                    .build());
        }
        // custom mapping for location
        Location location = event.getLocation();
        if (location != null){
            mappedDto.setLocation(LocationDto.builder()
                    .locationId(location.getLocationId())
                    .address(location.getAddress())
                    .county(location.getCounty())
                    .subCounty(location.getSubCounty())
                    .build());
        }
        byte[] image = event.getImage();
        if (image!=null && (image.length>0)){
            mappedDto.setBase64EncodedImage(Base64.getEncoder().encodeToString(image));
        }

        return mappedDto;
    }

    @Override
    public Event toEntity(EventDto eventDto) {
        Event mappedEntity = eventMapper.toEntity(eventDto);
        mappedEntity.setTime(eventDto.getTime());

        UserDto userDto = eventDto.getUser();
        if (userDto!=null){
            Long userId = userDto.getUserId();
            User user = userRepository.findByUserId(userId)
                    .orElse(User.builder()
                            .userId(userId)
                            .build());
            mappedEntity.setUser(user);
        }

        LocationDto locationDto = eventDto.getLocation();
        if (locationDto!=null){
            Long locationId = locationDto.getLocationId();
            Location location = locationRepository.findByLocationId(locationId)
                    .orElse(Location.builder()
                            .locationId(locationId)
                            .build());
            mappedEntity.setLocation(location);
        }

        String base64EncodedImage = eventDto.getBase64EncodedImage();
        if (base64EncodedImage!=null && !base64EncodedImage.isEmpty()){
            mappedEntity.setImage(Base64.getDecoder().decode(base64EncodedImage));
        }

        return mappedEntity;
    }
}
