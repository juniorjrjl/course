package com.ead.course.service.impl;

import com.ead.course.exception.DomainNotFoundException;
import com.ead.course.model.ModuleModel;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleQueryService;
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
public class ModuleQueryServiceImpl implements ModuleQueryService {

    private final ModuleRepository moduleRepository;

    @Override
    public ModuleModel findModuleIntoCourse(final UUID courseId, final UUID id) {
        return moduleRepository.findModulesIntoCourse(courseId, id)
                .orElseThrow(() -> new DomainNotFoundException("Module not found for this course"));
    }

    @Override
    public Page<ModuleModel> findAllByCourse(final Specification<ModuleModel> spec, final Pageable page) {
        return moduleRepository.findAll(spec, page);
    }

    @Override
    public ModuleModel findById(final UUID id) {
        return moduleRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Module not found"));
    }
}
