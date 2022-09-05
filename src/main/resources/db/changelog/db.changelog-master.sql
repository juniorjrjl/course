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

--liquibase formatted sql
--changeset junior:202209051549
--comment: modules table create

CREATE TABLE TB_MODULES(
    id uuid not null primary key,
    title varchar(150) not null,
    description varchar(250) not null,
    creation_date timestamp not null
);

--rollback DROP TABLE TB_MODULES;

--liquibase formatted sql
--changeset junior:202209051702
--comment: modules table create

CREATE TABLE TB_LESSONS(
    id uuid not null primary key,
    title varchar(150) not null,
    description varchar(250) not null,
    video_url varchar(255) not null,
    creation_date timestamp not null
);

--rollback DROP TABLE TB_LESSONS;

--liquibase formatted sql
--changeset junior:202209051837
--comment: add references tb_module to tb_courses

ALTER TABLE TB_MODULES
      ADD COLUMN course_id uuid not null,
      ADD CONSTRAINT FK_MODULES_COURSES FOREIGN KEY(course_id) REFERENCES TB_COURSES(id);

--rollback ALTER TABLE TB_MODULES DROP CONSTRAINT FK_MODULES_COURSES;ALTER TABLE TB_MODULES DROP COLUMN course_id;

--liquibase formatted sql
--changeset junior:202209051838
--comment: add references tb_lessons to tb_module

ALTER TABLE TB_LESSONS
      ADD COLUMN module_id uuid not null,
      ADD CONSTRAINT FK_LESSONS_MODULES FOREIGN KEY(module_id) REFERENCES TB_MODULES(id);

--rollback ALTER TABLE TB_LESSONS DROP CONSTRAINT FK_LESSONS_MODULES;ALTER TABLE TB_LESSONS DROP COLUMN module_id;