package com.ead.course.adapter.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.UUID;

public record NotificationCommandDTO(@JsonProperty("title")String title,
                                     @JsonProperty("message")String message,
                                     @JsonProperty("userId") UUID userId){

    @Builder(toBuilder = true)
    public NotificationCommandDTO{}

}
