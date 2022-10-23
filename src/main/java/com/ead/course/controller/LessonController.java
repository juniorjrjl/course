package com.ead.course.controller;

import com.ead.course.dto.LessonDTO;
import com.ead.course.model.LessonModel;
import com.ead.course.service.LessonService;
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
import org.springframework.security.access.prepost.PreAuthorize;
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
@RequestMapping("modules/{moduleId}/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class LessonController {

    public final LessonService lessonService;
    private final ModuleService moduleService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    public ResponseEntity<Object> save(@PathVariable final UUID moduleId, @RequestBody @Valid final LessonDTO request){
        log.debug("[POST] [save] lessonDto {} and moduleId {} received", moduleId, request);
        var moduleOptional = moduleService.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found");
        }
        var model = new LessonModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setModule(moduleOptional.get());
        model = lessonService.save(model);
        log.debug("[POST] [register] lessonModel saved {}", model);
        log.info("[POST] [register] lesson saved successfully id {}", model.getId());
        return ResponseEntity.status(CREATED).body(model);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID moduleId, @PathVariable final UUID id,
                                         @RequestBody @Valid final LessonDTO request){
        log.debug("[PUT] [update] lessonDto {} and moduleId {} received", moduleId, request);
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module");
        }
        var model = modelOptional.get();
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        model.setVideoUrl(request.getVideoUrl());
        model = lessonService.save(model);
        log.debug("[PUT] [update] lessonDto saved {}", model);
        log.info("[PUT] [update] lesson updated successfully id {}", model.getId());
        return ResponseEntity.status(OK).body(model);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID moduleId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] moduleId {} and id {} received", moduleId, id);
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module");
        }
        lessonService.delete(modelOptional.get());
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] lesson with id {} deleted successfully", id);
        return ResponseEntity.status(OK).build();
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public ResponseEntity<Page<LessonModel>> findAll(@PathVariable final UUID moduleId, final SpecificationTemplate.LessonSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find lessons with spec {} and page {}", spec, pageable);
        var page = lessonService.findAllByLesson(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);
        log.debug("[GET] [findAll] lessons founded {}", page);
        log.info("[GET] [findAll] lessons founded {}", page);
        return ResponseEntity.status(OK).body(page);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public ResponseEntity<Object> findModuleIntoCourse(@PathVariable final UUID moduleId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] moduleId {} and id {} received", moduleId, id);
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        var model = modelOptional.get();
        log.debug("[GET] [getOne] lesson founded {}", model);
        log.info("[GET] [getOne] lesson with id {} founded {}", id, model);
        return ResponseEntity.status(OK).body(model);
    }

}
