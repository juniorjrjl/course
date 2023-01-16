package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.mapper.LessonMapper;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity_;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity_;
import com.ead.course.adapter.spec.LessonSpec;
import com.ead.course.core.domain.LessonDomain;
import com.ead.course.core.domain.LessonFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.persistence.LessonPersistencePort;
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
public class LessonPersistencePortImpl implements LessonPersistencePort {

    private final EntityManager entityManager;
    private final LessonJpaRepository lessonJpaRepository;
    private final LessonMapper lessonMapper;

    @Transactional
    @Override
    public LessonDomain save(final LessonDomain domain) {
        var entity = lessonJpaRepository.save(lessonMapper.toEntity(domain));
        return lessonMapper.toDomain(entity);
    }

    @Transactional
    @Override
    public void update(final LessonDomain domain) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var update = criteriaBuilder.createCriteriaUpdate(LessonEntity.class);
        var root = update.from(LessonEntity.class);
        update.set(root.get(LessonEntity_.title), domain.title());
        update.set(root.get(LessonEntity_.description), domain.description());
        update.set(root.get(LessonEntity_.videoUrl), domain.videoUrl());
        update.set(root.get(LessonEntity_.module).get(ModuleEntity_.id), domain.moduleId());
        update.where(criteriaBuilder.equal(root.get(LessonEntity_.id), domain.id()));
        entityManager.createQuery(update).executeUpdate();
    }

    @Transactional
    @Override
    public void delete(final UUID id, final UUID moduleId) {
        lessonJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Optional<LessonDomain> findById(final UUID id) {
        return lessonJpaRepository.findById(id).map(lessonMapper::toDomain);
    }

    @Transactional
    @Override
    public Optional<LessonDomain> findLessonIntoModule(final UUID moduleId, final UUID id) {
        return lessonJpaRepository.findLessonIntoModule(moduleId, id).map(lessonMapper::toDomain);
    }

    @Transactional
    @Override
    public List<LessonDomain> findAllByLesson(final LessonFilterDomain filterDomain, final PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.pageNumber(), pageInfo.pageSize());
        var filterDTO = lessonMapper.toDTO(filterDomain);
        return lessonJpaRepository.findAll(LessonSpec.findAllFilter(filterDTO, filterDomain.moduleId()), pageable)
                .getContent().stream().map(lessonMapper::toDomain)
                .collect(Collectors.toList());

    }

}
