package com.ead.course.adapter.service.decorator.query;

import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.query.LessonQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class LessonQueryServicePortImplDecorator implements LessonQueryServicePort {

    private final LessonQueryServicePort lessonQueryServicePort;

    @Transactional
    @Override
    public LessonDomain findById(final UUID id) {
        return lessonQueryServicePort.findById(id);
    }

    @Transactional
    @Override
    public LessonDomain findLessonIntoModule(final UUID moduleId, final UUID id) {
        return lessonQueryServicePort.findLessonIntoModule(moduleId, id);
    }

    @Transactional
    @Override
    public List<LessonDomain> findAllByLesson(final LessonFilterDomain filterDomain, final PageInfo pageInfo) {
        return lessonQueryServicePort.findAllByLesson(filterDomain, pageInfo);
    }
}
