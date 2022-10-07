package com.ead.course.service.impl;

import com.ead.course.client.AuthUserClient;
import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import com.ead.course.repository.CourseUserRepository;
import com.ead.course.service.CourseUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService {

    private final CourseUserRepository courseUserRepository;
    private final AuthUserClient authUserClient;

    @Override
    public boolean existsByCourseAndUserId(final CourseModel courseModel, final UUID userId) {
        return courseUserRepository.existsByCourseAndUserId(courseModel, userId);
    }

    @Override
    public CourseUserModel save(final CourseUserModel courseUserModel) {
        return courseUserRepository.save(courseUserModel);
    }

    @Transactional
    @Override
    public CourseUserModel saveAndSendSubscriptionUserInCourse(final CourseUserModel courseUserModel) {
        var model = courseUserRepository.save(courseUserModel);
        authUserClient.postSubscriptionUserInCourse(model.getCourse().getId(), model.getUserId());
        return model;
    }

    @Override
    public boolean existsByUserId(final UUID userId) {
        return courseUserRepository.existsByUserId(userId);
    }

    @Override
    public void deleteCourseUserByUser(final UUID userId) {
        courseUserRepository.deleteAllByUserId(userId);
    }
}
