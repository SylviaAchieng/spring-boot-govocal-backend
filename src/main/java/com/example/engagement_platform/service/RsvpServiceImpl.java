package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.exception.NoSuchRecordFoundException;
import com.example.engagement_platform.mappers.RsvpMapper;
import com.example.engagement_platform.model.Event;
import com.example.engagement_platform.model.RSVP;
import com.example.engagement_platform.model.User;
import com.example.engagement_platform.model.dto.request.RsvpRequest;
import com.example.engagement_platform.model.dto.response.RsvpDto;
import com.example.engagement_platform.repository.EventRepository;
import com.example.engagement_platform.repository.RsvpRepository;
import com.example.engagement_platform.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class RsvpServiceImpl implements RsvpService {

    private final RsvpRepository rsvpRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final RsvpMapper rsvpMapper;

    @Override
    public GenericResponseV2<RsvpDto> add(RsvpRequest rsvpRequest) {
        // step 1 check if event and user are present
//        User user = userRepository.findByFullName(rsvpRequest.getUser().getFullName()).orElseThrow(() -> new NoSuchRecordFoundException("User not found")); //todo:
        User optuser = userRepository.findByFullNameAndEmail(rsvpRequest.getUser().getFullName(), rsvpRequest.getUser().getEmail()).orElseThrow(() -> new NoSuchRecordFoundException("User not found"));
        Event event = eventRepository.findById(rsvpRequest.getEvent().getEventId()).orElseThrow(() -> new NoSuchRecordFoundException("Event not found"));

        // step 2: check if event is still on
        LocalDate eventDate = event.getEventDate().toLocalDate();
        if (LocalDate.now().isAfter(eventDate)) {
            return GenericResponseV2.<RsvpDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Event already closed")
                    .build();
        }

        // Step 3: Check if the user has already RSVPed for this event
        boolean alreadyRsvped = rsvpRepository.existsByUserAndEvent(optuser, event);
        if (alreadyRsvped) {
            return GenericResponseV2.<RsvpDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("You have already RSVPed for this event")
                    .build();
        }

        //todo: step 3: check if max attendee reached

        // todo: step 4: optional: check if paid

        // step 5: save rsvp
        RSVP rsvp = RSVP.builder()
                .rsvpStatus("BOOKED") //TODO: figure out which status go here
                .event(event)
                .user(optuser)
                .build();
        RSVP savedRsvp = rsvpRepository.save(rsvp);
        return GenericResponseV2.<RsvpDto>builder()
                .status(ResponseStatusEnum.SUCCESS)
                .message("Rsvp placed successfully")
                ._embedded(mapRsvToRsvpDto(savedRsvp))
                .build();
    }

    @Override
    public GenericResponseV2<List<RsvpDto>> getAllRsvpByEventId(Long eventId) {
        try {
            Event event = Event.builder()
                    .eventId(eventId)
                    .build();
            List<RSVP> rsvps = rsvpRepository.findAllByEvent(event);
            List<RsvpDto> response = rsvps.stream().map(rsvpMapper::RSVPToRsvpDto).collect(Collectors.toList());
            return GenericResponseV2.<List<RsvpDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Successfully retrieved rsvps")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<RsvpDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve rsvps")
                    ._embedded(null)
                    .build();
        }
    }

    //todo: implement a better mapper
    private RsvpDto mapRsvToRsvpDto(RSVP savedRsvp) {
        return RsvpDto.builder()
                .id(savedRsvp.getRsvpID())
                .build();
    }
}
