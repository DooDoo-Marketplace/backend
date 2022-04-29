create table if not exists orders_status
(
    id  int  primary key,
    name    varchar(255)    not null    unique
    );

insert into orders_status (id, name) values (1, 'WAITING_PAYMENT');
insert into orders_status (id, name) values (2, 'PAID');
insert into orders_status (id, name) values (3, 'TAKEN_FROM_WAREHOUSE');
insert into orders_status (id, name) values (4, 'TRANSPORT');
insert into orders_status (id, name) values (5, 'COMPLETED');
insert into orders_status (id, name) values (6, 'CANCELLED');