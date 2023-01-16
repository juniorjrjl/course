package com.ead.course.adapter.service.decorator.query;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Component
@AllArgsConstructor
public class CourseQueryServicePortImplDecorator implements CourseQueryServicePort {

    private final CourseQueryServicePort courseQueryServicePort;

    @Transactional
    @Override
    public CourseDomain findById(final UUID id) {
        return courseQueryServicePort.findById(id);
    }

    @Transactional
    @Override
    public List<CourseDomain> findAll(final CourseFilterDomain filterDomain, final PageInfo pageInfo) {
        return courseQueryServicePort.findAll(filterDomain, pageInfo);
    }

    @Transactional
    @Override
    public boolean existsByCourseAndUser(final UUID id, final UUID userId) {
        return courseQueryServicePort.existsByCourseAndUser(id, userId);
    }
}
