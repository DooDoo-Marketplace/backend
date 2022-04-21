create table if not exists dictionary_skus(
    dictionary_id   bigserial   not null,
    sku_id  bigserial   not null,
    primary key (dictionary_id, sku_id),
    constraint fk_dictionary foreign key (dictionary_id) references dictionary(id),
    constraint fk_sku foreign key (sku_id) references sku(id)
)