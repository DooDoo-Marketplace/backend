create table if not exists users
(
    id            serial not null
    constraint users_pkey
    primary key,
    about         varchar(255),
    first_name    varchar(255),
    last_name     varchar(255),
    online_at     timestamp,
    phone         varchar(255) not null,
    photo         varchar(255),
    registered_at timestamp
);

create
unique index if not exists t_users_phone_uindex on users (phone);