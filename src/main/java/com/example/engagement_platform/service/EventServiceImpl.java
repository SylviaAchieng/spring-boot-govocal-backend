package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.EventMapper;
import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.UserType;
import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.EventDto;
import com.example.engagement_platform.model.dto.response.LocationDto;
import com.example.engagement_platform.repository.EventRepository;
import com.example.engagement_platform.repository.LocationRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService{

    private final EventRepository eventRepository;
    private final EventMapper eventMapper;
    private final LocationRepository locationRepository;
    private final UserRepository userRepository;

    @Override
    public GenericResponseV2<List<EventDto>> getAllEvents() {
        try {
            List<Event> events = eventRepository.findAll();
            List<EventDto> response = events.stream().map(eventMapper::toDto).toList();
            return GenericResponseV2.<List<EventDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Events retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<EventDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve events")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<EventDto> createEvent(EventDto eventDto) {
        try {
            // Fetch the location details based on the locationId from the location entity
            Location location = locationRepository.findByLocationId(eventDto.getLocation().getLocationId())
                    .orElse(Location.builder().locationId(1L).county("Nairobi").subCounty("Nairobi").build());

            eventDto.setLocation(LocationDto.builder()
                    .locationId(location.getLocationId())
                    .build());
            User user = userRepository.findByUserId(eventDto.getUser().getUserId()).orElse(User.builder().userId(17L).userType(UserType.valueOf("ADMIN")).build());

            eventDto.setUser(UserDto.builder()
                            .userId(user.getUserId())
                    .build());

            Event eventToBeSaved = eventMapper.toEntity(eventDto);
            Event savedEvent = eventRepository.save(eventToBeSaved);
            EventDto response = eventMapper.toDto(savedEvent);
            return GenericResponseV2.<EventDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Event created successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<EventDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to create event")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<EventDto> getEventById(Long eventId) {
        try {
            Event eventFromDb = eventRepository.findByEventId(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            EventDto response = eventMapper.toDto(eventFromDb);
            return GenericResponseV2.<EventDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Event retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<EventDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve event")
                    ._embedded(null)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> deleteEventById(Long eventId) {
        try {
            Event eventFromDb = eventRepository.findByEventId(eventId).orElseThrow(() -> new RuntimeException("Event not found"));
            eventRepository.delete(eventFromDb);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Event deleted successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to delete events")
                    ._embedded(false)
                    .build();
        }
    }

    @Override
    public GenericResponseV2<Boolean> updateEventById(EventDto eventDto, Long eventId) {
        try {
            Event eventToBeSaved = eventMapper.toEntity(eventDto);
            Event savedEvent = eventRepository.save(eventToBeSaved);
            eventMapper.toDto(savedEvent);
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Event updated successfully")
                    ._embedded(true)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<Boolean>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to update event")
                    ._embedded(false)
                    .build();
        }
    }

}
