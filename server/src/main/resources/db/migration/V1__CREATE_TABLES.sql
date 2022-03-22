create table atms
(
    id       bigint generated by default as identity,
    password varchar(255),
    username varchar(255),
    primary key (id)
);
create table accounts
(
    id      bigint not null,
    balance bigint,
    primary key (id)
);
create table accounts_cards
(
    account_id   bigint not null,
    cards_number bigint not null,
    primary key (account_id, cards_number)
);
create table cards
(
    number     bigint not null,
    pin        integer,
    account_id bigint,
    primary key (number)
);
alter table accounts_cards
    drop constraint if exists drop_accounts_cards;
alter table accounts_cards
    add constraint uk_cards_number unique (cards_number);
alter table accounts_cards
    add constraint fk_cards_number foreign key (cards_number) references cards;
alter table accounts_cards
    add constraint fk_account_id foreign key (account_id) references accounts;
alter table cards
    add constraint account_id_fk foreign key (account_id) references accounts;
