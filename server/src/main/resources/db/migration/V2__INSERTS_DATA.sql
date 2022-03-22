insert into accounts (id, balance)
VALUES (1234, 10000);
insert into cards (number, pin, account_id)
VALUES (1111222233334444, 1234, 1234);
insert into accounts_cards (account_id, cards_number)
VALUES (1234, 1111222233334444);

insert into accounts (id, balance)
VALUES (1000, 10000);
insert into cards (number, pin, account_id)
VALUES (1000200030004000, 1001, 1000);
insert into accounts_cards (account_id, cards_number)
VALUES (1000, 1000200030004000);