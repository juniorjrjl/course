package com.ead.course.adapter.service.decorator;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.port.service.CourseServicePort;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Component
@AllArgsConstructor
public class CourseServicePortImplDecorator implements CourseServicePort {

    private final CourseServicePort courseServicePort;

    @Transactional
    @Override
    public void delete(final UUID id) {
        courseServicePort.delete(id);
    }

    @Transactional
    @Override
    public CourseDomain save(final CourseDomain domain) {
        return courseServicePort.save(domain);
    }

    @Transactional
    @Override
    public CourseDomain update(final CourseDomain domain) {
        return courseServicePort.update(domain);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(final UUID id, final UUID userId) {
        courseServicePort.saveSubscriptionUserInCourse(id, userId);
    }
}
