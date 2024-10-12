package com.example.engagement_platform.service;

import com.example.engagement_platform.model.Event;
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
    public List<Event> getAllEvents() {
        return eventRepository.findAll();
    }

    @Override
    public Event createEvent(Event events) {
        return eventRepository.save(events);
    }

    @Override
    public Event getEventById(Long eventId) {
        Optional<Event> eventFromDb = eventRepository.findById(eventId);
        if (eventFromDb.isPresent()){
            return eventFromDb.get();
        }else {
          throw new RuntimeException("Event not Found");
        }
    }

    @Override
    public void deleteEventById(Long eventId) {
        Optional<Event> eventFrDb = eventRepository.findById(eventId);
        if (eventFrDb.isPresent()){
            eventRepository.deleteById(eventId);
        }else{
            throw new RuntimeException("Event not found");
        }
    }

    @Override
    public void updateEventById(Event events, Long eventId) {
        Optional<Event> eventFrDatabase = eventRepository.findById(eventId);
        if (eventFrDatabase.isPresent()){
            eventRepository.save(events);
        }else {
            throw new RuntimeException("Event not found");
        }
    }

}
