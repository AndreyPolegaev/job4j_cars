-- У машины может быть только 1 уникальный двигатель. car-engine (one-to-one). Машина не может существовать без двигателя
-- При удалении двигателя, удаляем  автомобиль
-- driver - имя владельфа  автомобиля
-- history-owner связывает автомобили и владельцев.
-- У 1 авто может быть много владльцев, у 1 владельца много авто (many-to-many)

create table if not exists car
(
    id serial primary key,
    name varchar(64),
    engine_id int not null unique references engine(id)
);

create table if not exists engine
(
    id serial primary key,
    number varchar(128)
);

create table if not exists driver
(
    id serial primary key,
    name varchar(64)
);

create table if not exists history_owner
(
    id serial primary key,
    driver_id int not null references driver(id),
    car_id int not null references car(id)
);