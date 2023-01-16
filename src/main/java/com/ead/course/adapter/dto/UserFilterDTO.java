package com.ead.course.adapter.dto;

import com.ead.course.core.domain.enumeration.UserStatus;
import com.ead.course.core.domain.enumeration.UserType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public record UserFilterDTO(@JsonProperty("email")
                            String email,
                            @JsonProperty("fullName")
                            String fullName,
                            @JsonProperty("userStatus")
                            UserStatus userStatus,
                            @JsonProperty("userType")
                            UserType userType) {

    @Builder(toBuilder = true)
    public UserFilterDTO {}

    public String likeEmail(){
        return String.format("%%%s%%", email);
    }

    public String likeFullName(){
        return String.format("%%%s%%", fullName);
    }

}
