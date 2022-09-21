--liquibase formatted sql
--changeset junior:202209051538
--comment: courses table create

CREATE TABLE TB_COURSES(
                           id uuid not null primary key,
                           name varchar(150) not null,
                           description varchar(250) not null,
                           image_url varchar(255),
                           creation_date timestamp not null,
                           last_update_date timestamp not null,
                           course_status varchar(11) not null,
                           course_level varchar(12) not null,
                           user_instructor uuid not null
);

--rollback DROP TABLE TB_COURSES;