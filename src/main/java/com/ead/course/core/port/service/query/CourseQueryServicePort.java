package com.ead.course.core.port.service.query;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.UUID;

public interface CourseQueryServicePort {

    CourseDomain findById(final UUID id);

    List<CourseDomain> findAll(final CourseFilterDomain filterDomain, final PageInfo pageInfo);

    boolean existsByCourseAndUser(final UUID id, final UUID userId);

}
