package com.ead.course.service.impl;

import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

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
            moduleRepository.delete(model);
        }
    }
}
