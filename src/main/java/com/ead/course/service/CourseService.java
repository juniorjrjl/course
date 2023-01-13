package com.ead.course.service;

import com.ead.course.model.CourseModel;

import java.util.UUID;

public interface CourseService {

    void delete(final UUID id);

    CourseModel save(final CourseModel model);

    CourseModel update(final UUID id, final CourseModel model);

    void saveSubscriptionUserInCourse(final UUID id, final UUID userId);

}
