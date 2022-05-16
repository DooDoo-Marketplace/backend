create table if not exists images
(
    id uuid
        default gen_random_uuid()
        primary key,
    filename varchar(256) not null,
    hashSum   varchar(256) not null,
    fileSize bigint not null
        check ( fileSize > 0 and fileSize < 10000000 ),
    mimetype varchar(256) not null,
    created_at timestamp not null
        default now(),
    updated_at timestamp not null
        default now()
);

