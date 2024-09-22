package com.example.engagement_platform.common;

import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GenericResponse {
    private ResponseStatusEnum status;
    private String message;
    private List<Object> _embedded = new ArrayList<>();
}
