package com.ead.course.core.service.query;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.exception.DomainNotFoundException;
import com.ead.course.core.port.persistence.ModulePersistencePort;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
public class ModuleQueryServicePortImpl implements ModuleQueryServicePort {

    private final ModulePersistencePort modulePersistencePort;

    @Override
    public ModuleDomain findModuleIntoCourse(final UUID courseId, final UUID id) {
        return modulePersistencePort.findModuleIntoCourse(courseId, id)
                .orElseThrow(() -> new DomainNotFoundException("Module not found for this course"));
    }

    @Override
    public List<ModuleDomain> findAllByCourse(final ModuleFilterDomain filterDomain, final PageInfo pageInfo) {
        return modulePersistencePort.findAllByCourse(filterDomain, pageInfo);
    }

    @Override
    public ModuleDomain findById(final UUID id) {
        return modulePersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("Module not found"));
    }
}
