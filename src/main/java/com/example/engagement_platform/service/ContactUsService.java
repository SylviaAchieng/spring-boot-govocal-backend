package com.example.engagement_platform.service;

import com.example.engagement_platform.common.GenericResponseV2;
import com.example.engagement_platform.model.dto.response.ContactUsDto;

public interface ContactUsService {
    GenericResponseV2<ContactUsDto> addMessage(ContactUsDto contactUsDto);
}
