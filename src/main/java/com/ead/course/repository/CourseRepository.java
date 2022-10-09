package com.ead.course.repository;

import com.ead.course.model.CourseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface CourseRepository extends JpaRepository<CourseModel, UUID>, JpaSpecificationExecutor<CourseModel> {
    @Query(value = "SELECT CASE WHEN COUNT(tcu) > 0 THEN true ELSE false END FROM TB_COURSES_USERS tcu WHERE tcu.course_id = :courseId AND tcu.user_id = :userId", nativeQuery = true)
    boolean existsByCourseAndUser(final UUID courseId, final UUID userId);

    @Modifying
    @Query(value = "INSERT INTO TB_COURSES_USERS (course_id, user_id) VALUES (:courseId, :userId)", nativeQuery = true)
    void saveSubscriptionUserInCourse(final UUID courseId, final UUID userId);

    @Modifying
    @Query(value = "DELETE FROM TB_COURSES_USERS WHERE course_id = :courseId", nativeQuery = true)
    void deleteCourseUserByCourse(final UUID courseId);

    @Modifying
    @Query(value = "DELETE FROM TB_COURSES_USERS WHERE user_id = :userId",nativeQuery = true)
    void deleteCourseUserByUser(final UUID userId);
}
