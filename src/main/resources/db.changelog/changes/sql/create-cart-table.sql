create table if not exists cart
(
    id bigserial  not null primary key,
    user_id bigint not null
        constraint cart_users_id_fk references users,
    sku_id bigint not null
        constraint cart_sku_id_fk references sku,
    count int not null
        check (count >= 0),
    cart_status_id int not null
        constraint cart_status_id_fk references cart_status
);


