package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.dto.CourseUserDTO;
import com.ead.course.adapter.dto.SubscriptionDTO;
import com.ead.course.adapter.dto.UserFilterDTO;
import com.ead.course.adapter.mapper.UserMapper;
import com.ead.course.adapter.outbound.persistence.entity.UserEntity;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.CourseServicePort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import com.ead.course.core.port.service.query.UserQueryServicePort;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequestMapping
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseUserController {

    private final CourseServicePort courseServicePort;
    private final CourseQueryServicePort courseQueryServicePort;
    private final UserQueryServicePort userQueryServicePort;
    private final UserMapper userMapper;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @GetMapping("courses/{courseId}/users")
    public Page<UserEntity> getAllUsersByCourse(final UserFilterDTO filterParam,
                                                @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                                @PathVariable final UUID courseId){
        courseQueryServicePort.findById(courseId);
        var pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());
        var users = userQueryServicePort.findAll(userMapper.toDomain(filterParam), pageInfo);
        return new PageImpl<>(users.stream().map(userMapper::toEntity).collect(Collectors.toList()), pageable, users.size());
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @PostMapping("courses/{courseId}/users/subscription")
    public CourseUserDTO subscribeUser(@PathVariable final UUID courseId,
                                                @RequestBody @Valid final SubscriptionDTO request){
        courseServicePort.saveSubscriptionUserInCourse(courseId, request.userId());
        return new CourseUserDTO(courseId, request.userId());
    }


}
