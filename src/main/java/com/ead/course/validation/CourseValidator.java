package com.ead.course.validation;

import com.ead.course.client.AuthUserClient;
import com.ead.course.dto.CourseDTO;
import com.ead.course.dto.UserDTO;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.client.HttpStatusCodeException;

import java.util.UUID;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@Component
public class CourseValidator implements Validator {

    private final Validator validator;
    private final AuthUserClient authUserClient;

    public CourseValidator(@Qualifier("defaultValidator") final Validator validator,
                           final AuthUserClient authUserClient) {
        this.validator = validator;
        this.authUserClient = authUserClient;
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
        ResponseEntity<UserDTO> responseUserInstructor;
        try {
            responseUserInstructor = authUserClient.getUserById(userInstructor);
            if (responseUserInstructor.getBody().canNotCreateCourse()){
                errors.rejectValue("userInstructor", "UserInstructorError", "User must be INSTRUCTOR or ADMIN");
            }
        }catch (HttpStatusCodeException e){
            if (e.getStatusCode().equals(NOT_FOUND)){
                errors.rejectValue("userInstructor", "UserInstructorError", "Instructor not found.");
            }
        }
    }

}
