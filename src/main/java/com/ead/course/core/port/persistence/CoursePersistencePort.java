package com.ead.course.core.port.persistence;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import com.ead.course.core.domain.PageInfo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CoursePersistencePort {

    void delete(final UUID id);

    CourseDomain save(final CourseDomain domain);

    void update(final CourseDomain domain);

    void saveSubscriptionUserInCourse(final UUID id, final UUID userId);

    Optional<CourseDomain> findById(final UUID id);

    List<CourseDomain> findAll(final CourseFilterDomain filterDomain, final PageInfo pageInfo);

    boolean existsByCourseAndUser(final UUID id, final UUID userId);

}
