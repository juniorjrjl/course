package com.ead.course.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

@Builder
public record NotificationCommandDTO(@JsonProperty("title")String title,
                                     @JsonProperty("message")String message,
                                     @JsonProperty("userId") UUID userId){
    
}
