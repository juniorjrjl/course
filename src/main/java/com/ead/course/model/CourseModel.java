package com.ead.course.model;

import com.ead.course.enumeration.CourseLevel;
import com.ead.course.enumeration.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;
import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Entity
@Table(name = "TB_COURSES")
public class CourseModel implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String name;

    @Column(nullable = false, length = 250)
    private String description;

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STR_ISO8601)
    private OffsetDateTime creationDate;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STR_ISO8601)
    private OffsetDateTime lastUpdateDate;

    @Column(nullable = false)
    @Enumerated(STRING)
    private CourseStatus courseStatus;

    @Column(nullable = false)
    @Enumerated(STRING)
    private CourseLevel courseLevel;

    @Column(nullable = false)
    private UUID userInstructor;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @OneToMany(mappedBy = "course", cascade = ALL, orphanRemoval = true)
    private Set<ModuleModel> modules;

    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @OneToMany(mappedBy = "course")
    private Set<CourseUserModel> coursesUsers;

    public CourseUserModel toCourseUserModel(final UUID userID){
        return new CourseUserModel(null, this, userID);
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseModel that = (CourseModel) o;
        return id.equals(that.id) && name.equals(that.name) && description.equals(that.description) &&
                Objects.equals(imageUrl, that.imageUrl) && creationDate.equals(that.creationDate) &&
                lastUpdateDate.equals(that.lastUpdateDate) && courseStatus == that.courseStatus &&
                courseLevel == that.courseLevel && userInstructor.equals(that.userInstructor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, imageUrl, creationDate, lastUpdateDate, courseStatus,
                courseLevel, userInstructor);
    }
}
