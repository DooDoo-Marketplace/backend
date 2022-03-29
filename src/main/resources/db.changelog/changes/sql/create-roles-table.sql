create table if not exists roles
(
    id   bigserial    not null
        constraint role_pk
            primary key,
    name varchar(255) not null
);


insert into roles (name) VALUES
    ('USER'),
    ('ADMIN');
