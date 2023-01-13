package com.ead.course.service.impl;

import com.ead.course.exception.DomainNotFoundException;
import com.ead.course.model.LessonModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class LessonQueryServiceImpl implements LessonQueryService {

    private final LessonRepository lessonRepository;

    @Override
    public LessonModel findById(final UUID id) {
        return lessonRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Lesson not found"));
    }

    @Override
    public LessonModel findLessonIntoModule(final UUID moduleId, final UUID id) {
        return lessonRepository.findLessonIntoModule(moduleId, id).orElseThrow(() -> new DomainNotFoundException("Lesson not found for this module"));
    }

    @Override
    public Page<LessonModel> findAllByLesson(final Specification<LessonModel> spec, final Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }

}
