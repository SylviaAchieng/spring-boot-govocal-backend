package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.request.RsvpRequest;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import com.example.engagement_platform.service.RsvpService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/rsvp")
public class RsvpController {

    private final RsvpService rsvpService;

    @PostMapping
    public ResponseEntity<GenericResponseV2<RsvpDto>> add(@RequestBody RsvpRequest rsvpRequest){
        GenericResponseV2<RsvpDto> response = rsvpService.add(rsvpRequest);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<GenericResponseV2<List<RsvpDto>>> getAllRsvp(){
        GenericResponseV2<List<RsvpDto>> response = rsvpService.getAllRsvp();
        return ResponseEntity.ok().body(response);
    }
}
