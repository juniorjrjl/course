package com.ead.course.core.port.service.query;

import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.UUID;

public interface LessonQueryServicePort {

    LessonDomain findById(final UUID id);
    LessonDomain findLessonIntoModule(final UUID moduleId, final UUID id);

    List<LessonDomain> findAllByLesson(final LessonFilterDomain filterDomain, final PageInfo pageInfo);

}
