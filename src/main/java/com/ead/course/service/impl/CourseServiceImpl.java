package com.ead.course.service.impl;

import com.ead.course.model.CourseModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(final CourseModel model) {
        var modules = moduleRepository.findAllModulesIntoCourse(model.getId());
        if (CollectionUtils.isNotEmpty(modules)){
            for(ModuleModel module: modules){
                var lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
                if (CollectionUtils.isNotEmpty(lessons)){
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.delete(model);
    }

    @Override
    public CourseModel save(final CourseModel model) {
        return courseRepository.save(model);
    }

    @Override
    public Optional<CourseModel> findById(final UUID id) {
        return courseRepository.findById(id);
    }

    @Override
    public Page<CourseModel> findAll(final Specification<CourseModel> spec, final Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }
}
