create table if not exists auth_requests
(
    id         bigserial not null,
    code       integer   not null,
    created_at timestamp,
    attempts   integer,
    phone      varchar(255)
);


