create table if not exists roles
(
    id   bigserial    not null
        constraint role_pk
            primary key,
    name varchar(255) not null
);

alter table roles
    owner to postgres;

create unique index if not exists role_id_uindex
    on roles (id);