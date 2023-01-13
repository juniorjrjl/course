package com.ead.course.service;

import com.ead.course.model.ModuleModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface ModuleQueryService {

    ModuleModel findModuleIntoCourse(final UUID courseId, final UUID id);

    Page<ModuleModel> findAllByCourse(final Specification<ModuleModel> spec, final Pageable page);

    ModuleModel findById(final UUID id);
}
