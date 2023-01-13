package com.ead.course.service;

import com.ead.course.model.LessonModel;

import java.util.UUID;

public interface LessonService {
    LessonModel save(final LessonModel model);

    LessonModel update (final UUID id, final LessonModel model);

    void delete(final UUID id, final UUID moduleId);

}
