package com.ead.course.dto;

import com.ead.course.model.UserModel;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import org.springframework.beans.BeanUtils;

import java.util.UUID;

public record UserEventDTO(@JsonProperty("id")
                           UUID id,
                           @JsonProperty("username")
                           String username,
                           @JsonProperty("email")
                           String email,
                           @JsonProperty("fullName")
                           String fullName,
                           @JsonProperty("userState")
                           String userStatus,
                           @JsonProperty("userType")
                           String userType,
                           @JsonProperty("phoneNumber")
                           String phoneNumber,
                           @JsonProperty("cpf")
                           String cpf,
                           @JsonProperty("imageUrl")
                           String imageUrl,
                           @JsonProperty("actionType")
                           String actionType) {

    @Builder(toBuilder = true)
    public UserEventDTO {}

    public UserModel toModel(){
        var model = new UserModel();
        BeanUtils.copyProperties(this, model);
        return model;
    }
}