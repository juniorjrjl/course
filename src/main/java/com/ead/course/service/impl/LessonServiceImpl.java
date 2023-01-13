package com.ead.course.service.impl;

import com.ead.course.model.LessonModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonQueryService;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleQueryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final LessonQueryService lessonQueryService;
    private final ModuleQueryService moduleQueryService;

    @Override
    public LessonModel save(final LessonModel model) {
        moduleQueryService.findById(model.getModule().getId());
        return lessonRepository.save(model);
    }

    @Override
    public LessonModel update(final UUID id, final LessonModel model) {
        var modelToUpdate = lessonQueryService.findById(id);
        modelToUpdate.setTitle(model.getTitle());
        modelToUpdate.setDescription(model.getDescription());
        modelToUpdate.setVideoUrl(model.getVideoUrl());
        modelToUpdate.setModule(model.getModule());
        return save(modelToUpdate);
    }

    @Override
    public void delete(final UUID id, final UUID moduleId) {
        var model = lessonQueryService.findLessonIntoModule(moduleId, id);
        lessonRepository.delete(model);
    }

}
