package com.ead.course.service.impl;

import com.ead.course.model.LessonModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

    @Override
    public LessonModel save(final LessonModel model) {
        return lessonRepository.save(model);
    }

    @Override
    public Optional<LessonModel> findLessonIntoModule(final UUID moduleId, final UUID id) {
        return lessonRepository.findLessonIntoModule(moduleId, id);
    }

    @Override
    public void delete(final LessonModel model) {
        lessonRepository.delete(model);
    }

    @Override
    public Page<LessonModel> findAllByLesson(final Specification<LessonModel> spec, final Pageable pageable) {
        return lessonRepository.findAll(spec, pageable);
    }
}
