package com.ead.course.adapter.outbound.persistence;

import com.ead.course.adapter.mapper.CourseMapper;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity_;
import com.ead.course.adapter.spec.CourseSpec;
import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.domain.CourseFilterDomain;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.persistence.CoursePersistencePort;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CoursePersistencePortImpl implements CoursePersistencePort {

    private EntityManager entityManager;
    private final CourseJpaRepository courseJpaRepository;
    private final CourseMapper courseMapper;

    @Transactional
    @Override
    public void delete(final UUID id) {
        courseJpaRepository.deleteById(id);
    }

    @Transactional
    @Override
    public CourseDomain save(final CourseDomain domain) {
        var entity = courseJpaRepository.save(courseMapper.toEntity(domain));
        return courseMapper.toDomain(entity);
    }

    @Transactional
    @Override
    public void update(final CourseDomain domain) {
        var criteriaBuilder = entityManager.getCriteriaBuilder();
        var update = criteriaBuilder.createCriteriaUpdate(CourseEntity.class);
        var root = update.from(CourseEntity.class);
        update.set(root.get(CourseEntity_.name), domain.name());
        update.set(root.get(CourseEntity_.description), domain.description());
        update.set(root.get(CourseEntity_.imageUrl), domain.imageUrl());
        update.set(root.get(CourseEntity_.courseStatus), domain.courseStatus());
        update.set(root.get(CourseEntity_.courseLevel), domain.courseLevel());
        update.set(root.get(CourseEntity_.userInstructor), domain.userInstructor());
        update.set(root.get(CourseEntity_.lastUpdateDate), OffsetDateTime.now());
        update.where(criteriaBuilder.equal(root.get(CourseEntity_.id), domain.id()));
        entityManager.createQuery(update).executeUpdate();
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(final UUID id, final UUID userId) {
        courseJpaRepository.saveSubscriptionUserInCourse(id, userId);
    }

    @Transactional
    @Override
    public Optional<CourseDomain> findById(final UUID id) {
        return courseJpaRepository.findById(id).map(courseMapper::toDomain);
    }

    @Transactional
    @Override
    public List<CourseDomain> findAll(final CourseFilterDomain filterDomain, final PageInfo pageInfo) {
        var pageable = PageRequest.of(pageInfo.pageNumber(), pageInfo.pageSize());
        var filterDTO = courseMapper.toDTO(filterDomain);
        return courseJpaRepository.findAll(CourseSpec.findAllFilter(filterDTO, filterDomain.userId()), pageable)
                .getContent().stream().map(courseMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Transactional
    @Override
    public boolean existsByCourseAndUser(final UUID id, final UUID userId) {
        return courseJpaRepository.existsByCourseAndUser(id, userId);
    }

}
