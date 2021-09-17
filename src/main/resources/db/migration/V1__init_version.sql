create table if not exists employee
(
    id serial not null
        constraint employee_pkey
            primary key,
    name varchar(200) not null
);

create table if not exists responsibility
(
    id serial not null
        constraint responsibility_pkey
            primary key,
    name varchar(200) not null,
    days_number integer not null,
    constraint responsibility_name_days_number_key
        unique (name, days_number)
);

create index if not exists resp_name_index
    on responsibility (name);

create index if not exists odd_days_index
    on responsibility (days_number)
    where ((days_number % 2) <> 0);

create index if not exists even_days_index
    on responsibility (days_number)
    where ((days_number % 2) = 0);

create table if not exists schedule
(
    id serial not null
        constraint schedule_pkey
            primary key,
    employee_id integer not null
        constraint schedule_employee_id_fkey
            references employee,
    responsibility_id integer not null
        constraint schedule_responsibility_id_fkey
            references responsibility,
    start_date date,
    end_date date
);

