create table if not exists cart_status
(
    id  bigserial  primary key,
    name    varchar(255)    not null
);

insert into cart_status (name) values ('ACTIVE');
insert into cart_status (name) values ('DELETED');
insert into cart_status (name) values ('IN_GROUP');


