create table if not exists groups
(
    id         bigserial primary key,
    sku_id     bigint       not null
        constraint group_sku_id_fk references sku,
    count      int        not null,
    region     varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    expired_at timestamp    not null default current_timestamp + interval '1 day'
);

create table if not exists groups_users
(
    group_id bigint not null
        constraint groups_users_group_id_fk references groups,
    user_id  bigint not null
        constraint groups_users_user_id_fk references users,
    primary key (group_id, user_id)
);
