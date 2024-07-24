package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Events;
import com.example.engagement_platform.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService{

    @Autowired
    private EventRepository eventRepository;

    @Override
    public List<Events> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Events createEvent(Events events) {
        return eventRepository.save(events);
    }

    @Override
    public Events getEventById(Long eventId) {
        Optional<Events> eventFromDb = eventRepository.findById(eventId);
        if (eventFromDb.isPresent()){
            return eventFromDb.get();
        }else {
          throw new RuntimeException("Event not Found");
        }
    }

    @Override
    public void deleteEventById(Long eventId) {
        Optional<Events> eventFrDb = eventRepository.findById(eventId);
        if (eventFrDb.isPresent()){
            eventRepository.deleteById(eventId);
        }else{
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public void updateEventById(Events events, Long eventId) {
        Optional<Events> eventFrDatabase = eventRepository.findById(eventId);
        if (eventFrDatabase.isPresent()){
            eventRepository.save(events);
        }else {
            throw new RuntimeException("Event not found");
        }
    }

}
