package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.request.RsvpRequest;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import com.example.engagement_platform.service.RsvpService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    @Operation(summary = "create rsvp")
    @PostMapping
    public ResponseEntity<GenericResponseV2<RsvpDto>> add(@RequestBody RsvpRequest rsvpRequest){
        GenericResponseV2<RsvpDto> response = rsvpService.add(rsvpRequest);
        return ResponseEntity.ok(response);
    }
    @Operation(summary = "get all rsvp")
    @GetMapping("/{eventId}")
    public ResponseEntity<GenericResponseV2<List<RsvpDto>>> getAllRsvpByEventId(@PathVariable Long eventId){
        GenericResponseV2<List<RsvpDto>> response = rsvpService.getAllRsvpByEventId(eventId);
        return ResponseEntity.ok().body(response);
    }
}
