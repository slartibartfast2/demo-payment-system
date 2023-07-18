drop table if exists cards CASCADE;
create table cards
(
    id                 serial,
    created_at         timestamp without time zone default CURRENT_TIMESTAMP,
    card_number        varchar(255) not null,
    card_holder_name   varchar(255) not null,
    card_token         varchar(255) unique,
    primary key (id)
);