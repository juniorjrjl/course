package com.ead.course.service.impl;

import com.ead.course.repository.ModuleRepository;
import com.ead.course.service.ModuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ModuleServiceImpl implements ModuleService {

    private final ModuleRepository moduleRepository;

}
