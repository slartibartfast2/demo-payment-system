drop table if exists payment_events CASCADE;
drop table if exists payment_orders CASCADE;
create sequence payment_events_id_seq increment 3 start 1;
create sequence payment_orders_id_seq increment 3 start 1;
create table payment_events
(
    id                 serial,
    created_at         timestamp without time zone default CURRENT_TIMESTAMP,
    checkout_id        varchar(255) unique,
    buyer_account      varchar(1000) not null,
    credit_card_token  varchar(255) not null,
    payment_completed  boolean default false,
    primary key (id)
);

create table payment_orders
(
    id                      serial,
    created_at              timestamp without time zone default CURRENT_TIMESTAMP,
    buyer_account           varchar(255) not null,
    seller_account          varchar(255) not null,
    order_amount            decimal not null,
    currency                varchar(50) not null,
    payment_order_status    varchar(50) not null,
    ledger_updated          boolean default false,
    wallet_updated          boolean default false,
    payment_event_id        int not null,
    primary key (id),
    CONSTRAINT fk_payment_event
        FOREIGN KEY(payment_event_id)
            REFERENCES payment_events(id)
);