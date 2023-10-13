package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;


public record LessonDTO(
        @NotBlank
        @JsonProperty("title")
        String title,
        @JsonProperty("description")
        String description,
        @NotBlank
        @JsonProperty("videoUrl")
        String videoUrl){}
