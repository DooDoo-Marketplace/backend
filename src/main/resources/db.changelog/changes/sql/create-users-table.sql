create table if not exists users
(
    id            bigserial not null
        constraint user_pk
            primary key,
    uuid          uuid not null
        default gen_random_uuid(),
    first_name    varchar(255),
    last_name     varchar(255),
    email         varchar(255),
    registered_at timestamp,
    online_at     timestamp,
    phone         varchar(255) unique
);

