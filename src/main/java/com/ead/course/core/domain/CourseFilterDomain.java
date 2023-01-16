package com.ead.course.core.domain;

import com.ead.course.core.domain.enumeration.CourseLevel;
import com.ead.course.core.domain.enumeration.CourseStatus;

import java.util.UUID;

public record CourseFilterDomain(String name, CourseLevel courseLevel, CourseStatus courseStatus, UUID userId) {


}
