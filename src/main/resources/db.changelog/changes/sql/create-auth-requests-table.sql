create table if not exists auth_requests
(
    id         bigserial not null,
    code       integer   not null,
    created_at timestamp,
    attempts   integer,
    phone      varchar(255)
);

alter table auth_requests
    owner to postgres;

create unique index if not exists auth_requests_id_uindex
    on auth_requests (id);