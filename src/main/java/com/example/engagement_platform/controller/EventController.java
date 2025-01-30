package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.service.EventService;
import io.swagger.v3.oas.annotations.Operation;
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
    @Operation(summary = "get all events")
    @GetMapping
    public ResponseEntity<GenericResponseV2<List<EventDto>>> getAllEvents(){
        GenericResponseV2<List<EventDto>> response = eventService.getAllEvents();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
    @Operation(summary = "create event")
    @PostMapping
    public ResponseEntity<GenericResponseV2<EventDto>> createEvent(@Valid @RequestBody EventDto eventDto){
        GenericResponseV2<EventDto> responseV2 = eventService.createEvent(eventDto);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.ok().body(responseV2);
        }

    }
    @Operation(summary = "get event by id")
    @GetMapping("/{eventId}")
    public ResponseEntity<GenericResponseV2<EventDto>> getEventById(@PathVariable Long eventId){
        GenericResponseV2<EventDto> responseV2 = eventService.getEventById(eventId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }

    }
    @Operation(summary = "delete event by id")
    @DeleteMapping("/{eventId}")
    public ResponseEntity<GenericResponseV2<Boolean>> deleteEventById(@PathVariable Long eventId){
        GenericResponseV2<Boolean> responseV2 = eventService.deleteEventById(eventId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }
    @Operation(summary = "update event by id")
    @PutMapping("/{eventId}")
    public ResponseEntity<GenericResponseV2<Boolean>> updateEventById(@Valid @RequestBody EventDto eventDto, @PathVariable Long eventId){
        GenericResponseV2<Boolean> response = eventService.updateEventById(eventDto,eventId);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

}
