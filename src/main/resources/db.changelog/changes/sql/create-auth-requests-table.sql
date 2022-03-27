create table if not exists auth_requests
(
    id         serial    not null
        constraint auth_requests_pkey
        primary key,
    attempts   integer,
    code       integer   not null,
    created_at timestamp not null,
    phone      varchar(255) not null
);