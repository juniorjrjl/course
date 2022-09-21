package com.ead.course.service;

import com.ead.course.repository.CourseUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseUserServiceImpl implements CourseUserService{

    private final CourseUserRepository courseUserRepository;

}
