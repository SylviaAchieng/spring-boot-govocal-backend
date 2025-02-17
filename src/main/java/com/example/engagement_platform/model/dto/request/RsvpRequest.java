package com.example.engagement_platform.model.dto.request;

import com.example.engagement_platform.model.dto.UserDto;
import com.example.engagement_platform.model.dto.response.EventDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RsvpRequest {
    private EventDto event;
    private UserDto user;
}
