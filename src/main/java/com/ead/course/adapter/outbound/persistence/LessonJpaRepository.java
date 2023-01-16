package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LessonJpaRepository extends JpaRepository<LessonEntity, UUID>, JpaSpecificationExecutor<LessonEntity> {

    @Query(value = "SELECT * FROM TB_LESSONS WHERE module_id = :moduleId", nativeQuery = true)
    List<LessonEntity> findAllLessonsIntoModule(final UUID moduleId);

    @Query(value = "SELECT * FROM TB_LESSONS WHERE module_id = :moduleId AND id = :id", nativeQuery = true)
    Optional<LessonEntity> findLessonIntoModule(final UUID moduleId, final UUID id);

}
