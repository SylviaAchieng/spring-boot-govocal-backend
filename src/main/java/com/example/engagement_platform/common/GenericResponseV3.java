package com.example.engagement_platform.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseV3<T, X> {
    private String message;
    private ResponseStatusEnum status;
    private X metadata;
    private T _embedded;
}