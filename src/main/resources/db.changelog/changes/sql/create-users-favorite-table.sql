create table if not exists users_favorite
(
    id       bigserial not null
        constraint user_favorite_pk
            primary key,
    user_id  bigint    not null
        constraint user_favorite_users_id_fk
            references users,
    favorite_id bigint    not null
        constraint user_favorite_favorite_id_fk
            references sku
        constraint user_favorite_favorite_id_fk_
            references sku
);