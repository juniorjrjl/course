package com.ead.course.controller;

import com.ead.course.dto.LessonDTO;
import com.ead.course.model.LessonModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.LessonQueryService;
import com.ead.course.service.LessonService;
import com.ead.course.specication.SpecificationTemplate;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("modules/{moduleId}/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class LessonController {

    public final LessonService lessonService;
    private final LessonQueryService lessonQueryService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public LessonModel save(@PathVariable final UUID moduleId, @RequestBody @Valid final LessonDTO request){
        log.debug("[POST] [save] lessonDto {} and moduleId {} received", moduleId, request);
        var model = new LessonModel();
        BeanUtils.copyProperties(request, model);
        model.setModule(new ModuleModel());
        model.getModule().setId(moduleId);
        model = lessonService.save(model);
        log.debug("[POST] [register] lessonModel saved {}", model);
        log.info("[POST] [register] lesson saved successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public LessonModel update(@PathVariable final UUID moduleId, @PathVariable final UUID id,
                                         @RequestBody @Valid final LessonDTO request){
        log.debug("[PUT] [update] lessonDto {} and moduleId {} received", moduleId, request);
        var model = lessonQueryService.findLessonIntoModule(moduleId, id);
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        model.setVideoUrl(request.getVideoUrl());
        model.setModule(new ModuleModel());
        model.getModule().setId(moduleId);
        model = lessonService.update(id, model);
        log.debug("[PUT] [update] lessonDto saved {}", model);
        log.info("[PUT] [update] lesson updated successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID moduleId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] moduleId {} and id {} received", moduleId, id);
        lessonService.delete(id, moduleId);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] lesson with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<LessonModel> findAll(@PathVariable final UUID moduleId, final SpecificationTemplate.LessonSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find lessons with spec {} and page {}", spec, pageable);
        var page = lessonQueryService.findAllByLesson(SpecificationTemplate.lessonModuleId(moduleId).and(spec), pageable);
        log.debug("[GET] [findAll] lessons founded {}", page);
        log.info("[GET] [findAll] lessons founded {}", page);
        return page;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public LessonModel findModuleIntoCourse(@PathVariable final UUID moduleId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] moduleId {} and id {} received", moduleId, id);
        var model = lessonQueryService.findLessonIntoModule(moduleId, id);
        log.debug("[GET] [getOne] lesson founded {}", model);
        log.info("[GET] [getOne] lesson with id {} founded {}", id, model);
        return model;
    }

}
