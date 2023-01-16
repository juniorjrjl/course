package com.ead.course.adapter.mapper;

import com.ead.course.adapter.dto.LessonFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.UUID;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class LessonMapper {

    @Mapping(target = "moduleId", source = "module.id")
    public abstract LessonDomain toDomain(final LessonEntity entity);

    @Mapping(target = "module.id", source = "moduleId")
    public abstract LessonEntity toEntity(final LessonDomain domain);

    public abstract LessonFilterDomain toDomain(final LessonFilterDTO dto, final UUID moduleId);

    public abstract LessonFilterDTO toDTO(final LessonFilterDomain dto);


}
