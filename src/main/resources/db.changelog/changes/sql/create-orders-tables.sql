create table if not exists orders
(
    id         uuid primary key default gen_random_uuid(),
    cart_id     bigint       not null
        constraint orders_cart_id_fk references cart,
    user_id bigint not null
        constraint orders_users_id_fk references users,
    orders_status_id int not null
        constraint orders_status_id_fk references orders_status,
    address varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp
);

create index if not exists user_index
    on orders(user_id);

create table if not exists groups_orders
(
    id         uuid primary key default gen_random_uuid(),
    group_id     UUID
        constraint orders_cart_id_fk references groups,
    user_id bigint not null
        constraint groups_orders_users_id_fk references users,
    cart_id     bigint       not null
        constraint groups_orders_cart_id_fk references cart,
    groups_orders_status_id int not null
        constraint orders_groups_status_id_fk references orders_status,
    address varchar(255) not null,
    created_at timestamp    not null default current_timestamp,
    updated_at timestamp    not null default current_timestamp
);

create index if not exists user_index
    on groups_orders(user_id);