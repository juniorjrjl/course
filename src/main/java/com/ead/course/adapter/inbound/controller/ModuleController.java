package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.dto.ModuleDTO;
import com.ead.course.adapter.dto.ModuleFilterDTO;
import com.ead.course.adapter.mapper.ModuleMapper;
import com.ead.course.adapter.outbound.persistence.entity.CourseEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.ModuleServicePort;
import com.ead.course.core.port.service.query.ModuleQueryServicePort;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("courses/{courseId}/modules")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class ModuleController {

    private final ModuleServicePort moduleServicePort;
    private final ModuleQueryServicePort moduleQueryServicePort;
    private final ModuleMapper moduleMapper;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public ModuleEntity save(@PathVariable final UUID courseId, @RequestBody @Valid final ModuleDTO request){
        log.debug("[POST] [save] moduleDto {} and courseId {} received", courseId,request);
        var entity = new ModuleEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setCourse(new CourseEntity());
        entity.getCourse().setId(courseId);
        var domain = moduleMapper.toDomain(entity);
        domain = moduleServicePort.save(domain);
        log.debug("[POST] [register] moduleModel saved {}", domain);
        log.info("[POST] [register] module saved successfully id {}", domain.id());
        return moduleMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public ModuleEntity update(@PathVariable final UUID courseId, @PathVariable final UUID id,
                               @RequestBody @Valid final ModuleDTO request){
        log.debug("[PUT] [update] moduleDto {} and courseId {} received", courseId, request);
        var entity = new ModuleEntity();
        entity.setId(id);
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setCourse(new CourseEntity());
        entity.getCourse().setId(courseId);
        var domain = moduleServicePort.update(moduleMapper.toDomain(entity));
        log.debug("[PUT] [update] moduleModel saved {}", domain);
        log.info("[PUT] [update] module updated successfully id {}", domain.id());
        return moduleMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID courseId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] courseId {} and id {} received", courseId, id);
        moduleServicePort.delete(id, courseId);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] module with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<ModuleEntity> findAll(@PathVariable final UUID courseId, final ModuleFilterDTO filterParams,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find modules with spec {} and page {}", filterParams, pageable);
        var pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());
        var modules = moduleQueryServicePort.findAllByCourse(moduleMapper.toDomain(filterParams, courseId), pageInfo);
        log.debug("[GET] [findAll] modules founded {}", modules);
        log.info("[GET] [findAll] modules founded {}", modules);
        return new PageImpl<>(modules.stream().map(moduleMapper::toEntity).collect(Collectors.toList()), pageable, modules.size());
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public ModuleEntity findModuleIntoCourse(@PathVariable final UUID courseId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] courseId {} and id {} received", courseId, id);
        var domain = moduleQueryServicePort.findModuleIntoCourse(courseId, id);
        log.debug("[GET] [getOne] module founded {}", domain);
        log.info("[GET] [getOne] Module with id {} founded {}", id, domain);
        return moduleMapper.toEntity(domain);
    }

}
