create table if not exists images
(
    id uuid not null
        default gen_random_uuid()
        primary key,
    filename varchar(256) not null,
    hashSum   varchar(256) not null,
    fileSize bigint not null
        check ( fileSize > 0 and images.fileSize < 10485760 ),
    mimetype varchar(256) not null,
--     encoding varchar(7) not null,
--     extname   varchar(256) not null,
    created_at timestamp not null
        default now(),
    updated_at timestamp not null
        default now()
);

