package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.response.ContactUsDto;

import java.util.List;

public interface ContactUsService {
    GenericResponseV2<ContactUsDto> addMessage(ContactUsDto contactUsDto);

    GenericResponseV2<List<ContactUsDto>> getAllMessages();

}
