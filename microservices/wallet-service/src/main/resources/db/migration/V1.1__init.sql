drop table if exists balances CASCADE;
drop table if exists balance_transactions CASCADE;
create sequence balances_id_seq increment 3 start 1;
create sequence balance_transactions_id_seq increment 3 start 1;
create table balances
(
    id                 serial,
    created_at         timestamp without time zone,
    updated_at         timestamp,
    account            varchar(1000) unique,
    balance            decimal not null,
    currency           varchar(50) not null,
    primary key (id)
);

create table balance_transactions
(
    id                 serial,
    created_at         timestamp without time zone,
    account            varchar(1000) not null,
    amount             decimal not null,
    currency           varchar(50) not null,
    direction          varchar(15) not null,
    primary key (id)
);