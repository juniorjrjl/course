--liquibase formatted sql
--changeset junior:202209211346
--comment: user course relation table create

CREATE TABLE TB_COURSES_USERS(
                                 id uuid not null primary key,
                                 user_id uuid not null,
                                 course_id uuid not null,
                                 CONSTRAINT FK_COURSES_USERS_USERS FOREIGN KEY(course_id) REFERENCES TB_COURSES(id)
);

--rollback DROP TABLE TB_COURSES_USERS;