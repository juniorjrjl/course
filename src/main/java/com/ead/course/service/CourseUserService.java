package com.ead.course.service;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;

import java.util.UUID;

public interface CourseUserService {

    boolean existsByCourseAndUserId(final CourseModel courseModel, final UUID userId);

    CourseUserModel save(final CourseUserModel courseUserModel);

    CourseUserModel saveAndSendSubscriptionUserInCourse(final CourseUserModel courseUserModel);

    boolean existsByUserId(final UUID userId);

    void deleteCourseUserByUser(final UUID userId);
}
