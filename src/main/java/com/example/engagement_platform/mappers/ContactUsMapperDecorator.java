package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.ContactUs;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.ContactUsDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class ContactUsMapperDecorator implements ContactUsMapper{
    @Autowired
    @Qualifier("delegate")
    private ContactUsMapper contactUsMapper;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Override
    public ContactUsDto toDto(ContactUs contactUs) {
        ContactUsDto mappedDto = contactUsMapper.toDto(contactUs);
        // custom mapping for location
        Location location = contactUs.getLocation();
        if (location != null){
            mappedDto.setLocation(LocationDto.builder()
                    .locationId(location.getLocationId())
                    .address(location.getAddress())
                    .county(location.getCounty())
                    .subCounty(location.getSubCounty())
                    .build());
        }
        return mappedDto;
    }

    @Override
    public ContactUs toEntity(ContactUsDto contactUsDto) {
        ContactUs mappedEntity = contactUsMapper.toEntity(contactUsDto);

        LocationDto locationDto = contactUsDto.getLocation();
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
