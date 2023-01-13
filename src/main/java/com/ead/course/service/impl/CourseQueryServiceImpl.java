package com.ead.course.service.impl;

import com.ead.course.exception.DomainNotFoundException;
import com.ead.course.model.CourseModel;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseQueryService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CourseQueryServiceImpl implements CourseQueryService {

    private final CourseRepository courseRepository;

    @Override
    public CourseModel findById(final UUID id) {
        return courseRepository.findById(id).orElseThrow(() -> new DomainNotFoundException("Course not found"));
    }

    @Override
    public Page<CourseModel> findAll(final Specification<CourseModel> spec, final Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(final UUID courseId, final UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }
}
