--liquibase formatted sql
--changeset junior:202210071741
--comment: users_courses table create

CREATE TABLE TB_COURSES_USERS(
    course_id uuid not null,
    user_id uuid not null,
    CONSTRAINT FK_COURSES_USERS_USERS FOREIGN KEY(course_id) REFERENCES TB_COURSES(id),
    CONSTRAINT FK_COURSES_USERS_COURSES FOREIGN KEY(user_id) REFERENCES TB_USERS(id),
    PRIMARY KEY (course_id, user_id)
);

--rollback DROP TABLE TB_COURSES_USERS;
