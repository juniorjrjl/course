package com.ead.course.core.domain;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.UUID;

public record LessonDomain(
        UUID id,

        String title,

        String description,

        String videoUrl,

        OffsetDateTime creationDate,
        UUID moduleId) {

    @Builder(toBuilder = true)
    public LessonDomain{}

}
