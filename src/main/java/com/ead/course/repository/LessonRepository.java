package com.ead.course.repository;

import com.ead.course.model.LessonModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonRepository extends JpaRepository<LessonModel, UUID>, JpaSpecificationExecutor<LessonModel> {

    @Query(value = "SELECT * FROM TB_LESSONS WHERE module_id = :moduleId", nativeQuery = true)
    List<LessonModel> findAllLessonsIntoModule(final UUID moduleId);

    @Query(value = "SELECT * FROM TB_LESSONS WHERE module_id = :moduleId AND id = :id", nativeQuery = true)
    Optional<LessonModel> findLessonIntoModule(final UUID moduleId, final UUID id);

}
