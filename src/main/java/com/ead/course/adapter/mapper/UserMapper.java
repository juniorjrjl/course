package com.ead.course.adapter.mapper;

import com.ead.course.adapter.dto.UserEventDTO;
import com.ead.course.adapter.dto.UserFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.UserEntity;
import com.ead.course.core.domain.UserDomain;
import com.ead.course.core.domain.UserFilterDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class UserMapper {

    @Mapping(target = "coursesIds", expression = "java(getCourseEntityId(entity.getCourses()))")
    public abstract UserDomain toDomain(final UserEntity entity);

    protected Set<UUID> getCourseEntityId(final Set<CourseEntity> entities){
        return CollectionUtils.isNotEmpty(entities) ?
                entities.stream().map(CourseEntity::getId).collect(Collectors.toSet()):
                null;
    }

    @Mapping(target = "courses", expression = "java(getIdInCourseDomain(domain.coursesIds()))")
    public abstract UserEntity toEntity(final UserDomain domain);


    protected Set<CourseEntity> getIdInCourseDomain(final Set<UUID> ids){
        return CollectionUtils.isNotEmpty(ids) ?
                ids.stream().map(id -> {
                    var course = new CourseEntity();
                    course.setId(id);
                    return course;
                }).collect(Collectors.toSet()) :
                null;
    }

    @Mapping(target = "coursesIds", ignore = true)
    public abstract UserDomain toDomain(final UserEventDTO event);

    public abstract UserFilterDomain toDomain(final UserFilterDTO dto);

    public abstract UserFilterDTO toDTO(final UserFilterDomain dto);

}
