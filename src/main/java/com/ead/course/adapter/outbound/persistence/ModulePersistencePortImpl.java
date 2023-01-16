package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.mapper.ModuleMapper;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity_;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity_;
import com.ead.course.adapter.spec.ModuleSpec;
import com.ead.course.core.domain.ModuleDomain;
import com.ead.course.core.domain.ModuleFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.persistence.ModulePersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class ModulePersistencePortImpl implements ModulePersistencePort {

    private final EntityManager entityManager;
    private final ModuleJpaRepository moduleJpaRepository;
    private final ModuleMapper moduleMapper;

    @Transactional
    @Override
    public void delete(final UUID id, final UUID courseId) {
        moduleJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public ModuleDomain save(final ModuleDomain domain) {
        var entity = moduleMapper.toEntity(domain);
        return moduleMapper.toDomain(moduleJpaRepository.save(entity));
    }

    @Transactional
    @Override
    public void update(final ModuleDomain domain) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var update = criteriaBuilder.createCriteriaUpdate(ModuleEntity.class);
        var root = update.from(ModuleEntity.class);
        update.set(root.get(ModuleEntity_.title), domain.title());
        update.set(root.get(ModuleEntity_.description), domain.description());
        update.set(root.get(ModuleEntity_.course).get(CourseEntity_.id), domain.courseId());
        update.where(criteriaBuilder.equal(root.get(ModuleEntity_.id), domain.id()));
        entityManager.createQuery(update).executeUpdate();
    }

    @Transactional
    @Override
    public Optional<ModuleDomain> findModuleIntoCourse(final UUID courseId, final UUID id) {
        return moduleJpaRepository.findModulesIntoCourse(courseId, id).map(moduleMapper::toDomain);
    }

    @Transactional
    @Override
    public List<ModuleDomain> findAllByCourse(final ModuleFilterDomain filterDomain, final PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.pageNumber(), pageInfo.pageSize());
        var filterDTO = moduleMapper.toDTO(filterDomain);
        return moduleJpaRepository.findAll(ModuleSpec.findAllFilter(filterDTO, filterDomain.moduleId()), pageable)
                .getContent().stream().map(moduleMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public Optional<ModuleDomain> findById(final UUID id) {
        return moduleJpaRepository.findById(id).map(moduleMapper::toDomain);
    }

}
