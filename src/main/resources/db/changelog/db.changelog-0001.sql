--liquibase formatted sql

--changeset apinske:thing-table
create table thing (
    id int generated always as identity primary key,
    name varchar(255)
);

--changeset apinske:thing-name-index
create index idx_thing_name on thing (name);
