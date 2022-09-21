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