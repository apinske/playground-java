--liquibase formatted sql

--changeset apinske:thing-data
alter table thing 
    add column data blob(104857600)
;
