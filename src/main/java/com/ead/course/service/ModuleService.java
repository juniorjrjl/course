package com.ead.course.service;

import com.ead.course.model.ModuleModel;

import java.util.UUID;

public interface ModuleService {

    void delete(final UUID id, final UUID courseId);

    ModuleModel save(final ModuleModel model);

    ModuleModel update(final UUID id, final ModuleModel model);

}
