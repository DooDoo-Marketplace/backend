create table if not exists reviews
(
    id uuid  primary key default gen_random_uuid(),
    user_id bigint not null
        constraint review_users_id_fk references users,
    sku_id bigint not null
        constraint review_sku_id_fk references sku,
    text varchar not null ,
    photo_url varchar(255),
    rating float not null ,
    created_at timestamp not null,
    check (rating >= 0 and rating <= 5)
);