package com.ead.course.controller;

import com.ead.course.client.AuthUserClient;
import com.ead.course.dto.SubscriptionDTO;
import com.ead.course.dto.UserDTO;
import com.ead.course.service.CourseService;
import com.ead.course.service.CourseUserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpStatusCodeException;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("courses/{courseId}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseUserController {

    private final AuthUserClient authUserClient;
    private final CourseService courseService;

    private final CourseUserService courseUserService;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                                             @PathVariable final UUID courseId){
        return ResponseEntity.status(OK).body(authUserClient.getAllUsersByCourse(pageable, courseId));
    }

    @PostMapping("subscription")
    public ResponseEntity<Object> subscribeUser(@PathVariable final UUID courseId,
                                                @RequestBody @Valid final SubscriptionDTO request){
        ResponseEntity<UserDTO> responseUser;
        var modelOptional = courseService.findById(courseId);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        var model = modelOptional.get();
        if (courseUserService.existsByCourseAndUserId(model, request.getUserId())){
            return ResponseEntity.status(CONFLICT).body("Error: subscription already exists");
        }
        try {
            responseUser = authUserClient.getUserById(request.getUserId());
            if (responseUser.getBody().isBlocked()){
                return ResponseEntity.status(CONFLICT).body("User is blocked");
            }
        }catch (HttpStatusCodeException e){
            if (e.getStatusCode().equals(NOT_FOUND)){
                return ResponseEntity.status(NOT_FOUND).body("User not found");
            }
        }
        var courseUser = courseUserService.saveAndSendSubscriptionUserInCourse(model.toCourseUserModel(request.getUserId()));
        return ResponseEntity.status(CREATED).body(courseUser);
    }

}
