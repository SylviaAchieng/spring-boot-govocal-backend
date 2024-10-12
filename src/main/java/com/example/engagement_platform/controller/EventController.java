package com.example.engagement_platform.controller;

import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.service.EventService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {

    private final EventService eventService;

    @GetMapping
    public ResponseEntity<List<Event>> getAllEvents(){
        try {
            List<Event> events = eventService.getAllEvents();
            return new ResponseEntity<>(events, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping
    public ResponseEntity<Event> createEvent(@Valid @RequestBody Event events){
        try {
            Event createdEvent = eventService.createEvent(events);
            return new ResponseEntity<>(createdEvent, HttpStatus.CREATED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{eventId}")
    public ResponseEntity<Event> getEventById(@PathVariable Long eventId){
        try {
            Event eventById = eventService.getEventById(eventId);
            return new ResponseEntity<>(eventById, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{eventId}")
    public ResponseEntity<Boolean> deleteEventById(@PathVariable Long eventId){
        try {
            eventService.deleteEventById(eventId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/{eventId}")
    public ResponseEntity<Boolean> updateEventById(@Valid @RequestBody Event events, @PathVariable Long eventId){
        try {
            eventService.updateEventById(events,eventId);
            return new ResponseEntity<>(true, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(false, HttpStatus.BAD_REQUEST);
        }
    }
}
