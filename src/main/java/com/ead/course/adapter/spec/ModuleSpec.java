package com.ead.course.adapter.spec;

import com.ead.course.adapter.dto.ModuleFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity_;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity_;
import jakarta.persistence.criteria.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class ModuleSpec {

    public static Specification<ModuleEntity> findAllFilter(final ModuleFilterDTO filter, final UUID courseId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (StringUtils.isNotBlank(filter.title())){
                predicates.add(criteriaBuilder.like(root.get(ModuleEntity_.title), filter.likeTitle()));
            }
            if (Objects.nonNull(courseId)){
                query.distinct(true);
                var course = query.from(CourseEntity.class);
                var courseModules = course.get(CourseEntity_.modules);
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(course.get(CourseEntity_.id), courseId), criteriaBuilder.isMember(root, courseModules)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
