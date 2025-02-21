package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.dto.response.EventDto;

import java.util.List;

public interface EventService {
    GenericResponseV2<List<EventDto>> getAllEvents();

    GenericResponseV2<EventDto> createEvent(EventDto eventDto);

    GenericResponseV2<EventDto> getEventById(Long eventId);

    GenericResponseV2<Boolean> deleteEventById(Long eventId);

    GenericResponseV2<Boolean> updateEventById(EventDto eventDto, Long eventId);

    GenericResponseV2<List<EventDto>> getEventByLocationId(Long locationId);
}
