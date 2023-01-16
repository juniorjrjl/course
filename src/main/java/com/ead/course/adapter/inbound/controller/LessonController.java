package com.ead.course.adapter.inbound.controller;

import com.ead.course.adapter.dto.LessonDTO;
import com.ead.course.adapter.dto.LessonFilterDTO;
import com.ead.course.adapter.mapper.LessonMapper;
import com.ead.course.adapter.outbound.persistence.entity.LessonEntity;
import com.ead.course.adapter.outbound.persistence.entity.ModuleEntity;
import com.ead.course.core.domain.PageInfo;
import com.ead.course.core.port.service.LessonServicePort;
import com.ead.course.core.port.service.query.LessonQueryServicePort;
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
@RequestMapping("modules/{moduleId}/lessons")
@CrossOrigin(origins = "*", maxAge = 3600)
@AllArgsConstructor
@Log4j2
public class LessonController {

    public final LessonServicePort lessonServicePort;
    private final LessonQueryServicePort lessonQueryServicePort;
    private final LessonMapper lessonMapper;

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PostMapping
    @ResponseStatus(CREATED)
    public LessonEntity save(@PathVariable final UUID moduleId, @RequestBody @Valid final LessonDTO request){
        log.debug("[POST] [save] lessonDto {} and moduleId {} received", moduleId, request);
        var entity = new LessonEntity();
        BeanUtils.copyProperties(request, entity);
        entity.setModule(new ModuleEntity());
        entity.getModule().setId(moduleId);
        var domain = lessonServicePort.save(lessonMapper.toDomain(entity));
        log.debug("[POST] [register] lessonModel saved {}", domain);
        log.info("[POST] [register] lesson saved successfully id {}", domain.id());
        return lessonMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @PutMapping("{id}")
    public LessonEntity update(@PathVariable final UUID moduleId, @PathVariable final UUID id,
                               @RequestBody @Valid final LessonDTO request){
        log.debug("[PUT] [update] lessonDto {} and moduleId {} received", moduleId, request);
        var entity = new LessonEntity();
        entity.setId(id);
        entity.setTitle(request.title());
        entity.setDescription(request.description());
        entity.setVideoUrl(request.videoUrl());
        entity.setModule(new ModuleEntity());
        entity.getModule().setId(moduleId);
        var domain = lessonServicePort.update(lessonMapper.toDomain(entity));
        log.debug("[PUT] [update] lessonDto saved {}", domain);
        log.info("[PUT] [update] lesson updated successfully id {}", domain.id());
        return lessonMapper.toEntity(domain);
    }

    @PreAuthorize("hasAnyRole('INSTRUCTOR')")
    @DeleteMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable final UUID moduleId, @PathVariable final UUID id) {
        log.debug("[DELETE] [delete] moduleId {} and id {} received", moduleId, id);
        lessonServicePort.delete(id, moduleId);
        log.debug("[DELETE] [delete] id deleted {}", id);
        log.info("[DELETE] [delete] lesson with id {} deleted successfully", id);
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping
    public Page<LessonEntity> findAll(@PathVariable final UUID moduleId, final LessonFilterDTO filterParams,
                                      @PageableDefault(sort = "id", direction = Sort.Direction.ASC) final Pageable pageable){
        log.debug("[GET] [findAll] find lessons with spec {} and page {}", filterParams, pageable);
        var pageInfo = new PageInfo(pageable.getPageNumber(), pageable.getPageSize());
        var lessons = lessonQueryServicePort.findAllByLesson(lessonMapper.toDomain(filterParams, moduleId), pageInfo);
        log.debug("[GET] [findAll] lessons founded {}", lessons);
        log.info("[GET] [findAll] lessons founded {}", lessons);
        return new PageImpl<>(lessons.stream().map(lessonMapper::toEntity).collect(Collectors.toList()), pageable, lessons.size());
    }

    @PreAuthorize("hasAnyRole('STUDENT')")
    @GetMapping("{id}")
    public LessonEntity findModuleIntoCourse(@PathVariable final UUID moduleId, @PathVariable final UUID id){
        log.debug("[GET] [getOne] moduleId {} and id {} received", moduleId, id);
        var domain = lessonQueryServicePort.findLessonIntoModule(moduleId, id);
        log.debug("[GET] [getOne] lesson founded {}", domain);
        log.info("[GET] [getOne] lesson with id {} founded {}", id, domain);
        return lessonMapper.toEntity(domain);
    }

}
