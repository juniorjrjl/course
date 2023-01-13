package com.ead.course.controller;

import com.ead.course.config.security.AuthenticationCurrentUserService;
import com.ead.course.dto.CourseDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.service.CourseQueryService;
import com.ead.course.service.CourseService;
import com.ead.course.specication.SpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
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
import java.util.Objects;
import java.util.UUID;

import static com.ead.course.specication.SpecificationTemplate.courseUserId;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseController {

    private final CourseService courseService;
    private final CourseQueryService courseQueryService;
    private final AuthenticationCurrentUserService authenticationCurrentUserService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public CourseModel save(@RequestBody @Valid final CourseDTO request){
        log.debug("[POST] [save] courseDto received {}", request);
        var model = new CourseModel();
        BeanUtils.copyProperties(request, model);
        var currentUserId = authenticationCurrentUserService.getCurrentUser().id();
        if (ObjectUtils.notEqual(currentUserId, request.getUserInstructor())){
            throw new AccessDeniedException("Forbidden");
        }        model = courseService.save(model);
        log.debug("[POST] [register] courseModel saved {}", model);
        log.info("[POST] [register] course saved successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID id) {
        log.debug("[DELETE] [delete] id received {}", id);
        courseService.delete(id);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] Course with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public CourseModel update(@PathVariable final UUID id, @RequestBody @Valid final CourseDTO request) {
        log.debug("[PUT] [update] courseDto received {}", request);
        var model = new CourseModel();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setImageUrl(request.getImageUrl());
        model.setCourseStatus(request.getCourseStatus());
        model.setCourseLevel(request.getCourseLevel());
        model = courseService.update(id, model);
        log.debug("[PUT] [update] courseModel saved {}", model);
        log.info("[PUT] [update] course updated successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<CourseModel> findAll(final SpecificationTemplate.CourseSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                                     @RequestParam(required = false) final UUID userId){
        log.debug("[GET] [findAll] find courses with spec {} and page {}", spec, pageable);
        var page = courseQueryService.findAll(Objects.isNull(userId) ? spec : courseUserId(userId).and(spec), pageable);
        log.debug("[GET] [findAll] courses founded {}", page);
        log.info("[GET] [findAll] courses founded {}", page);
        return page;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public CourseModel findById(@PathVariable final UUID id) {
        log.debug("[GET] [getOne] id received {}", id);
        var courseModel = courseQueryService.findById(id);
        log.debug("[GET] [getOne] user founded {}", courseModel);
        log.info("[GET] [getOne] User with id {} founded {}", id, courseModel);
        return courseModel;
    }

}
