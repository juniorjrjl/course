package com.ead.course.adapter.mapper;

import com.ead.course.adapter.dto.CourseFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.outbound.persistence.entity.UserEntity;
import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import org.apache.commons.collections4.CollectionUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.mapstruct.MappingConstants.ComponentModel.SPRING;

@Mapper(componentModel = SPRING)
public abstract class CourseMapper {

    @Mapping(target = "modulesIds", expression = "java(getModuleEntityId(entity.getModules()))")
    @Mapping(target = "usersIds", expression = "java(getUserEntityId(entity.getUsers()))")
    public abstract CourseDomain toDomain(final CourseEntity entity);

    protected Set<UUID> getModuleEntityId(final Set<ModuleEntity> entities){
        return CollectionUtils.isNotEmpty(entities) ?
                entities.stream().map(ModuleEntity::getId).collect(Collectors.toSet()) :
                null;
    }

    protected Set<UUID> getUserEntityId(final Set<UserEntity> entities){
        return CollectionUtils.isNotEmpty(entities) ?
                entities.stream().map(UserEntity::getId).collect(Collectors.toSet()) :
                null;
    }

    @Mapping(target = "modules", expression = "java(getIdInModuleDomain(domain.modulesIds()))")
    @Mapping(target = "users", expression = "java(getIdInUserDomain(domain.usersIds()))")
    public abstract CourseEntity toEntity(final CourseDomain domain);

    protected Set<ModuleEntity> getIdInModuleDomain(final Set<UUID> ids){
        return CollectionUtils.isNotEmpty(ids) ?
                ids.stream().map(id -> {
                    var module = new ModuleEntity();
                    module.setId(id);
                    return module;
                }).collect(Collectors.toSet()) :
                null;
    }

    protected Set<UserEntity> getIdInUserDomain(final Set<UUID> ids){
        return CollectionUtils.isNotEmpty(ids) ?
                ids.stream().map(id -> {
                    var user = new UserEntity();
                    user.setId(id);
                    return user;
                }).collect(Collectors.toSet()) :
                null;
    }

    public abstract CourseFilterDomain toDomain(final CourseFilterDTO dto, UUID userId);

    public abstract CourseFilterDTO toDTO(final CourseFilterDomain dto);

}
