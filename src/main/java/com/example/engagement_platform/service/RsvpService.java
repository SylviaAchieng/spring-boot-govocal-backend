package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.request.RsvpRequest;
import com.example.engagement_platform.model.dto.response.RsvpDto;

import java.util.List;

public interface RsvpService {
    GenericResponseV2<RsvpDto> add(RsvpRequest rsvpRequest);


    GenericResponseV2<List<RsvpDto>> getAllRsvpByEventId(Long eventId);
}
