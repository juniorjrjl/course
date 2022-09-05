package com.ead.course.service.impl;

import com.ead.course.repository.CourseRepository;
import com.ead.course.service.CourseService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;

}
