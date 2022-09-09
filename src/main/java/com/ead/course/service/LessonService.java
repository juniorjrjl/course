package com.ead.course.service;

import com.ead.course.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonService {
    LessonModel save(final LessonModel model);

    Optional<LessonModel> findLessonIntoModule(final UUID moduleId, final UUID id);

    void delete(final LessonModel model);

    Page<LessonModel> findAllByLesson(final Specification<LessonModel> spec, final Pageable pageable);
}
