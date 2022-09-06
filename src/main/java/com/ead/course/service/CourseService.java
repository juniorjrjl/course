package com.ead.course.service;

import com.ead.course.model.CourseModel;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CourseService {

    void delete(final CourseModel model);

    CourseModel save(final CourseModel model);

    Optional<CourseModel> findById(final UUID id);

    List<CourseModel> findAll();
}
