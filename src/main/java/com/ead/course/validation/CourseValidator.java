package com.ead.course.validation;

import com.ead.course.config.security.AuthenticationCurrentUserService;
import com.ead.course.dto.CourseDTO;
import com.ead.course.service.UserService;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.util.UUID;

@Component
public class CourseValidator implements Validator {

    private final Validator validator;
    private final UserService userService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    public CourseValidator(@Qualifier("defaultValidator") final Validator validator,
                           final UserService userService,
                           final AuthenticationCurrentUserService authenticationCurrentUserService) {
        this.validator = validator;
        this.userService = userService;
        this.authenticationCurrentUserService = authenticationCurrentUserService;
    }

    @Override
    public boolean supports(final Class<?> clazz) {
        return false;
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        var courseDTO = (CourseDTO) target;
        validator.validate(courseDTO, errors);
        if (!errors.hasErrors()){
            validateUserInstructor(courseDTO.getUserInstructor(), errors);
        }
    }

    private void validateUserInstructor(final UUID userInstructor, final Errors errors){
        var currentUserId = authenticationCurrentUserService.getCurrentUser().id();
        var modelOptional = userService.findById(userInstructor);
        if (ObjectUtils.notEqual(currentUserId, userInstructor)){
            throw new AccessDeniedException("Forbidden");
        }
        modelOptional.ifPresentOrElse(model -> {
            if (model.isStudent()){
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
            }},
                () -> errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found")
        );

    }

}
