package com.ead.course.repository;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    boolean existsByCourseAndUserId(final CourseModel courseModel, final UUID userId);
}
