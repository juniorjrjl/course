package com.ead.course.repository;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseUserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CourseUserRepository extends JpaRepository<CourseUserModel, UUID>, JpaSpecificationExecutor<CourseModel> {

    boolean existsByCourseAndUserId(final CourseModel courseModel, final UUID userId);

    @Query(value = "select * from TB_COURSES_USERS where course_id = :courseId", nativeQuery = true)
    List<CourseUserModel> findAllCourseUserIntoCourse(final UUID courseId);

    boolean existsByUserId(final UUID userId);

    void deleteAllByUserId(final UUID userId);
}
