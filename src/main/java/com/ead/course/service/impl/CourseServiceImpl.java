package com.ead.course.service.impl;

import com.ead.course.dto.NotificationCommandDTO;
import com.ead.course.exception.UserBlockedException;
import com.ead.course.exception.UserSubscriptionAlreadyExistsException;
import com.ead.course.model.CourseModel;
import com.ead.course.publisher.NotificationCommandPublisher;
import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseQueryService;
import com.ead.course.service.CourseService;
import com.ead.course.service.UserQueryService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseQueryService courseQueryService;
    private final UserQueryService userQueryService;
    //private final ModuleRepository moduleRepository;
    //private final LessonRepository lessonRepository;
    private final NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void delete(final UUID id) {
        var model = courseQueryService.findById(id);
        /*var modules = moduleRepository.findAllModulesIntoCourse(model.getId());
        if (CollectionUtils.isNotEmpty(modules)){
            for(ModuleModel module: modules){
                var lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
                if (CollectionUtils.isNotEmpty(lessons)){
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.deleteCourseUserByCourse(model.getId());*/
        courseRepository.delete(model);
    }

    @Override
    public CourseModel save(final CourseModel model) {
        var userModel = userQueryService.findById(model.getUserInstructor());
        if (userModel.isStudent()){
            throw new AccessDeniedException("User must be INSTRUCTOR or ADMIN");
        }
        return courseRepository.save(model);
    }

    @Override
    public CourseModel update(final UUID id, final CourseModel model) {
        var modelToUpdate = courseQueryService.findById(id);
        modelToUpdate.setName(model.getName());
        modelToUpdate.setDescription(model.getDescription());
        modelToUpdate.setImageUrl(model.getImageUrl());
        modelToUpdate.setCourseStatus(model.getCourseStatus());
        modelToUpdate.setCourseLevel(model.getCourseLevel());
        return save(modelToUpdate);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(final UUID id, final UUID userId) {
        var courseModel = courseQueryService.findById(id);
        if(courseQueryService.existsByCourseAndUser(id, userId)){
            throw new UserSubscriptionAlreadyExistsException("Error: subscription already exists!");
        }
        var userModel = userQueryService.findById(userId);
        if(userModel.isBlocked()){
            throw new UserBlockedException("User is blocked");
        }
        courseRepository.saveSubscriptionUserInCourse(courseModel.getId(), userModel.getId());
        try {
            var dto = NotificationCommandDTO.builder()
                    .title("Ben-vindo(a) ao curso: " + courseModel.getName())
                    .message(userModel.getFullName() + " a sua inscrição foi realizada com sucesso!")
                    .userId(userModel.getId())
                    .build();
            notificationCommandPublisher.publishNotificationCommand(dto);
        }catch (Exception e){
            log.warn("Error sending notification!");
        }

    }
}
