package com.ead.course.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

import static com.ead.course.enumeration.UserStatus.BLOCKED;
import static com.ead.course.enumeration.UserType.STUDENT;
import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(NON_NULL)
@Entity
@Table(name = "TB_USERS")
public class UserModel implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    private UUID id;

    @Column(nullable = false, unique = true, length = 50)
    private String email;

    @Column(nullable = false, length = 150)
    private String fullName;

    @Column(nullable = false)
    private String userStatus;

    @Column(nullable = false)
    private String userType;

    @Column(length = 20)
    private String cpf;

    @Column
    private String imageUrl;

    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @ManyToMany(mappedBy = "users")
    private Set<CourseModel> courses;

    public boolean isStudent(){
        return userType.equals(STUDENT.toString());
    }

    public boolean isBlocked(){
        return userStatus.equals(BLOCKED.toString());
    }

}
