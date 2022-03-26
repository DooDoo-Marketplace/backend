create table if not exists cart
(
    id bigserial  not null primary key,
    user_id bigint not null
        references users,
    sku_id bigint not null
        references sku,
    count int not null
        check (count >= 0),
    deleted bool not null
        default false
);

create unique index if not exists cart_id_uindex
	on cart (id);

