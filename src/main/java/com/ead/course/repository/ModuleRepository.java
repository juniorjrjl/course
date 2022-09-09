package com.ead.course.repository;

import com.ead.course.model.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID>, JpaSpecificationExecutor<ModuleModel> {

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(final UUID courseId);

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_id = :courseId AND id = :id", nativeQuery = true)
    Optional<ModuleModel> findModulesIntoCourse(final UUID courseId, final UUID id);
}
