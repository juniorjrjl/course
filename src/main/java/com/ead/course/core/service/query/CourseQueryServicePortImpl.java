package com.ead.course.core.service.query;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.exception.DomainNotFoundException;
import com.ead.course.core.port.persistence.CoursePersistencePort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CourseQueryServicePortImpl implements CourseQueryServicePort {

    private final CoursePersistencePort coursePersistencePort;

    @Override
    public CourseDomain findById(final UUID id) {
        return coursePersistencePort.findById(id).orElseThrow(() -> new DomainNotFoundException("Course not found"));
    }

    @Override
    public List<CourseDomain> findAll(final CourseFilterDomain filterDomain, final PageInfo pageInfo) {
        return coursePersistencePort.findAll(filterDomain, pageInfo);
    }

    @Override
    public boolean existsByCourseAndUser(final UUID id, final UUID userId) {
        return coursePersistencePort.existsByCourseAndUser(id, userId);
    }
}
