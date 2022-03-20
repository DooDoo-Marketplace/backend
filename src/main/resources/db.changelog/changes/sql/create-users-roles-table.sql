create table if not exists users_roles
(
    id       bigserial not null
        constraint user_roles_pk
            primary key,
    user_id  bigint    not null
        constraint user_roles_users_id_fk
            references users,
    roles_id bigint    not null
        constraint user_roles_roles_id_fk
            references roles
        constraint user_roles_roles_id_fk_
            references roles
);

alter table users_roles
    owner to postgres;

create unique index user_roles_id_uindex
    on users_roles (id);
