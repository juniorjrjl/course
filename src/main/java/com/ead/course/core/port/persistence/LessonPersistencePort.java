package com.ead.course.core.port.persistence;


import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonPersistencePort {

    LessonDomain save(final LessonDomain domain);

    void update (final LessonDomain domain);

    void delete(final UUID id, final UUID moduleId);

    Optional<LessonDomain> findById(final UUID id);
    Optional<LessonDomain> findLessonIntoModule(final UUID moduleId, final UUID id);

    List<LessonDomain> findAllByLesson(final LessonFilterDomain filterDomain, final PageInfo pageInfo);

}
