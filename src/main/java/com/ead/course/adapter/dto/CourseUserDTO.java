package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CourseUserDTO(@JsonProperty("courseId")
                            UUID courseId,
                            @JsonProperty("userId")
                            UUID userId) {
}
