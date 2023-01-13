package com.ead.course.controller;

import com.ead.course.dto.SubscriptionDTO;
import com.ead.course.model.UserModel;
import com.ead.course.service.CourseQueryService;
import com.ead.course.service.CourseService;
import com.ead.course.service.UserQueryService;
import com.ead.course.specication.SpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.ead.course.specication.SpecificationTemplate.userCourseId;
import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseUserController {

    private final CourseService courseService;
    private final CourseQueryService courseQueryService;
    private final UserQueryService userQueryService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("courses/{courseId}/users")
    public Page<UserModel> getAllUsersByCourse(final SpecificationTemplate.UserSpec spec,
                                               @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                               @PathVariable final UUID courseId){
        courseQueryService.findById(courseId);
        return userQueryService.findAll(userCourseId(courseId).and(spec), pageable);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("courses/{courseId}/users/subscription")
    public ResponseEntity<Object> subscribeUser(@PathVariable final UUID courseId,
                                                @RequestBody @Valid final SubscriptionDTO request){
        courseService.saveSubscriptionUserInCourse(courseId, request.getUserId());
        return ResponseEntity.status(CREATED).body("Subscription created successfully");
    }


}
