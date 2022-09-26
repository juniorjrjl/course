package com.ead.course.dto;

import com.ead.course.enumeration.UserStatus;
import com.ead.course.enumeration.UserType;
import lombok.Data;

import java.util.UUID;

import static com.ead.course.enumeration.UserStatus.BLOCKED;
import static com.ead.course.enumeration.UserType.STUDENT;

@Data
public class UserDTO {

    private UUID id;
    private String username;
    private String email;
    private String fullname;
    private UserStatus userStatus;
    private UserType userType;
    private String phoneNumber;
    private String cpf;
    private String imageUrl;

    public boolean isBlocked(){
        return userStatus.equals(BLOCKED);
    }

    public boolean canNotCreateCourse(){
        return userType.equals(STUDENT);
    }

}
