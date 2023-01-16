package com.ead.course.adapter.spec;

import com.ead.course.adapter.dto.CourseFilterDTO;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity_;
import com.ead.course.adapter.outbound.persistence.entity.UserEntity;
import com.ead.course.adapter.outbound.persistence.entity.UserEntity_;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class CourseSpec {

    public static Specification<CourseEntity> findAllFilter(final CourseFilterDTO filter, UUID userId){
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (Objects.nonNull(filter.courseLevel())){
                predicates.add(criteriaBuilder.equal(root.get(CourseEntity_.courseLevel), filter.courseLevel()));
            }
            if (Objects.nonNull(filter.courseStatus())){
                predicates.add(criteriaBuilder.equal(root.get(CourseEntity_.courseStatus), filter.courseStatus()));
            }
            if (StringUtils.isNotBlank(filter.name())){
                predicates.add(criteriaBuilder.like(root.get(CourseEntity_.name), filter.likeName()));
            }
            if (Objects.nonNull(userId)){
                query.distinct(true);
                var user = query.from(UserEntity.class);
                var usersCourses = user.get(UserEntity_.courses);
                predicates.add(criteriaBuilder.and(criteriaBuilder.equal(user.get(UserEntity_.id), userId), criteriaBuilder.isMember(root, usersCourses)));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

}
