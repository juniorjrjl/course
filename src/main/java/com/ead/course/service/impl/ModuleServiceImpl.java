package com.ead.course.service.impl;

import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
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
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(final ModuleModel model) {
        var lessons = lessonRepository.findAllLessonsIntoModule(model.getId());
        if (CollectionUtils.isNotEmpty(lessons)){
            if (CollectionUtils.isNotEmpty(lessons)){
                lessonRepository.deleteAll(lessons);
            }
        }
        moduleRepository.delete(model);
    }

    @Override
    public ModuleModel save(final ModuleModel model) {
        return moduleRepository.save(model);
    }

    @Override
    public Optional<ModuleModel> findModuleIntoCourse(final UUID courseId, final UUID id) {
        return moduleRepository.findModulesIntoCourse(courseId, id);
    }

    @Override
    public Page<ModuleModel> findAllByCourse(final Specification<ModuleModel> spec, final Pageable page) {
        return moduleRepository.findAll(spec, page);
    }

    @Override
    public Optional<ModuleModel> findById(final UUID id) {
        return moduleRepository.findById(id);
    }
}
