package com.ead.course.service.impl;

import com.ead.course.repository.LessonRepository;
import com.ead.course.service.LessonService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;

}
