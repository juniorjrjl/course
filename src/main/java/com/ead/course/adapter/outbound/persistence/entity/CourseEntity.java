package com.ead.course.adapter.outbound.persistence.entity;

import com.ead.course.core.domain.enumeration.CourseLevel;
import com.ead.course.core.domain.enumeration.CourseStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.EnumType.STRING;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Entity
@Table(name = "TB_COURSES")
public class CourseEntity implements Serializable {

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
    private Set<ModuleEntity> modules;

    @ToString.Exclude
    @JsonProperty(access = WRITE_ONLY)
    @ManyToMany(fetch = LAZY)
    @JoinTable(name = "TB_COURSES_USERS",
            joinColumns = @JoinColumn(name = "course_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private Set<UserEntity> users;


    @PrePersist
    public void beforeInsert(){
        this.creationDate = OffsetDateTime.now();
        this.lastUpdateDate = OffsetDateTime.now();
    }

    @PreUpdate
    public void beforeUpdate(){
        this.lastUpdateDate = OffsetDateTime.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CourseEntity that = (CourseEntity) o;
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
