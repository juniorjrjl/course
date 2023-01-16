package com.ead.course.core.port.service;

import com.ead.course.core.domain.ModuleDomain;

import java.util.UUID;

public interface ModuleServicePort {

    void delete(final UUID id, final UUID courseId);

    ModuleDomain save(final ModuleDomain domain);

    ModuleDomain update(final ModuleDomain domain);

}
