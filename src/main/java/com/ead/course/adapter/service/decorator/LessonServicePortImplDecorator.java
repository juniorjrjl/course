package com.ead.course.adapter.service.decorator;

import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.port.service.LessonServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor
public class LessonServicePortImplDecorator implements LessonServicePort {

    private final LessonServicePort lessonServicePort;

    @Transactional
    @Override
    public LessonDomain save(final LessonDomain domain) {
        return lessonServicePort.save(domain);
    }

    @Transactional
    @Override
    public LessonDomain update(final LessonDomain domain) {
        return lessonServicePort.update(domain);
    }

    @Transactional
    @Override
    public void delete(final UUID id, final UUID moduleId) {
        lessonServicePort.delete(id, moduleId);
    }
}
