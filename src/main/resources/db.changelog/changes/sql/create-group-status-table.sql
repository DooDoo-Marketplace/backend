create table if not exists group_status
(
    id  int  primary key,
    name    varchar(255)    not null    unique
);

insert into group_status (id, name) values (1, 'ACTIVE');
insert into group_status (id, name) values (2, 'EXTRA');
insert into group_status (id, name) values (3, 'CANCELED');
insert into group_status (id, name) values (4, 'COMPLETED');

alter table if exists groups
    add column if not exists group_status_id int not null
    constraint group_status_id_fk references group_status