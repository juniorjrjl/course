package com.ead.course.controller;

import com.ead.course.dto.ModuleDTO;
import com.ead.course.model.CourseModel;
import com.ead.course.model.ModuleModel;
import com.ead.course.service.ModuleQueryService;
import com.ead.course.service.ModuleService;
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
@RequestMapping("courses/{courseId}/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class ModuleController {

    private final ModuleService moduleService;
    private final ModuleQueryService moduleQueryService;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public ModuleModel save(@PathVariable final UUID courseId, @RequestBody @Valid final ModuleDTO request){
        log.debug("[POST] [save] moduleDto {} and courseId {} received", courseId,request);
        var model = new ModuleModel();
        BeanUtils.copyProperties(request, model);
        model.setCourse(new CourseModel());
        model.getCourse().setId(courseId);
        model = moduleService.save(model);
        log.debug("[POST] [register] moduleModel saved {}", model);
        log.info("[POST] [register] module saved successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public ModuleModel update(@PathVariable final UUID courseId, @PathVariable final UUID id,
                                       @RequestBody @Valid final ModuleDTO request){
        log.debug("[PUT] [update] moduleDto {} and courseId {} received", courseId, request);
        var model = new ModuleModel();
        model.setTitle(request.getTitle());
        model.setDescription(request.getDescription());
        model.setCourse(new CourseModel());
        model.getCourse().setId(courseId);
        model = moduleService.update(id, model);
        log.debug("[PUT] [update] moduleModel saved {}", model);
        log.info("[PUT] [update] module updated successfully id {}", model.getId());
        return model;
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID courseId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] courseId {} and id {} received", courseId, id);
        moduleService.delete(id, courseId);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] module with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<ModuleModel> findAll(@PathVariable final UUID courseId, final SpecificationTemplate.ModuleSpec spec,
                                                     @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find modules with spec {} and page {}", spec, pageable);
        var page = moduleQueryService.findAllByCourse(SpecificationTemplate.moduleCourseId(courseId).and(spec), pageable);
        log.debug("[GET] [findAll] modules founded {}", page);
        log.info("[GET] [findAll] modules founded {}", page);
        return page;
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public ModuleModel findModuleIntoCourse(@PathVariable final UUID courseId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] courseId {} and id {} received", courseId, id);
        var model = moduleQueryService.findModuleIntoCourse(courseId, id);
        log.debug("[GET] [getOne] module founded {}", model);
        log.info("[GET] [getOne] Module with id {} founded {}", id, model);
        return model;
    }

}
