package com.ead.course.controller;

import com.ead.course.dto.ModuleDTO;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.CourseService;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
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
@RequestMapping("courses/{courseId}/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class ModuleController {

    private final ModuleService moduleService;
    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> save(@PathVariable final UUID courseId, @RequestBody @Valid final ModuleDTO request){
        var courseOptional = courseService.findById(courseId);
        if (courseOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        var model = new ModuleModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setCourse(courseOptional.get());
        return ResponseEntity.status(CREATED).body(moduleService.save(model));
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID courseId, @PathVariable final UUID id,
                                       @RequestBody @Valid final ModuleDTO request){
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        var model = modelOptional.get();
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        return ResponseEntity.status(OK).body(moduleService.save(model));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID courseId, @PathVariable final UUID id) {
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        moduleService.delete(modelOptional.get());
        return ResponseEntity.status(OK).build();
    }

    @GetMapping
    public ResponseEntity<List<ModuleModel>> findAll(@PathVariable final UUID courseId){
        return ResponseEntity.status(OK).body(moduleService.findAllByCourse(courseId));
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findModuleIntoCourse(@PathVariable final UUID courseId, @PathVariable final UUID id){
        var modelOptional = moduleService.findModuleIntoCourse(courseId, id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Module not found for this course");
        }
        return ResponseEntity.status(OK).body(modelOptional.get());
    }

}
