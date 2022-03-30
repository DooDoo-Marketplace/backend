create table if not exists sku
(
    id bigserial not null
        constraint sku_pk
            primary key,
    name varchar(255) not null ,
    description varchar(255) ,
    region varchar(255) not null ,
    count int not null,
    weight float not null,
    retail_price float,
    group_price float not null,
    percent float not null,
    min_count int not null,
    rating float not null,
    created_at timestamp
        default now(),
    updated_at timestamp
        default now(),
    CHECK ( count >= 0 ),
    CHECK ( weight >= 0 ),
    CHECK ( retail_price >= 0 ),
    CHECK ( group_price >= 0 ),
    CHECK ( percent >= 0 ),
    CHECK ( min_count >= 0 )
);

create index name_uindex
    on sku (name);

create index retail_price_uindex
    on sku (retail_price);

create index group_price_uindex
    on sku (group_price);