package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.RSVP;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
@DecoratedWith(value = RsvpMapperDecorator.class)
public interface RsvpMapper {
    RsvpMapper INSTANCE = Mappers.getMapper(RsvpMapper.class);
    RsvpDto RSVPToRsvpDto(RSVP rsvp);
    RSVP RsvpDtoToRSVP(RsvpDto rsvpDto);

}
