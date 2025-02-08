package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.request.PublicServantDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.PublicServantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;


public class UserMapperDecorator implements UserMapper{

    @Autowired
    @Qualifier("delegate")
    private  UserMapper userMapper;

    @Autowired
    private PublicServantRepository publicServantRepository;

    @Autowired
    private LocationRepository locationRepository;

    @Override
    public UserDto toDto(User user) {
        UserDto mappedDto = userMapper.toDto(user);

        // custom mapping for location
        Location location = user.getLocation();
        if (location != null){
            mappedDto.setLocation(LocationDto.builder()
                            .locationId(location.getLocationId())
                            .address(location.getAddress())
                            .county(location.getCounty())
                            .subCounty(location.getSubCounty())
                    .build());
        }
        // custom mapping for public servant
        UserType userType = user.getUserType();
        if (userType.equals(UserType.PUBLIC_SERVANT)) {
            //get public details and map it
            publicServantRepository.findByUser(User.builder().userId(user.getUserId()).build())
                    .ifPresent(publicServant -> {
                       mappedDto.setPublicServant(PublicServantDto.builder()
                                       .department(publicServant.getDepartment())
                                       .position(publicServant.getPosition())
                               .build());
                    });
        }

        return mappedDto;
    }

    @Override
    public User toEntity(UserDto userDto) {
        User mappedEntity = userMapper.toEntity(userDto);


        LocationDto locationDto = userDto.getLocation();
        if (locationDto!=null){
            Long locationId = locationDto.getLocationId();
            Location location = locationRepository.findByLocationId(locationId)
                    .orElse(Location.builder()
                            .locationId(locationId)
                            .build());
            mappedEntity.setLocation(location);
        }
        return mappedEntity;
    }
}
