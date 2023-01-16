package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LessonFilterDTO(@JsonProperty("title")
                              String title) {

    public String likeTitle(){
        return String.format("%%%s%%", title);
    }

}
