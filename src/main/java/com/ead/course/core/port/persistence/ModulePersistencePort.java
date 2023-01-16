package com.ead.course.core.port.persistence;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModulePersistencePort {

    void delete(final UUID id, final UUID courseId);

    ModuleDomain save(final ModuleDomain domain);

    void update(final ModuleDomain domain);

    Optional<ModuleDomain> findModuleIntoCourse(final UUID courseId, final UUID id);

    List<ModuleDomain> findAllByCourse(final ModuleFilterDomain filterDomain, final PageInfo pageInfo);

    Optional<ModuleDomain> findById(final UUID id);

}
