create table if not exists dictionary_skus(
    dictionary_id   bigserial   not null,
    sku_id  bigserial   not null,
    primary key (dictionary_id, sku_id),
    constraint dictionary_skus_dictionary_id_fk foreign key (dictionary_id) references dictionary(id),
    constraint dictionary_skus_sku_id_fk foreign key (sku_id) references sku(id)
)