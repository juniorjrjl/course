package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.config.security.AuthenticationCurrentUserService;
import com.ead.course.adapter.dto.CourseDTO;
import com.ead.course.adapter.dto.CourseFilterDTO;
import com.ead.course.adapter.mapper.CourseMapper;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.CourseServicePort;
import com.ead.course.core.port.service.query.CourseQueryServicePort;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseController {

    private final CourseServicePort courseServicePort;
    private final CourseQueryServicePort courseQueryServicePort;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;
    private final CourseMapper courseMapper;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public CourseEntity save(@RequestBody @Valid final CourseDTO request){
        log.debug("[POST] [save] courseDto received {}", request);
        var entity = new CourseEntity();
        BeanUtils.copyProperties(request, entity);
        var currentUserId = authenticationCurrentUserService.getCurrentUser().id();
        if (ObjectUtils.notEqual(currentUserId, request.userInstructor())){
            throw new AccessDeniedException("Forbidden");
        }
        var domain = courseServicePort.save(courseMapper.toDomain(entity));
        log.debug("[POST] [register] courseModel saved {}", domain);
        log.info("[POST] [register] course saved successfully id {}", domain.id());
        return courseMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID id) {
        log.debug("[DELETE] [delete] id received {}", id);
        courseServicePort.delete(id);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] Course with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public CourseEntity update(@PathVariable final UUID id, @RequestBody @Valid final CourseDTO request) {
        log.debug("[PUT] [update] courseDto received {}", request);
        var entity = new CourseEntity();
        entity.setId(id);
        entity.setName(request.name());
        entity.setDescription(request.description());
        entity.setImageUrl(request.imageUrl());
        entity.setCourseStatus(request.courseStatus());
        entity.setCourseLevel(request.courseLevel());
        var domain = courseServicePort.update(courseMapper.toDomain(entity));
        log.debug("[PUT] [update] courseModel saved {}", domain);
        log.info("[PUT] [update] course updated successfully id {}", domain.id());
        return courseMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<CourseEntity> findAll(final CourseFilterDTO filterParams,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                      @RequestParam(required = false) final UUID userId){
        log.debug("[GET] [findAll] find courses with spec {} and page {}", filterParams, pageable);
        var pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());
        var courses = courseQueryServicePort.findAll(courseMapper.toDomain(filterParams, userId), pageInfo);
        log.debug("[GET] [findAll] courses founded {}", courses);
        log.info("[GET] [findAll] courses founded {}", courses);
        return new PageImpl<>(courses.stream().map(courseMapper::toEntity).collect(Collectors.toList()), pageable, courses.size());
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public CourseEntity findById(@PathVariable final UUID id) {
        log.debug("[GET] [getOne] id received {}", id);
        var domain = courseQueryServicePort.findById(id);
        log.debug("[GET] [getOne] user founded {}", domain);
        log.info("[GET] [getOne] User with id {} founded {}", id, domain);
        return courseMapper.toEntity(domain);
    }

}
