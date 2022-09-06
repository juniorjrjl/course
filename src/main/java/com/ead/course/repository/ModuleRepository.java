package com.ead.course.repository;

import com.ead.course.model.ModuleModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ModuleRepository extends JpaRepository<ModuleModel, UUID> {

    @Query(value = "SELECT * FROM TB_MODULES WHERE course_id = :courseId", nativeQuery = true)
    List<ModuleModel> findAllModulesIntoCourse(final UUID courseId);

}
