package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.LocationMapper;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {
    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;
    @Override
    public GenericResponse createLocation(Location location) {
        try {
            Location response = locationRepository.save(location);
            return GenericResponse.builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully created a location")
                    ._embedded(Collections.singletonList(response))
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponse.builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create location")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<List<LocationDto>> getAllLocations() {
        try {
            List<Location> locations = locationRepository.findAll();
            List<LocationDto> response =locations.stream().map(locationMapper::locationToLocationDto).collect(Collectors.toList());
            return GenericResponseV2.<List<LocationDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved locations")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<LocationDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve locations")
                    ._embedded(null)
                    .build();
        }

    }

    @Override
    public GenericResponseV2<LocationDto> getLocationById(Long locationId) {
        try {
            Optional<Location> locationFromDb = locationRepository.findById(locationId);
            LocationDto response = locationFromDb.stream()
                    .map(locationMapper::locationToLocationDto)
                    .filter(dto -> dto.getLocationId().equals(locationId))
                    .findFirst()
                    .orElse(null);
            return GenericResponseV2.<LocationDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Succesfully retrieved location")
                    ._embedded(response)
                    .build();
        }catch(Exception e){
            e.printStackTrace();
            return GenericResponseV2.<LocationDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve location")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateLocationById(Long locationId, Location location) {
        return null;
    }

}
