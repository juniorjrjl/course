package com.ead.course.service;

import com.ead.course.model.CourseModel;
import com.ead.course.model.UserModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(final CourseModel model);

    CourseModel save(final CourseModel model);

    Optional<CourseModel> findById(final UUID id);

    Page<CourseModel> findAll(final Specification<CourseModel> spec, final Pageable pageable);

    boolean existsByCourseAndUser(final UUID courseId, final UUID userId);

    void saveSubscriptionUserInCourse(final CourseModel courseModel, final UserModel userModel);

}
