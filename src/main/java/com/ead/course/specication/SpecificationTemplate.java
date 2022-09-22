package com.ead.course.specication;

import com.ead.course.model.CourseModel;
import com.ead.course.model.CourseModel_;
import com.ead.course.model.CourseUserModel_;
import com.ead.course.model.LessonModel;
import com.ead.course.model.LessonModel_;
import com.ead.course.model.ModuleModel;
import com.ead.course.model.ModuleModel_;
import net.kaczmarzyk.spring.data.jpa.domain.Equal;
import net.kaczmarzyk.spring.data.jpa.domain.Like;
import net.kaczmarzyk.spring.data.jpa.web.annotation.And;
import net.kaczmarzyk.spring.data.jpa.web.annotation.Spec;
import org.springframework.data.jpa.domain.Specification;

import java.util.UUID;

public class SpecificationTemplate {

    @And({
            @Spec(path = CourseModel_.COURSE_LEVEL, spec = Equal.class),
            @Spec(path = CourseModel_.COURSE_STATUS, spec = Equal.class),
            @Spec(path = CourseModel_.NAME, spec = Like.class)
    })
    public interface CourseSpec extends Specification<CourseModel> {}

    @Spec(path = ModuleModel_.TITLE, spec = Like.class)
    public interface ModuleSpec extends Specification<ModuleModel> {}

    @Spec(path = LessonModel_.TITLE, spec = Like.class)
    public interface LessonSpec extends Specification<LessonModel> {}

    public static Specification<ModuleModel> moduleCourseId(final UUID courseId){
        return (root, query, criteriaBuilder) -> {
          query.distinct(true);
          var course = query.from(CourseModel.class);
          var courseModules = course.get(CourseModel_.modules);
          return criteriaBuilder.and(criteriaBuilder.equal(course.get(CourseModel_.id), courseId), criteriaBuilder.isMember(root, courseModules));
        };
    }

    public static Specification<LessonModel> lessonModuleId(final UUID moduleId){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            var module = query.from(ModuleModel.class);
            var moduleLessons = module.get(ModuleModel_.lessons);
            return criteriaBuilder.and(criteriaBuilder.equal(module.get(ModuleModel_.id), moduleId), criteriaBuilder.isMember(root, moduleLessons));
        };
    }

    public static Specification<CourseModel> courseUserId(final UUID userId){
        return (root, query, criteriaBuilder) -> {
            query.distinct(true);
            var  courseProd = root.join(CourseModel_.coursesUsers);
            return criteriaBuilder.equal(courseProd.get(CourseUserModel_.userId), userId);
        };
    }

}
