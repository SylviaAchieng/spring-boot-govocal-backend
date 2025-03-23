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
import java.util.List;

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
                    "<b>County:</b> " + location.getCounty() + "<br>" +
                    "<b>User Type:</b> " + contactUsDto.getUserType();

            emailService.sendEmail("achiengsylvia157@gmail.com", subject, body,contactUsDto.getEmail(), contactUsDto.getFullName());
            String subject1 = "Thank you for contacting us";
            String body1 = "<html>" +
                    "<body style='font-family: Arial, sans-serif;'>" +
                    "<p>Dear " + contactUsDto.getFullName() + ",</p>" +
                    "<p>Thank you for reaching out to us. We have received your message and will get back to you as soon as possible.</p>" +
                    "<p>If you have any urgent concerns, feel free to reply to this email.</p>" +
                    "<br>" +
                    "<p>Best regards,</p>" +
                    "<p><strong>The GOVocal Team</strong></p>" +
                    "</body></html>";
            emailService.sendEmail(contactUsDto.getEmail(), subject1, body1,"achiengsylvia157@gmail.com", "GOVocal");
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

    @Override
    public GenericResponseV2<List<ContactUsDto>> getAllMessages() {
        try {
            List<ContactUsDto> response = contactUsRepository.findAll()
                    .stream()
                    .map(contactUsMapper::toDto)
                    .toList();
            return GenericResponseV2.<List<ContactUsDto>>builder()
                    .status(ResponseStatusEnum.SUCCESS)
                    .message("Messages retrieved successfully")
                    ._embedded(response)
                    .build();
        }catch (Exception e){
            e.printStackTrace();
            return GenericResponseV2.<List<ContactUsDto>>builder()
                    .status(ResponseStatusEnum.ERROR)
                    .message("Unable to retrieve messages")
                    ._embedded(null)
                    .build();
        }
    }
}
