package com.ead.course.adapter.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Fetch;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;
import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.AUTO;
import static org.hibernate.annotations.FetchMode.SUBSELECT;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Entity
@Table(name = "TB_MODULES")
public class ModuleEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STR_ISO8601)
    private OffsetDateTime creationDate;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = LAZY)
    private CourseEntity course;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @OneToMany(mappedBy = "module", cascade = ALL, orphanRemoval = true)
    @Fetch(SUBSELECT)
    private Set<LessonEntity> lessons;

    @PrePersist
    public void beforeInsert(){
        this.creationDate = OffsetDateTime.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ModuleEntity that = (ModuleEntity) o;
        return id.equals(that.id) && title.equals(that.title) &&
                description.equals(that.description) &&
                creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, creationDate);
    }
}
