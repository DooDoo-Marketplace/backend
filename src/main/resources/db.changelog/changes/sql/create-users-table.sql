create table if not exists users
(
    id            bigserial not null
        constraint user_pk
            primary key,
    photo         varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    email         varchar(255),
    registered_at timestamp,
    online_at     timestamp,
    phone         varchar(255)
);

alter table users
    owner to postgres;

create unique index if not exists t_users_phone_uindex
    on users (phone);

create unique index if not exists user_id_uindex
    on users (id);