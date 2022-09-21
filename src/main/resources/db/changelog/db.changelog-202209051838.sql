--liquibase formatted sql
--changeset junior:202209051838
--comment: add references tb_lessons to tb_module

ALTER TABLE TB_LESSONS
    ADD COLUMN module_id uuid not null,
      ADD CONSTRAINT FK_LESSONS_MODULES FOREIGN KEY(module_id) REFERENCES TB_MODULES(id);

--rollback ALTER TABLE TB_LESSONS DROP CONSTRAINT FK_LESSONS_MODULES;ALTER TABLE TB_LESSONS DROP COLUMN module_id;