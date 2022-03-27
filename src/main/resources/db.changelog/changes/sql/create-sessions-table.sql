create table if not exists sessions
(
    id         serial       not null
        constraint sessions_pkey
        primary key,
    created_at timestamp    not null,
    expired    boolean      not null,
    token      varchar(255) not null,
    user_id    integer
        constraint fkruie73rneumyyd1bgo6qw8vjt
        references users
);