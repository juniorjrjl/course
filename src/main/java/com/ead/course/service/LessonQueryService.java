package com.ead.course.service;

import com.ead.course.model.LessonModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface LessonQueryService {

    LessonModel findById(final UUID id);
    LessonModel findLessonIntoModule(final UUID moduleId, final UUID id);

    Page<LessonModel> findAllByLesson(final Specification<LessonModel> spec, final Pageable pageable);
}
