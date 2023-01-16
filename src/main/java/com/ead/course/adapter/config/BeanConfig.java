package com.ead.course.adapter.config;


import com.ead.course.CourseApplication;
import com.ead.course.adapter.service.decorator.CourseServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.LessonServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.ModuleServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.UserServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.query.CourseQueryServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.query.LessonQueryServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.query.ModuleQueryServicePortImplDecorator;
import com.ead.course.adapter.service.decorator.query.UserQueryServicePortImplDecorator;
import com.ead.course.core.port.persistence.CoursePersistencePort;
import com.ead.course.core.port.persistence.LessonPersistencePort;
import com.ead.course.core.port.persistence.ModulePersistencePort;
import com.ead.course.core.port.persistence.UserPersistencePort;
import com.ead.course.core.port.publisher.NotificationCommandPublisherPort;
import com.ead.course.core.port.service.CourseServicePort;
import com.ead.course.core.port.service.LessonServicePort;
import com.ead.course.core.port.service.ModuleServicePort;
import com.ead.course.core.port.service.UserServicePort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import com.ead.course.core.port.service.query.LessonQueryServicePort;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import com.ead.course.core.port.service.query.UserQueryServicePort;
import com.ead.course.core.service.CourseServicePortImpl;
import com.ead.course.core.service.LessonServicePortImpl;
import com.ead.course.core.service.ModuleServicePortImpl;
import com.ead.course.core.service.UserServicePortImpl;
import com.ead.course.core.service.query.CourseQueryServicePortImpl;
import com.ead.course.core.service.query.LessonQueryServicePortImpl;
import com.ead.course.core.service.query.ModuleQueryServicePortImpl;
import com.ead.course.core.service.query.UserQueryServicePortImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
@ComponentScan(basePackageClasses = CourseApplication.class)
public class BeanConfig {

    @Bean
    @Primary
    public CourseQueryServicePort courseQueryServicePort(final CoursePersistencePort persistence){
        var courseQueryServicePort = new CourseQueryServicePortImpl(persistence);
        return new CourseQueryServicePortImplDecorator(courseQueryServicePort);
    }

    @Bean
    @Primary
    public CourseServicePort courseServicePort(final CoursePersistencePort persistence, final CourseQueryServicePort courseQueryServicePort,
                                               final UserQueryServicePort userQueryServicePort, final NotificationCommandPublisherPort notificationCommandPublisherPort){
        var courseServicePort = new CourseServicePortImpl(persistence, courseQueryServicePort, userQueryServicePort, notificationCommandPublisherPort);
        return new CourseServicePortImplDecorator(courseServicePort);
    }

    @Bean
    @Primary
    public LessonQueryServicePort lessonQueryServicePort(final LessonPersistencePort persistence){
        var lessonQueryServicePort = new LessonQueryServicePortImpl(persistence);
        return new LessonQueryServicePortImplDecorator(lessonQueryServicePort);
    }

    @Bean
    @Primary
    public LessonServicePort lessonServicePort(final LessonPersistencePort persistence, final LessonQueryServicePort lessonQueryServicePort,
                                               final ModuleQueryServicePort moduleQueryServicePort){
        var lessonServicePort = new LessonServicePortImpl(persistence, lessonQueryServicePort, moduleQueryServicePort);
        return new LessonServicePortImplDecorator(lessonServicePort);
    }

    @Bean
    @Primary
    public ModuleQueryServicePort moduleQueryServicePort(final ModulePersistencePort persistence){
        var moduleQueryServicePort = new ModuleQueryServicePortImpl(persistence);
        return new ModuleQueryServicePortImplDecorator(moduleQueryServicePort);
    }

    @Bean
    @Primary
    public ModuleServicePort moduleServicePort(final ModulePersistencePort persistence, final ModuleQueryServicePort moduleQueryServicePort,
                                               final CourseQueryServicePort courseQueryServicePort){
        var moduleServicePort = new ModuleServicePortImpl(persistence, moduleQueryServicePort, courseQueryServicePort);
        return new ModuleServicePortImplDecorator(moduleServicePort);
    }

    @Bean
    @Primary
    public UserQueryServicePort userQueryServicePort(final UserPersistencePort persistence){
        var userQueryService = new UserQueryServicePortImpl(persistence);
        return new UserQueryServicePortImplDecorator(userQueryService);
    }

    @Bean
    @Primary
    public UserServicePort userServicePort(final UserPersistencePort persistence){
        var userService = new UserServicePortImpl(persistence);
        return new UserServicePortImplDecorator(userService);
    }

}
