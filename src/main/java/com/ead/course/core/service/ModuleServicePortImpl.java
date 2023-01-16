package com.ead.course.core.service;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.port.persistence.ModulePersistencePort;
import com.ead.course.core.port.service.ModuleServicePort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import lombok.AllArgsConstructor;

import java.util.UUID;

@AllArgsConstructor
public class ModuleServicePortImpl implements ModuleServicePort {

    private final ModulePersistencePort modulePersistencePort;
    private final ModuleQueryServicePort moduleQueryServicePort;
    private final CourseQueryServicePort courseQueryServicePort;

    @Override
    public void delete(final UUID id, final UUID courseId) {
        moduleQueryServicePort.findModuleIntoCourse(courseId, id);
        modulePersistencePort.delete(id, courseId);
    }

    @Override
    public ModuleDomain save(final ModuleDomain domain) {
        courseQueryServicePort.findById(domain.courseId());
        return modulePersistencePort.save(domain);
    }

    @Override
    public ModuleDomain update(final ModuleDomain domain) {
        var domainToUpdate = moduleQueryServicePort.findById(domain.id());
        domainToUpdate = domainToUpdate.toBuilder().title(domain.title())
                .description(domain.description())
                .courseId(domain.courseId()).build();
        modulePersistencePort.update(domainToUpdate);
        return domainToUpdate;
    }
}
