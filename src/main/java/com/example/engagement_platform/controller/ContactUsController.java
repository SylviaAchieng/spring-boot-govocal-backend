package com.example.engagement_platform.controller;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.common.ResponseStatusEnum;
import com.example.engagement_platform.model.dto.response.ContactUsDto;
import com.example.engagement_platform.service.ContactUsService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/contact-us")
public class ContactUsController {
    private final ContactUsService contactUsService;

    @PostMapping
    public ResponseEntity<GenericResponseV2<ContactUsDto>> addMessage(@RequestBody ContactUsDto contactUsDto){
        GenericResponseV2<ContactUsDto> response = contactUsService.addMessage(contactUsDto);
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }

    @GetMapping
    public ResponseEntity<GenericResponseV2<List<ContactUsDto>>> getAllMessages(){
        GenericResponseV2<List<ContactUsDto>> response = contactUsService.getAllMessages();
        if (response.getStatus().equals(ResponseStatusEnum.SUCCESS)){
            return ResponseEntity.ok().body(response);
        }else {
            return ResponseEntity.badRequest().body(response);
        }
    }
}
