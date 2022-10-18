package com.ead.course.service.impl;

import com.ead.course.dto.NotificationCommandDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.model.UserModel;
import com.ead.course.publisher.NotificationCommandPublisher;
import com.ead.course.repository.CourseRepository;
import com.ead.course.repository.LessonRepository;
import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.CourseService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
@Log4j2
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final ModuleRepository moduleRepository;
    private final LessonRepository lessonRepository;
    private final NotificationCommandPublisher notificationCommandPublisher;

    @Transactional
    @Override
    public void delete(final CourseModel model) {
        var modules = moduleRepository.findAllModulesIntoCourse(model.getId());
        if (CollectionUtils.isNotEmpty(modules)){
            for(ModuleModel module: modules){
                var lessons = lessonRepository.findAllLessonsIntoModule(module.getId());
                if (CollectionUtils.isNotEmpty(lessons)){
                    lessonRepository.deleteAll(lessons);
                }
            }
            moduleRepository.deleteAll(modules);
        }
        courseRepository.deleteCourseUserByCourse(model.getId());
        courseRepository.delete(model);
    }

    @Override
    public CourseModel save(final CourseModel model) {
        return courseRepository.save(model);
    }

    @Override
    public Optional<CourseModel> findById(final UUID id) {
        return courseRepository.findById(id);
    }

    @Override
    public Page<CourseModel> findAll(final Specification<CourseModel> spec, final Pageable pageable) {
        return courseRepository.findAll(spec, pageable);
    }

    @Override
    public boolean existsByCourseAndUser(final UUID courseId, final UUID userId) {
        return courseRepository.existsByCourseAndUser(courseId, userId);
    }

    @Transactional
    @Override
    public void saveSubscriptionUserInCourse(final CourseModel courseModel, final UserModel userModel) {
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
