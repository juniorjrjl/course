package com.ead.course.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
public class UserCourseDTO {

    private UUID courseId;

}