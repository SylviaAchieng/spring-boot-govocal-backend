package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Events;

import java.util.List;

public interface EventService {
    List<Events> getAllEvents();

    Events createEvent(Events events);

    Events getEventById(Long eventId);

    void deleteEventById(Long eventId);

    void updateEventById(Events events, Long eventId);
}
