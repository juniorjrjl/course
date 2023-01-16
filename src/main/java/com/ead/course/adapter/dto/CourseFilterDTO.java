package com.ead.course.adapter.dto;

import com.ead.course.core.domain.enumeration.CourseLevel;
import com.ead.course.core.domain.enumeration.CourseStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CourseFilterDTO(@JsonProperty("name")
                              String name,
                              @JsonProperty("courseLevel")
                              CourseLevel courseLevel,
                              @JsonProperty("courseStatus")
                              CourseStatus courseStatus) {

    public String likeName(){
        return String.format("%%%s%%", name);
    }

}
