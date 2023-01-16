package com.ead.course.core.port.service.query;

import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.UUID;

public interface ModuleQueryServicePort {

    ModuleDomain findModuleIntoCourse(final UUID courseId, final UUID id);

    List<ModuleDomain> findAllByCourse(final ModuleFilterDomain filterDomain, final PageInfo pageInfo);

    ModuleDomain findById(final UUID id);

}
