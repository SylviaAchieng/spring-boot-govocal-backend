package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Event;

import java.util.List;

public interface EventService {
    List<Event> getAllEvents();

    Event createEvent(Event events);

    Event getEventById(Long eventId);

    void deleteEventById(Long eventId);

    void updateEventById(Event events, Long eventId);
}
