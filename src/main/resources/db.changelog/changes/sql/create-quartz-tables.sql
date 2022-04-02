create table if not exists qrtz_blob_triggers
(
    trigger_name  character varying(80) not null,
    trigger_group character varying(80) not null,
    blob_data     text,
    sched_name    character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_calendars
(
    calendar_name character varying(80) not null,
    calendar      text                  not null,
    sched_name    character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_cron_triggers
(
    trigger_name    character varying(80) not null,
    trigger_group   character varying(80) not null,
    cron_expression character varying(80) not null,
    time_zone_id    character varying(80),
    sched_name      character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_fired_triggers
(
    entry_id          character varying(95) not null,
    trigger_name      character varying(80) not null,
    trigger_group     character varying(80) not null,
    instance_name     character varying(80) not null,
    fired_time        bigint                not null,
    priority          integer               not null,
    state             character varying(16) not null,
    job_name          character varying(80),
    job_group         character varying(80),
    is_nonconcurrent  boolean,
    is_update_data    boolean,
    sched_name        character varying(120) default 'testscheduler':: character varying not null,
    sched_time        bigint                not null,
    requests_recovery boolean
);

create table if not exists qrtz_job_details
(
    job_name          character varying(128) not null,
    job_group         character varying(80)  not null,
    description       character varying(120),
    job_class_name    character varying(200) not null,
    is_durable        boolean,
    is_nonconcurrent  boolean,
    is_update_data    boolean,
    sched_name        character varying(120) default 'testscheduler':: character varying not null,
    requests_recovery boolean,
    job_data          bytea
);

create table if not exists qrtz_locks
(
    lock_name  character varying(40) not null,
    sched_name character varying(120) default 'testscheduler':: character varying not null
);

insert into qrtz_locks
values ('trigger_access');
insert into qrtz_locks
values ('job_access');
insert into qrtz_locks
values ('calendar_access');
insert into qrtz_locks
values ('state_access');
insert into qrtz_locks
values ('misfire_access');

create table if not exists qrtz_paused_trigger_grps
(
    trigger_group character varying(80) not null,
    sched_name    character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_scheduler_state
(
    instance_name     character varying(200) not null,
    last_checkin_time bigint,
    checkin_interval  bigint,
    sched_name        character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_simple_triggers
(
    trigger_name    character varying(80) not null,
    trigger_group   character varying(80) not null,
    repeat_count    bigint                not null,
    repeat_interval bigint                not null,
    times_triggered bigint                not null,
    sched_name      character varying(120) default 'testscheduler':: character varying not null
);

create table if not exists qrtz_simprop_triggers
(
    sched_name    character varying(120) not null,
    trigger_name  character varying(200) not null,
    trigger_group character varying(200) not null,
    str_prop_1    character varying(512),
    str_prop_2    character varying(512),
    str_prop_3    character varying(512),
    int_prop_1    integer,
    int_prop_2    integer,
    long_prop_1   bigint,
    long_prop_2   bigint,
    dec_prop_1    numeric(13, 4),
    dec_prop_2    numeric(13, 4),
    bool_prop_1   boolean,
    bool_prop_2   boolean
);

create table if not exists qrtz_triggers
(
    trigger_name   character varying(80) not null,
    trigger_group  character varying(80) not null,
    job_name       character varying(80) not null,
    job_group      character varying(80) not null,
    description    character varying(120),
    next_fire_time bigint,
    prev_fire_time bigint,
    priority       integer,
    trigger_state  character varying(16) not null,
    trigger_type   character varying(8)  not null,
    start_time     bigint                not null,
    end_time       bigint,
    calendar_name  character varying(80),
    misfire_instr  smallint,
    job_data       bytea,
    sched_name     character varying(120) default 'testscheduler':: character varying not null
);


alter table only qrtz_blob_triggers
    add constraint qrtz_blob_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_calendars
    add constraint qrtz_calendars_pkey primary key (sched_name, calendar_name);

alter table only qrtz_cron_triggers
    add constraint qrtz_cron_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_fired_triggers
    add constraint qrtz_fired_triggers_pkey primary key (sched_name, entry_id);

alter table only qrtz_job_details
    add constraint qrtz_job_details_pkey primary key (sched_name, job_name, job_group);

alter table only qrtz_locks
    add constraint qrtz_locks_pkey primary key (sched_name, lock_name);

alter table only qrtz_paused_trigger_grps
    add constraint qrtz_paused_trigger_grps_pkey primary key (sched_name, trigger_group);

alter table only qrtz_scheduler_state
    add constraint qrtz_scheduler_state_pkey primary key (sched_name, instance_name);

alter table only qrtz_simple_triggers
    add constraint qrtz_simple_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_simprop_triggers
    add constraint qrtz_simprop_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

alter table only qrtz_triggers
    add constraint qrtz_triggers_pkey primary key (sched_name, trigger_name, trigger_group);

create
index  fki_qrtz_simple_triggers_job_details_name_group on qrtz_triggers using btree (job_name, job_group);

create
index fki_qrtz_simple_triggers_qrtz_triggers on qrtz_simple_triggers using btree (trigger_name, trigger_group);

create
index idx_qrtz_ft_j_g on qrtz_fired_triggers using btree (sched_name, job_name, job_group);

create
index idx_qrtz_ft_jg on qrtz_fired_triggers using btree (sched_name, job_group);

create
index idx_qrtz_ft_t_g on qrtz_fired_triggers using btree (sched_name, trigger_name, trigger_group);

create
index idx_qrtz_ft_tg on qrtz_fired_triggers using btree (sched_name, trigger_group);

create
index idx_qrtz_ft_trig_inst_name on qrtz_fired_triggers using btree (sched_name, instance_name);

create
index idx_qrtz_j_grp on qrtz_job_details using btree (sched_name, job_group);

create
index idx_qrtz_t_c on qrtz_triggers using btree (sched_name, calendar_name);

create
index idx_qrtz_t_g on qrtz_triggers using btree (sched_name, trigger_group);

create
index idx_qrtz_t_j on qrtz_triggers using btree (sched_name, job_name, job_group);

create
index idx_qrtz_t_jg on qrtz_triggers using btree (sched_name, job_group);

create
index idx_qrtz_t_n_g_state on qrtz_triggers using btree (sched_name, trigger_group, trigger_state);

create
index idx_qrtz_t_n_state on qrtz_triggers using btree (sched_name, trigger_name, trigger_group, trigger_state);

create
index idx_qrtz_t_next_fire_time on qrtz_triggers using btree (sched_name, next_fire_time);

create
index idx_qrtz_t_nft_misfire on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time);

create
index idx_qrtz_t_nft_st on qrtz_triggers using btree (sched_name, trigger_state, next_fire_time);

create
index idx_qrtz_t_nft_st_misfire on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time, trigger_state);

create
index idx_qrtz_t_nft_st_misfire_grp on qrtz_triggers using btree (sched_name, misfire_instr, next_fire_time, trigger_group, trigger_state);

create
index idx_qrtz_t_state on qrtz_triggers using btree (sched_name, trigger_state);

alter table only qrtz_blob_triggers
    add constraint qrtz_blob_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers(sched_name, trigger_name, trigger_group);

alter table only qrtz_cron_triggers
    add constraint qrtz_cron_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers(sched_name, trigger_name, trigger_group);

alter table only qrtz_simple_triggers
    add constraint qrtz_simple_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers(sched_name, trigger_name, trigger_group);

alter table only qrtz_simprop_triggers
    add constraint qrtz_simprop_triggers_sched_name_fkey foreign key (sched_name, trigger_name, trigger_group) references qrtz_triggers(sched_name, trigger_name, trigger_group);

alter table only qrtz_triggers
    add constraint qrtz_triggers_sched_name_fkey foreign key (sched_name, job_name, job_group) references qrtz_job_details(sched_name, job_name, job_group);