package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.mappers.ContactUsMapper;
import com.example.engagement_platform.model.ContactUs;
import com.example.engagement_platform.model.Location;
import com.example.engagement_platform.model.dto.response.ContactUsDto;
import com.example.engagement_platform.repository.ContactUsRepository;
import com.example.engagement_platform.repository.LocationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ContactUsServiceImpl implements ContactUsService{
    private final ContactUsRepository contactUsRepository;
    private final LocationRepository locationRepository;
    private final ContactUsMapper contactUsMapper;
    private final EmailService emailService;

    @Override
    public GenericResponseV2<ContactUsDto> addMessage(ContactUsDto contactUsDto) {
        try {
            Location location = locationRepository.findByLocationId(contactUsDto.getLocation().getLocationId()).orElseThrow(() -> new RuntimeException("Location not found"));

            ContactUs contactUs = contactUsMapper.toEntity(contactUsDto);
            contactUs.setLocation(location);
            contactUs.setDate(LocalDateTime.now());

            contactUs = contactUsRepository.save(contactUs);

            ContactUsDto savedDto = contactUsMapper.toDto(contactUs);
            // Send email notification including county
            String subject = "New Contact Us Message from " + contactUsDto.getFullName();
            String body = "<b>Name:</b> " + contactUsDto.getFullName() + "<br>" +
                    "<b>Email:</b> " + contactUsDto.getEmail() + "<br>" +
                    "<b>Message:</b> " + contactUsDto.getMessage() + "<br>" +
                    "<b>County:</b> " + location.getCounty();

            emailService.sendSimpleMessage("admin@yourdomain.com", subject, body);
            return GenericResponseV2.<ContactUsDto>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Message sent successfully")
                    ._embedded(savedDto)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<ContactUsDto>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to send message")
                    ._embedded(null)
                    .build();
        }
    }
}
