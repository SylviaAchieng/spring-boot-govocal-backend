package com.example.engagement_platform.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenericResponseV2<T> {
    private String message;
    private ResponseStatusEnum status;
    private T _embedded;
}
