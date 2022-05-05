create table if not exists group_status
(
    id  int  primary key,
    name    varchar(255)    not null    unique
);

insert into group_status (id, name) values (1, 'ACTIVE');
insert into group_status (id, name) values (2, 'EXTRA');
insert into group_status (id, name) values (3, 'CANCELED');
insert into group_status (id, name) values (4, 'COMPLETED');
