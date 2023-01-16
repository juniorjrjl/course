package com.ead.course.core.port.service;

import com.ead.course.core.domain.LessonDomain;

import java.util.UUID;

public interface LessonServicePort {

    LessonDomain save(final LessonDomain domain);

    LessonDomain update (final LessonDomain domain);

    void delete(final UUID id, final UUID moduleId);

}
