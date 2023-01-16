package com.ead.course.core.domain;

import lombok.Builder;

import java.util.Set;
import java.util.UUID;

import static com.ead.course.core.domain.enumeration.UserStatus.BLOCKED;
import static com.ead.course.core.domain.enumeration.UserType.STUDENT;

public record UserDomain(
        UUID id,

        String email,

        String fullName,

        String userStatus,

        String userType,

        String cpf,

        String imageUrl,
        Set<UUID> coursesIds
) {

    @Builder(toBuilder = true)
    public UserDomain{}

    public boolean isStudent(){
        return userType.equals(STUDENT.toString());
    }

    public boolean isBlocked(){
        return userStatus.equals(BLOCKED.toString());
    }

}
