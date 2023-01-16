package com.ead.course.core.service;

import com.ead.course.core.domain.CourseDomain;
import com.ead.course.core.exception.UserBlockedException;
import com.ead.course.core.exception.UserSubscriptionAlreadyExistsException;
import com.ead.course.core.port.persistence.CoursePersistencePort;
import com.ead.course.core.port.service.CourseServicePort;
import com.ead.course.core.port.publisher.NotificationCommandPublisherPort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import com.ead.course.core.port.service.query.UserQueryServicePort;
import com.ead.course.adapter.domain.NotificationCommandDTO;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;

import java.util.UUID;

@Log4j2
@AllArgsConstructor
public class CourseServicePortImpl implements CourseServicePort {

    private final CoursePersistencePort coursePersistencePort;
    private final CourseQueryServicePort courseQueryServicePort;
    private final UserQueryServicePort userQueryServicePort;
    private final NotificationCommandPublisherPort notificationCommandPublisherPort;

    @Override
    public void delete(final UUID id) {
        courseQueryServicePort.findById(id);
        coursePersistencePort.delete(id);
    }

    @Override
    public CourseDomain save(final CourseDomain domain) {
        var userDomain = userQueryServicePort.findById(domain.userInstructor());
        if (userDomain.isStudent()){
            throw new AccessDeniedException("User must be INSTRUCTOR or ADMIN");
        }
        return coursePersistencePort.save(domain);
    }

    @Override
    public CourseDomain update(final CourseDomain domain) {
        var domainToUpdate = courseQueryServicePort.findById(domain.id());
        domainToUpdate = domainToUpdate.toBuilder()
                .name(domain.name())
                .description(domain.description())
                .imageUrl(domain.imageUrl())
                .courseStatus(domain.courseStatus())
                .courseLevel(domain.courseLevel())
                .build();
        coursePersistencePort.update(domainToUpdate);
        return domainToUpdate;
    }

    @Override
    public void saveSubscriptionUserInCourse(final UUID id, final UUID userId) {
        var model = courseQueryServicePort.findById(id);
        if(courseQueryServicePort.existsByCourseAndUser(id, userId)){
            throw new UserSubscriptionAlreadyExistsException("Error: subscription already exists!");
        }
        var userModel = userQueryServicePort.findById(userId);
        if(userModel.isBlocked()){
            throw new UserBlockedException("User is blocked");
        }
        coursePersistencePort.saveSubscriptionUserInCourse(model.id(), userModel.id());
        try {
            var dto = NotificationCommandDTO.builder()
                    .title("Ben-vindo(a) ao curso: " + model.name())
                    .message(userModel.fullName() + " a sua inscrição foi realizada com sucesso!")
                    .userId(userModel.id())
                    .build();
            notificationCommandPublisherPort.publishNotificationCommand(dto);
        }catch (Exception e){
            log.warn("Error sending notification!");
        }
    }

}
