package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.LocationDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = LocationMapperDecorator.class)
public interface LocationMapper {

    LocationMapper INSTANCE = Mappers.getMapper(LocationMapper.class);
    LocationDto locationToLocationDto(Location location);
    Location locationDtoToLocation(LocationDto locationDto);
}
