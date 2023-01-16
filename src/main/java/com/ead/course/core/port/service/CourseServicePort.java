package com.ead.course.core.port.service;

import com.ead.course.core.domain.CourseDomain;

import java.util.UUID;

public interface CourseServicePort {

    void delete(final UUID id);

    CourseDomain save(final CourseDomain domain);

    CourseDomain update(final CourseDomain domain);

    void saveSubscriptionUserInCourse(final UUID id, final UUID userId);

}
