package com.ead.course.adapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotBlank;

public record ModuleDTO(
        @NotBlank
        @JsonProperty("title")
        String title,
        @NotBlank
        @JsonProperty("description")
        String description){

}
