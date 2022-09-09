package com.ead.course.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LessonDTO {

    @NotBlank
    public String title;
    public String description;
    @NotBlank
    private String videoUrl;

}
