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