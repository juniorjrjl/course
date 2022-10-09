--liquibase formatted sql
--changeset junior:202210071204
--comment: user course relation table drop

DROP TABLE TB_COURSES_USERS;

--rollback CREATE TABLE TB_COURSES_USERS( id uuid not null primary key, user_id uuid not null, course_id uuid not null, CONSTRAINT FK_COURSES_USERS_USERS FOREIGN KEY(course_id) REFERENCES TB_COURSES(id) );
