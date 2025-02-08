package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.UserDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper(componentModel = "spring")
@DecoratedWith(value = UserMapperDecorator.class)
public interface UserMapper {

    UserMapper INSTANCE= Mappers.getMapper(UserMapper.class);

    @Mapping(source = "location", target = "location", ignore = true)
    UserDto toDto(User users);

    @Mapping(source = "location", target = "location", ignore = true)
    User toEntity(UserDto userDto);
}
