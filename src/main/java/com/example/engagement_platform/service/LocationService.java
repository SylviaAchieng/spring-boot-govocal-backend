package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponse;
import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.LocationDto;

import java.util.List;

public interface LocationService {
    GenericResponse createLocation(Location location);

    GenericResponseV2<List<LocationDto>> getAllLocations();

    GenericResponseV2<LocationDto> getLocationById(Long locationId);

    GenericResponseV2<Boolean> updateLocationById(Long locationId, Location location);

}
