package com.ead.course.adapter.dto;

import com.ead.course.core.domain.enumeration.CourseLevel;
import com.ead.course.core.domain.enumeration.CourseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record CourseDTO(
        @NotBlank
        @JsonProperty("name")
        String name,
        @NotBlank
        @JsonProperty("description")
        String description,
        @JsonProperty("imageUrl")
        String imageUrl,
        @NotNull
        @JsonProperty("courseStatus")
        CourseStatus courseStatus,
        @NotNull
        @JsonProperty("userInstructor")
        UUID userInstructor,
        @NotNull
        @JsonProperty("courseLevel")
        CourseLevel courseLevel){

}
