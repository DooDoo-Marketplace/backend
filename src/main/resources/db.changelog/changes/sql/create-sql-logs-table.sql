create table if not exists sql_logs
(
    id bigserial not null
        constraint sql_logs_pk primary key,
    user_id bigint not null,
    sql_request varchar(255),
    date timestamp,
    table_name varchar(255)
);

alter table sql_logs
    owner to postgres;

create unique index if not exists sql_logs_id_uindex
    on sql_logs (id);

