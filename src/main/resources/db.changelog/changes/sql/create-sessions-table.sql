create table if not exists sessions
(
    id         bigserial not null
        constraint sessions_pk
            primary key,
    user_id    bigint
        constraint sessions_users_id_fk
            references users,
    token      varchar(255),
    created_at timestamp,
    expired    boolean
);
