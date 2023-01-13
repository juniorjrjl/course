package com.ead.course.service.impl;

import com.ead.course.model.CourseModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseQueryService;
import com.ead.course.service.ModuleQueryService;
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
    private final ModuleQueryService moduleQueryService;

    private final CourseQueryService courseQueryService;
    //private final LessonRepository lessonRepository;

    @Transactional
    @Override
    public void delete(final UUID id, final UUID courseId) {
        var model = moduleQueryService.findModuleIntoCourse(courseId, id);
        /*var lessons = lessonRepository.findAllLessonsIntoModule(model.getId());
        if (CollectionUtils.isNotEmpty(lessons)){
            if (CollectionUtils.isNotEmpty(lessons)){
                lessonRepository.deleteAll(lessons);
            }
        }*/
        moduleRepository.delete(model);
    }

    @Override
    public ModuleModel save(final ModuleModel model) {
        courseQueryService.findById(model.getCourse().getId());
        return moduleRepository.save(model);
    }

    @Override
    public ModuleModel update(final UUID id, final ModuleModel model) {
        var modelToUpdate = moduleQueryService.findById(id);
        modelToUpdate.setTitle(model.getTitle());
        modelToUpdate.setDescription(model.getDescription());
        modelToUpdate.setCourse(model.getCourse());
        return save(modelToUpdate);
    }

}
