create table if not exists users_favorite
(
    user_id  bigint not null
        constraint users_favorite_user_id_fk references users,
    sku_id bigint not null
        constraint users_favorite_sku_id_fk references sku,
    primary key (user_id, sku_id)
);