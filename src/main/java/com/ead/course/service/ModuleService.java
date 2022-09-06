package com.ead.course.service;

import com.ead.course.model.ModuleModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleService {

    void delete(final ModuleModel model);

    ModuleModel save(final ModuleModel model);

    Optional<ModuleModel> findModuleIntoCourse(final UUID courseId, final UUID id);

    List<ModuleModel> findAllByCourse(final UUID courseId);
}
