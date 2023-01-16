package com.ead.course.adapter.mapper;

import com.ead.course.adapter.dto.ModuleFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class ModuleMapper {

    @Mapping(target = "lessonsIds", expression = "java(getLessonEntityId(entity.getLessons()))")
    @Mapping(target = "courseId", source = "course.id")
    public abstract ModuleDomain toDomain(final ModuleEntity entity);

    protected Set<UUID> getLessonEntityId(final Set<LessonEntity> entities){
        return CollectionUtils.isNotEmpty(entities) ?
                entities.stream().map(LessonEntity::getId).collect(Collectors.toSet()) :
                null;
    }

    @Mapping(target = "lessons", expression = "java(getIdInLessonDomain(domain.lessonsIds()))")
    @Mapping(target = "course.id", source = "courseId")
    public abstract ModuleEntity toEntity(final ModuleDomain domain);


    protected Set<LessonEntity> getIdInLessonDomain(final Set<UUID> ids){
        return CollectionUtils.isNotEmpty(ids) ?
                ids.stream().map(id -> {
                    var lesson = new LessonEntity();
                    lesson.setId(id);
                    return lesson;
                }).collect(Collectors.toSet()) :
                null;
    }

    public abstract ModuleFilterDomain toDomain(final ModuleFilterDTO dto, final UUID courseId);

    public abstract ModuleFilterDTO toDTO(final ModuleFilterDomain dto);

}
