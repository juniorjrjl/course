package com.ead.course.adapter.outbound.persistence.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;
import static com.fasterxml.jackson.annotation.JsonProperty.Access.WRITE_ONLY;
import static com.fasterxml.jackson.databind.util.StdDateFormat.DATE_FORMAT_STR_ISO8601;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.AUTO;

@Getter
@Setter
@ToString
@NoArgsConstructor
@JsonInclude(NON_NULL)
@Entity
@Table(name = "TB_LESSONS")
public class LessonEntity implements Serializable {

    public static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = AUTO)
    private UUID id;

    @Column(nullable = false, length = 150)
    private String title;

    @Column(nullable = false, length = 250)
    private String description;

    @Column(nullable = false)
    private String videoUrl;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = DATE_FORMAT_STR_ISO8601)
    private OffsetDateTime creationDate;

    @JsonProperty(access = WRITE_ONLY)
    @ToString.Exclude
    @ManyToOne(optional = false, fetch = LAZY)
    private ModuleEntity module;

    @PrePersist
    public void beforeInsert(){
        this.creationDate = OffsetDateTime.now();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonEntity that = (LessonEntity) o;
        return id.equals(that.id) &&
                title.equals(that.title) &&
                description.equals(that.description) &&
                videoUrl.equals(that.videoUrl) &&
                creationDate.equals(that.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, videoUrl, creationDate);
    }
}
