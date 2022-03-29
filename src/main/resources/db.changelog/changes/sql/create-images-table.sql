create table if not exists images
(
    id uuid not null
        default gen_random_uuid()
        primary key,
    filename varchar(256) not null,
    hashSum   varchar(256) not null,
    fileSize int not null
        check ( fileSize > 0 ),
    mimetype varchar(256) not null,
    encoding varchar(7) not null,
    extname   varchar(256) not null,
    createdAt timestamp not null
        default now(),
    updatedAt timestamp not null
        default now()
);

