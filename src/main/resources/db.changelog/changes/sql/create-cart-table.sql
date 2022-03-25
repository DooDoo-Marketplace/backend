create table if not exists cart
(
    id bigserial  not null primary key,
    user_id bigint not null
        references users,
    sku_id bigint not null
        references sku,
    count int not null
        check (count >= 0),
    isDeleted bool not null
        default 'false'
);

create unique index cart_id_uindex
	on cart (id);

create unique index cart_user_id_uindex
    on cart(user_id);


alter table cart
    owner to postgres;