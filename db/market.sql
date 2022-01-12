-- На сайте должны быть объявления. В объявлении должно быть: описание, марка машины, тип кузова, фото.
-- Объявление имеет статус продано или нет.
-- Должны cуществовать пользователи. Автор объявления.

create table if not exists body
(
    id   serial primary key,
    name varchar(64)
);

create table if not exists mark
(
    id   serial primary key,
    name varchar(128)
);

create table if not exists photo
(
    id   serial primary key,
    photoName varchar(128),
    ads_id int references advertisement(id)
);

create table if not exists users
(
    id   serial primary key,
    author varchar(128)
);

create table if not exists advertisement
(
    id   serial primary key,
    created timestamp,
    users_id int references users(id),
    description text,
    mark_id int references mark(id) not null,
    body_id int references body(id) not null,
    sold boolean not null
);

drop table advertisement;
drop table photo;

