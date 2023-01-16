package com.ead.course.core.domain;

import com.ead.course.core.domain.enumeration.CourseLevel;
import com.ead.course.core.domain.enumeration.CourseStatus;
import lombok.Builder;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

public record CourseDomain(
        UUID id,

        String name,

        String description,

        String imageUrl,

        OffsetDateTime creationDate,

        OffsetDateTime lastUpdateDate,

        CourseStatus courseStatus,

        CourseLevel courseLevel,

        UUID userInstructor,

        Set<UUID> modulesIds,
        Set<UUID> usersIds
) {

    @Builder(toBuilder = true)
    public CourseDomain{}

}
