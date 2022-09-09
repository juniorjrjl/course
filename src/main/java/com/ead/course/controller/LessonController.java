package com.ead.course.controller;

import com.ead.course.dto.LessonDTO;
import com.ead.course.dto.ModuleDTO;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.LessonService;
import com.ead.course.service.ModuleService;
import com.ead.course.specication.SpecificationTemplate;
import lombok.AllArgsConstructor;
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
import java.util.List;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequestMapping("modules/{moduleId}/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class LessonController {

    public final LessonService lessonService;
    private final ModuleService moduleService;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable final UUID moduleId, @RequestBody @Valid final LessonDTO request){
        var moduleOptional = moduleService.findById(moduleId);
        if (moduleOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found");
        }
        var model = new LessonModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setModule(moduleOptional.get());
        return ResponseEntity.status(CREATED).body(lessonService.save(model));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID moduleId, @PathVariable final UUID id,
                                         @RequestBody @Valid final LessonDTO request){
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module");
        }
        var model = modelOptional.get();
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        model.setVideoUrl(request.getVideoUrl());
        return ResponseEntity.status(OK).body(lessonService.save(model));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID moduleId, @PathVariable final UUID id) {
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Lesson not found for this module");
        }
        lessonService.delete(modelOptional.get());
        return ResponseEntity.status(OK).build();
    }

    @GetMapping
    public ResponseEntity<Page<LessonModel>> findAll(@PathVariable final UUID moduleId, final SpecificationTemplate.LessonSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        return ResponseEntity.status(OK).body(lessonService.findAllByLesson(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findModuleIntoCourse(@PathVariable final UUID moduleId, @PathVariable final UUID id){
        var modelOptional = lessonService.findLessonIntoModule(moduleId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        return ResponseEntity.status(OK).body(modelOptional.get());
    }

}
