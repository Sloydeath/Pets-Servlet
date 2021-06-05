CREATE TYPE PET_TYPE AS ENUM ('CAT', 'DOG');

CREATE TABLE IF NOT EXISTS person (
                                      id BIGSERIAL PRIMARY KEY,
                                      name VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS pet (
                                   id BIGSERIAL PRIMARY KEY,
                                   pet_type PET_TYPE,
                                   name VARCHAR,
                                   owner_id BIGINT NOT NULL,
                                   FOREIGN KEY (owner_id) REFERENCES person(id)
);

insert into person (name) values ('Andrew');
insert into person (name) values ('Ilya');
insert into person (name) values ('Nika');

insert into pet (pet_type, name, owner_id) values ('CAT', 'Lisa', 1);
insert into pet (pet_type, name, owner_id) values ('CAT', 'Barsik', 1);
insert into pet (pet_type, name, owner_id) values ('DOG', 'Kuki', 2);

select * from person;
select * from pet;