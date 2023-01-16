package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleJpaRepository extends JpaRepository<ModuleEntity, UUID>, JpaSpecificationExecutor<ModuleEntity> {

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_id = :courseId", nativeQuery = true)
    List<ModuleEntity> findAllModulesIntoCourse(final UUID courseId);

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_id = :courseId AND id = :id", nativeQuery = true)
    Optional<ModuleEntity> findModulesIntoCourse(final UUID courseId, final UUID id);

}
