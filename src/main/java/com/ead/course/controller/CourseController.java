package com.ead.course.controller;

import com.ead.course.dto.CourseDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.service.CourseService;
import com.ead.course.specication.SpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.ead.course.specication.SpecificationTemplate.courseUserId;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid final CourseDTO request){
        log.debug("[POST] [save] courseDto received {}", request);
        var model = new CourseModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setLastUpdateDate(OffsetDateTime.now());
        model = courseService.save(model);
        log.debug("[POST] [register] courseModel saved {}", model);
        log.info("[POST] [register] course saved successfully id {}", model.getId());
        return ResponseEntity.status(CREATED).body(model);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID id) {
        log.debug("[DELETE] [delete] id received {}", id);
        var modelOptional = courseService.findById(id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        courseService.delete(modelOptional.get());
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] Course with id {} deleted successfully", id);
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID id, @RequestBody @Valid final CourseDTO request) {
        log.debug("[PUT] [update] courseDto received {}", request);
        var modelOptional = courseService.findById(id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        var model = modelOptional.get();
        model.setName(request.getName());
        model.setDescription(request.getDescription());
        model.setImageUrl(request.getImageUrl());
        model.setCourseStatus(request.getCourseStatus());
        model.setCourseLevel(request.getCourseLevel());
        model.setLastUpdateDate(OffsetDateTime.now());
        model = courseService.save(model);
        log.debug("[PUT] [update] courseModel saved {}", model);
        log.info("[PUT] [update] course updated successfully id {}", model.getId());
        return ResponseEntity.status(OK).body(model);
    }

    @GetMapping
    public ResponseEntity<Page<CourseModel>> findAll(final SpecificationTemplate.CourseSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable,
                                                     @RequestParam(required = false) final UUID userId){
        log.debug("[GET] [findAll] find courses with spec {} and page {}", spec, pageable);
        var page = courseService.findAll(Objects.nonNull(userId) ? courseUserId(userId).and(spec) : spec, pageable);
        log.debug("[GET] [findAll] courses founded {}", page);
        log.info("[GET] [findAll] courses founded {}", page);
        return ResponseEntity.status(OK).body(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable final UUID id) {
        log.debug("[GET] [getOne] id received {}", id);
        var modelOptional = courseService.findById(id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        var courseModel = modelOptional.get();
        log.debug("[GET] [getOne] user founded {}", courseModel);
        log.info("[GET] [getOne] User with id {} founded {}", id, courseModel);
        return ResponseEntity.status(OK).body(courseModel);
    }

}
