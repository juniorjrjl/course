--liquibase formatted sql
--changeset junior:202209051837
--comment: add references tb_module to tb_courses

ALTER TABLE TB_MODULES
    ADD COLUMN course_id uuid not null,
      ADD CONSTRAINT FK_MODULES_COURSES FOREIGN KEY(course_id) REFERENCES TB_COURSES(id);

--rollback ALTER TABLE TB_MODULES DROP CONSTRAINT FK_MODULES_COURSES;ALTER TABLE TB_MODULES DROP COLUMN course_id;