package com.ead.course.controller;

import com.ead.course.dto.CourseDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.service.CourseService;
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
@RequestMapping("courses")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
public class CourseController {

    private final CourseService courseService;

    @PostMapping
    public ResponseEntity<Object> save(@RequestBody @Valid final CourseDTO request){
        var model = new CourseModel();
        BeanUtils.copyProperties(request, model);
        model.setCreationDate(OffsetDateTime.now());
        model.setLastUpdateDate(OffsetDateTime.now());
        return ResponseEntity.status(CREATED).body(courseService.save(model));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Object> delete(@PathVariable final UUID id) {
        var modelOptional = courseService.findById(id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        courseService.delete(modelOptional.get());
        return ResponseEntity.status(OK).build();
    }

    @PutMapping("{id}")
    public ResponseEntity<Object> update(@PathVariable final UUID id, @RequestBody @Valid final CourseDTO request) {
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
        return ResponseEntity.status(OK).body(courseService.save(model));
    }

    @GetMapping
    public ResponseEntity<List<CourseModel>> findAll(){
        return ResponseEntity.status(OK).body(courseService.findAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<Object> findById(@PathVariable final UUID id) {
        var modelOptional = courseService.findById(id);
        if (modelOptional.isEmpty()) {
            return ResponseEntity.status(NOT_FOUND).body("Course not found");
        }
        return ResponseEntity.status(OK).body(modelOptional.get());
    }

}
