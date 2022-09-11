package com.ead.course.controller;

import com.ead.course.dto.ModuleDTO;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
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
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("courses/{courseId}/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable final UUID courseId, @RequestBody @Valid final ModuleDTO request){
        log.debug("[POST] [save] moduleDto {} and courseId {} received", courseId,request);
        var courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        var model = new ModuleModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setCourse(courseOptional.get());
        model = moduleService.save(model);
        log.debug("[POST] [register] moduleModel saved {}", model);
        log.info("[POST] [register] module saved successfully id {}", model.getId());
        return ResponseEntity.status(CREATED).body(model);
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID courseId, @PathVariable final UUID id,
                                       @RequestBody @Valid final ModuleDTO request){
        log.debug("[PUT] [update] moduleDto {} and courseId {} received", courseId, request);
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        var model = modelOptional.get();
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        model = moduleService.save(model);
        log.debug("[PUT] [update] moduleModel saved {}", model);
        log.info("[PUT] [update] module updated successfully id {}", model.getId());
        return ResponseEntity.status(OK).body(model);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID courseId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] courseId {} and id {} received", courseId, id);
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        moduleService.delete(modelOptional.get());
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] module with id {} deleted successfully", id);
        return ResponseEntity.status(OK).build();
    }

    @GetMapping
    public ResponseEntity<Page<ModuleModel>> findAll(@PathVariable final UUID courseId, final SpecificationTemplate.ModuleSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find modules with spec {} and page {}", spec, pageable);
        var page = moduleService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        log.debug("[GET] [findAll] modules founded {}", page);
        log.info("[GET] [findAll] modules founded {}", page);
        return ResponseEntity.status(OK).body(page);
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findModuleIntoCourse(@PathVariable final UUID courseId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] courseId {} and id {} received", courseId, id);
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        var model = modelOptional.get();
        log.debug("[GET] [getOne] module founded {}", model);
        log.info("[GET] [getOne] Module with id {} founded {}", id, model);
        return ResponseEntity.status(OK).body(model);
    }

}
