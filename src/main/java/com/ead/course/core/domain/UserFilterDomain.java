package com.ead.course.core.domain;

import com.ead.course.core.domain.enumeration.UserStatus;
import com.ead.course.core.domain.enumeration.UserType;
import lombok.Builder;

public record UserFilterDomain(UserType userType,
                               UserStatus userStatus,
                               String email,
                               String fullName) {

    @Builder(toBuilder = true)
    public UserFilterDomain {

    }

}