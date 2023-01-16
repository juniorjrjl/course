package com.ead.course.core.service.query;

import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.exception.DomainNotFoundException;
import com.ead.course.core.port.persistence.LessonPersistencePort;
import com.ead.course.core.port.service.query.LessonQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class LessonQueryServicePortImpl implements LessonQueryServicePort {

    private final LessonPersistencePort lessonPersistencePort;

    @Override
    public LessonDomain findById(final UUID id) {
        return lessonPersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("Lesson not found"));
    }

    @Override
    public LessonDomain findLessonIntoModule(final UUID moduleId, final UUID id) {
        return lessonPersistencePort.findLessonIntoModule(moduleId, id).orElseThrow(() -> new DomainNotFoundException("Lesson not found for this module"));
    }

    @Override
    public List<LessonDomain> findAllByLesson(final LessonFilterDomain filterDomain, final PageInfo pageInfo) {
        return lessonPersistencePort.findAllByLesson(filterDomain, pageInfo);
    }

}
