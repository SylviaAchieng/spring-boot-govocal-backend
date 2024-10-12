package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/location")
public class LocationController {
    private final LocationService locationService;

    @PostMapping
    public ResponseEntity<GenericResponse> createLocation(@RequestBody Location location){
        GenericResponse response = locationService.createLocation(location);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponseV2<List<LocationDto>>> getAllLocations(){
        GenericResponseV2<List<LocationDto>> response = locationService.getAllLocations();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping("{locationId}")
    public ResponseEntity<GenericResponseV2<LocationDto>> getLocationById(@PathVariable Long locationId){
        GenericResponseV2<LocationDto> responseV2 = locationService.getLocationById(locationId);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.badRequest().body(responseV2);
        }
    }

    @PutMapping
    public ResponseEntity<GenericResponseV2<Boolean>> updateLocationById(
            @RequestParam(name = "locationId") Long locationId,
            @RequestBody Location location
    ){
        GenericResponseV2<Boolean> responseV2 = locationService.updateLocationById(locationId, location);
        if (responseV2.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(responseV2);
        }else {
            return ResponseEntity.ok().body(responseV2);
        }
    }
}
