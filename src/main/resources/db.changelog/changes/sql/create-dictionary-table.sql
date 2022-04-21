create table if not exists dictionary(
    id        bigserial not null
        constraint dictionary_pk
        primary key,
    word varchar(255) not null
);