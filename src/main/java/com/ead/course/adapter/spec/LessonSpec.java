package com.ead.course.adapter.spec;

import com.ead.course.adapter.dto.LessonFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity_;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class LessonSpec {

    public static Specification<LessonEntity> findAllFilter(final LessonFilterDTO filter, final UUID moduleId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(filter.title())){
                predicates.add(criteriaBuilder.like(root.get(LessonEntity_.title), filter.likeTitle()));
            }
            if (Objects.nonNull(moduleId)){
                query.distinct(true);
                var module = query.from(ModuleEntity.class);
                var moduleLessons = module.get(ModuleEntity_.lessons);
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(module.get(ModuleEntity_.id), moduleId), criteriaBuilder.isMember(root, moduleLessons)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
