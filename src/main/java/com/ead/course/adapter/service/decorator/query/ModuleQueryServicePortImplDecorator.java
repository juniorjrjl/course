package com.ead.course.adapter.service.decorator.query;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class ModuleQueryServicePortImplDecorator implements ModuleQueryServicePort {

    private final ModuleQueryServicePort moduleQueryServicePort;

    @Transactional
    @Override
    public ModuleDomain findModuleIntoCourse(final UUID courseId, final UUID id) {
        return moduleQueryServicePort.findModuleIntoCourse(courseId, id);
    }

    @Transactional
    @Override
    public List<ModuleDomain> findAllByCourse(final ModuleFilterDomain filterDomain, final PageInfo pageInfo) {
        return moduleQueryServicePort.findAllByCourse(filterDomain, pageInfo);
    }

    @Transactional
    @Override
    public ModuleDomain findById(final UUID id) {
        return moduleQueryServicePort.findById(id);
    }
}
