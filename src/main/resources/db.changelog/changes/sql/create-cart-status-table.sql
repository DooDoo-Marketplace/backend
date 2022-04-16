create table if not exists cart_status
(
    id  int  primary key,
    name    varchar(255)    not null    unique
);

insert into cart_status (id, name) values (1, 'ACTIVE');
insert into cart_status (id, name) values (2, 'DELETED');
insert into cart_status (id, name) values (3, 'IN_GROUP');


