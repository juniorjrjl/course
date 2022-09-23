package com.ead.course.controller;

import com.ead.course.CourseClient;
import com.ead.course.dto.UserDTO;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("courses/{courseId}/users")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseUserController {

    private final CourseClient courseClient;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> getAllUsersByCourse(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                                             @PathVariable final UUID courseId){
        return ResponseEntity.status(OK).body(courseClient.getAllUsersByCourse(pageable, courseId));
    }

}
