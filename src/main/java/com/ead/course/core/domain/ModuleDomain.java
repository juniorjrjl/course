package com.ead.course.core.domain;

import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record ModuleDomain(
        UUID id,

        String title,
        String description,
        OffsetDateTime creationDate,
        UUID courseId,
        Set<UUID> lessonsIds
) {

    @Builder(toBuilder = true)
    public ModuleDomain{}

}
