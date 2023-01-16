package com.ead.course.adapter.service.decorator;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.port.service.ModuleServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor
public class ModuleServicePortImplDecorator implements ModuleServicePort {

    private final ModuleServicePort moduleServicePort;

    @Transactional
    @Override
    public void delete(final UUID id, final UUID courseId) {
        moduleServicePort.delete(id, courseId);
    }

    @Transactional
    @Override
    public ModuleDomain save(final ModuleDomain domain) {
        return moduleServicePort.save(domain);
    }

    @Transactional
    @Override
    public ModuleDomain update(final ModuleDomain domain) {
        return moduleServicePort.update(domain);
    }
}
