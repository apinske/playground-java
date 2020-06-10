--liquibase formatted sql

--changeset apinske:error-table
create table error (
    id int generated always as identity primary key,
    name varchar(255)
);
