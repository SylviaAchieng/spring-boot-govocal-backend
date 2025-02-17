package com.example.engagement_platform.mappers;

import com.example.engagement_platform.model.RSVP;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class RsvpMapperDecorator implements RsvpMapper{

    @Autowired
    @Qualifier("delegate")
    private RsvpMapper rsvpMapper;
    @Override
    public RsvpDto RSVPToRsvpDto(RSVP rsvp) {
        RsvpDto rsvpDto = rsvpMapper.RSVPToRsvpDto(rsvp);
        rsvpDto.setId(rsvp.getRsvpID());
        return rsvpDto;
    }

    @Override
    public RSVP RsvpDtoToRSVP(RsvpDto rsvpDto) {
        RSVP rsvp =rsvpMapper.RsvpDtoToRSVP(rsvpDto);
        return rsvp;
    }
}
