package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.LocationDto;
import org.springframework.beans.factory.annotation.Autowired;

public class LocationMapperDecorator implements LocationMapper{

    @Autowired
    private LocationMapper locationMapper;

    @Override
    public LocationDto locationToLocationDto(Location location) {
        LocationDto locationDto = locationMapper.locationToLocationDto(location);
        return locationDto;
    }

    @Override
    public Location locationDtoToLocation(LocationDto locationDto) {
        Location location = locationMapper.locationDtoToLocation(locationDto);
        return location;
    }
}
