--liquibase formatted sql
--changeset junior:202210071550
--comment: user table create

CREATE TABLE TB_USERS(
                         id uuid not null primary key,
                         email varchar(50) unique not null,
                         full_name varchar(50) not null,
                         user_status varchar(20) not null,
                         user_type varchar(20) not null,
                         cpf varchar(20),
                         image_url varchar(255)
);

--rollback DROP TABLE TB_USERS;