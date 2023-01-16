package com.ead.course.core.service;

import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.port.persistence.LessonPersistencePort;
import com.ead.course.core.port.service.LessonServicePort;
import com.ead.course.core.port.service.query.LessonQueryServicePort;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class LessonServicePortImpl implements LessonServicePort {

    private final LessonPersistencePort lessonPersistencePort;
    private final LessonQueryServicePort lessonQueryServicePort;
    private final ModuleQueryServicePort moduleQueryServicePort;

    @Override
    public LessonDomain save(final LessonDomain domain) {
        moduleQueryServicePort.findById(domain.moduleId());
        return lessonPersistencePort.save(domain);
    }

    @Override
    public LessonDomain update(final LessonDomain domain) {
        var domainToUpdate = lessonQueryServicePort.findById(domain.id());
        domainToUpdate = domainToUpdate.toBuilder().title(domain.title())
                .description(domain.description())
                .videoUrl(domain.videoUrl())
                .moduleId(domain.moduleId()).build();
        lessonPersistencePort.update(domainToUpdate);
        return domainToUpdate;
    }

    @Override
    public void delete(final UUID id, final UUID moduleId) {
        lessonQueryServicePort.findLessonIntoModule(moduleId, id);
        lessonPersistencePort.delete(id, moduleId);
    }
}
